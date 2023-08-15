package com.github.dhslrl321.consumer_group.partition;

import com.github.support.annotation.TriplePartitionKafkaTest;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.support.helper.ConsumerRecordsHelper.recordListFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;

@TriplePartitionKafkaTest()
public class Partition_3_to_Consumer_1_Test {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @AfterEach
    void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("파티션 3:1 컨슈머 라면 해당 인스턴스가 모든 파티션을 점유한다")
    void name() {
        produce("my-topic",
                "a", "b", "c", "d", "e",
                "1", "2", "3", "4", "5"
        );

        KafkaConsumer<String, String> consumer1 = KafkaConsumerTestHelper.simpleConsumer();

        consumer1.subscribe(List.of("my-topic")); // 파티션 구독

        pollAndPrint(consumer1); // consume o 파티션은 하나만 점유됨
    }

    private void pollAndPrint(KafkaConsumer<String, String> consumer) {
        executorService.submit(() -> {
            List<ConsumerRecord<String, String>> records = recordListFrom(consumer.poll(Duration.ofSeconds(2)));
            records.forEach(it ->
                    System.out.printf("partition:[%s], offset:[%s], value:[%s]\n", it.partition(), it.offset(), it.value()));
        });
    }
}
