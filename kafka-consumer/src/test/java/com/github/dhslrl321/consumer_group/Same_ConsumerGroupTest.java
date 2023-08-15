package com.github.dhslrl321.consumer_group;

import com.github.support.annotation.TriplePartitionKafkaTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.support.helper.ConsumerRecordsHelper.recordListFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;
import static com.github.support.helper.KafkaConsumerTestHelper.simpleConsumer;

@TriplePartitionKafkaTest(testDescriptions = "컨슈머 그룹 테스트를 위해 파티션을 3개로 지정")
public class Same_ConsumerGroupTest {

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @AfterEach
    void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("컨슈머 그룹이 카프카 브로커에 등록되고 컨슈머 인스턴스에 파티션이 할당된다")
    void name() {
        produce("my-topic",
                "a", "b", "c", "d", "e",
                "1", "2", "3", "4", "5"
        );

        KafkaConsumer<String, String> sut_consumer1 = simpleConsumer("group.id", "A");
        KafkaConsumer<String, String> sut_consumer2 = simpleConsumer("group.id", "B");

        sut_consumer1.subscribe(List.of("my-topic")); // 파티션 구독
        sut_consumer2.subscribe(List.of("my-topic"));

        // 카프카 브로커에 컨슈머 인스턴스가 등록되는 순간 (re-balancing 이 일어남)
        executorService.submit(() -> {
            List<ConsumerRecord<String, String>> records = recordListFrom(sut_consumer1.poll(Duration.ofSeconds(2)));
            records.forEach(it ->
                    System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", "consumer1", it.partition(), it.offset(), it.value()));
        });

        executorService.submit(() -> {
            List<ConsumerRecord<String, String>> records = recordListFrom(sut_consumer2.poll(Duration.ofSeconds(2)));
            records.forEach(it ->
                    System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", "consumer2", it.partition(), it.offset(), it.value()));
        });
    }

}
