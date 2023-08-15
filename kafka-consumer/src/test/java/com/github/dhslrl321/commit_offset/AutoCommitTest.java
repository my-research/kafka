package com.github.dhslrl321.commit_offset;

import com.github.support.annotation.SinglePartitionKafkaTest;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.github.support.helper.ConsumerRecordsHelper.messagesFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.*;
import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@SinglePartitionKafkaTest
public class AutoCommitTest {

    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = consumerOf(Map.of(
                "max.poll.records", "3",
                "enable.auto.commit", "true"
        ));

        sut.subscribe(List.of("my-topic"));
    }

    @Test
    @DisplayName("auto commit 모드라서 poll() 이 호출될 때 commit 된다")
    void name() {
        produce("my-topic", "a", "b", "c", "1", "2", "3");

        List<String> first = messagesFrom(sut.poll(Duration.ofSeconds(2)));
        assertThat(first).isEqualTo(List.of("a", "b", "c"));

        List<String> second = messagesFrom(sut.poll(Duration.ofSeconds(2)));
        assertThat(second).isEqualTo(List.of("1", "2", "3"));
    }
}
