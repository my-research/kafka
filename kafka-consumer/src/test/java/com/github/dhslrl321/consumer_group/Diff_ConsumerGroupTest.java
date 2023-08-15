package com.github.dhslrl321.consumer_group;

import com.github.support.annotation.KafkaTest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.support.helper.ConsumerRecordsHelper.messagesFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;
import static com.github.support.helper.KafkaConsumerTestHelper.simpleConsumer;
import static org.assertj.core.api.Assertions.assertThat;

@KafkaTest(testDescriptions = "파티션 수는 2개")
public class Diff_ConsumerGroupTest {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    private KafkaConsumer<String, String> sut_consumer1;
    private KafkaConsumer<String, String> sut_consumer2;

    @AfterEach
    void tearDown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        sut_consumer1 = simpleConsumer("group.id", "g1");
        sut_consumer2 = simpleConsumer("group.id", "g2");

        sut_consumer1.subscribe(List.of("my-topic")); // 파티션 구독
        sut_consumer2.subscribe(List.of("my-topic"));
    }

    @Test
    @DisplayName("컨슈머 그룹 id 가 다르면 동일한 메시지에 대해서 각각의 컨슈머가 소비한다")
    void name() {
        produce("my-topic",
                "a", "b", "c",
                "1", "2", "3");

        // 컨슈머 그룹이 다르므로 모든 메시지 소비
        executorService.submit(() -> {
            List<String> messages = messagesFrom(sut_consumer1.poll(Duration.ofSeconds(2)));
            assertThat(messages).isEqualTo(List.of("a", "b", "c", "1", "2", "3"));
        });

        // 컨슈머 그룹이 다르므로 모든 메시지 소비
        executorService.submit(() -> {
            List<String> messages = messagesFrom(sut_consumer2.poll(Duration.ofSeconds(2)));
            assertThat(messages).isEqualTo(List.of("a", "b", "c", "1", "2", "3"));
        });
    }
}
