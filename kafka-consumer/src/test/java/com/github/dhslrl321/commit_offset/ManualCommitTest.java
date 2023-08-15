package com.github.dhslrl321.commit_offset;

import com.github.support.annotation.SinglePartitionKafkaTest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.github.support.helper.ConsumerRecordsHelper.messagesFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.consumerOf;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;
import static org.assertj.core.api.Assertions.assertThat;

@SinglePartitionKafkaTest
public class ManualCommitTest {
    KafkaConsumer<String, String> sut;

    @Test
    @DisplayName("manual commit 모드라서 처음 consume 했던 a, b, c 에 대해서 중복으로 소비한다")
    void name() {
        // message produce
        produce("my-topic", "a", "b", "c", "🔥", "✅", "⚽️");

        // first consume
        sut = manualCommitConsumer();
        assertThat(messagesFrom(sut.poll(Duration.ofSeconds(2))))
                .isEqualTo(List.of("a", "b", "c"));

        // commit & close
        sut.commitSync();
        sut.close();

        // second consume
        sut = manualCommitConsumer();
        assertThat(messagesFrom(sut.poll(Duration.ofSeconds(2))))
                .isEqualTo(List.of("🔥", "✅", "⚽️"));
    }

    private static KafkaConsumer<String, String> manualCommitConsumer() {
        KafkaConsumer<String, String> consumer = consumerOf(Map.of(
                "max.poll.records", "3",
                "enable.auto.commit", false
        ));
        consumer.subscribe(List.of("my-topic"));
        return consumer;
    }

}
