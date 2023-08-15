package com.github.dhslrl321.consumer_group;

import com.github.support.annotation.TriplePartitionKafkaTest;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.support.helper.ConsumerRecordsHelper.recordListFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;

@TriplePartitionKafkaTest
public class Refactor_ConsumerGroupTest {

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @AfterEach
    void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Test
    void name() {
        produce("my-topic", "a", "b", "c", "1", "2", "3");

        KafkaConsumer<String, String> consumer1 = KafkaConsumerTestHelper.simpleConsumer();
        KafkaConsumer<String, String> consumer2 = KafkaConsumerTestHelper.simpleConsumer();

        consumer1.subscribe(List.of("my-topic")); // 파티션 구독
        consumer2.subscribe(List.of("my-topic"));

        poll(consumer1); // 카프카 브로커에 컨슈머 인스턴스가 등록되는 순간 (re-balancing 이 일어남)
        poll(consumer2);
    }

    private void poll(KafkaConsumer<String, String> consumer) {
        executorService.submit(() -> {
            List<ConsumerRecord<String, String>> records = recordListFrom(consumer.poll(Duration.ofSeconds(2)));
            records.forEach(it ->
                    System.out.printf("partition:[%s], offset:[%s], value:[%s]\n", it.partition(), it.offset(), it.value()));
        });
    }
}
