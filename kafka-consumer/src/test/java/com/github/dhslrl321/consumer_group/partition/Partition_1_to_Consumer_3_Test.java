package com.github.dhslrl321.consumer_group.partition;

import com.github.support.annotation.SinglePartitionKafkaTest;
import com.github.support.helper.KafkaConsumerTestHelper;
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

@SinglePartitionKafkaTest(testDescriptions = "하나의 파티션에 3개의 컨슈머가 붙")
public class Partition_1_to_Consumer_3_Test {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @AfterEach
    void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("파티션 1:3 컨슈머라면 오로지 하나의 컨슈머 인스턴스만 컨슘한다")
    void name() {
        produce("my-topic",
                "a", "b", "c", "d", "e",
                "1", "2", "3", "4", "5"
        );

        KafkaConsumer<String, String> consumer1 = KafkaConsumerTestHelper.simpleConsumer();
        KafkaConsumer<String, String> consumer2 = KafkaConsumerTestHelper.simpleConsumer();
        KafkaConsumer<String, String> consumer3 = KafkaConsumerTestHelper.simpleConsumer();

        consumer1.subscribe(List.of("my-topic")); // 파티션 구독
        consumer2.subscribe(List.of("my-topic"));
        consumer3.subscribe(List.of("my-topic"));

        pollAndPrint(consumer1, "consumer1"); // consume o 파티션은 하나만 점유됨
        pollAndPrint(consumer2, "consumer2"); // consume x
        pollAndPrint(consumer2, "consumer3"); // consume x
    }

    private void pollAndPrint(KafkaConsumer<String, String> consumer, String consumerName) {
        executorService.submit(() -> {
            List<ConsumerRecord<String, String>> records = recordListFrom(consumer.poll(Duration.ofSeconds(2)));
            records.forEach(it ->
                    System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", consumerName, it.partition(), it.offset(), it.value()));
        });
    }
}
