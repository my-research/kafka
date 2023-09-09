package com.github.dhslrl321.commit_offset;

import com.github.support.annotation.SinglePartitionKafkaTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.github.support.helper.ConsumerRecordsHelper.messagesFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.consumerOf;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;
import static org.assertj.core.api.Assertions.assertThat;

@SinglePartitionKafkaTest
public class Disable_AutoCommit_Test {

    KafkaConsumer<String, String> sut;

    private static KafkaConsumer<String, String> manualCommitConsumer() {
        KafkaConsumer<String, String> consumer = consumerOf(Map.of(
                "max.poll.records", "3", // 1
                "enable.auto.commit", true // 2
        ));
        consumer.subscribe(List.of("my-topic"));
        return consumer;
    }

    @Test
    @DisplayName("manual commit 모드라서 처음 consume 했던 a, b, c 에 대해서 중복으로 소비한다")
    void name() {
        produce("my-topic", "a", "b", "c", "🔥", "🔥", "🔥"); // 3

        sut = manualCommitConsumer(); // 4

        // 3
        List<String> first = messagesFrom(sut.poll(Duration.ofSeconds(2)));
        assertThat(first).isEqualTo(List.of("a", "b", "c"));

        /*sut.close(); // 4 consumer 종료

        sut = manualCommitConsumer(); // 5*/

        // 6
        List<String> second = messagesFrom(sut.poll(Duration.ofSeconds(2)));
        assertThat(second).isEqualTo(List.of("a", "b", "c"));
    }
}
