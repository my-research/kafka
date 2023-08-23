package com.github.dhslrl321.partition;

import com.github.support.annotation.KafkaTest;
import com.github.support.assertions.Partition;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.github.support.assertions.KafkaAssertions.assertConsumedThat;
import static com.github.support.assertions.Topic.topic;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;

@KafkaTest
public class Consume_Partition_Test {
    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    @DisplayName("특정 파티션만 consume 할 수 있다")
    void name() {
        produce("my-topic", "say-hello", "hello world!");
        produce("my-topic", "say-good-bye", "bye world!");

        sut.assign(List.of(new TopicPartition("my-topic", 99)));

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(2));

        assertConsumedThat(actual, Partition.partition(0)).isEqualTo("hello world!");
    }
}
