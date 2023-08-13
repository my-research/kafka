package com.github.dhslrl321.consume;

import com.github.support.annotation.KafkaTest;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.github.support.assertions.KafkaAssertions.assertConsumedThat;
import static com.github.support.assertions.Topic.topic;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;

@KafkaTest
public class Consume_KafkaConsumerTest {

    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    @DisplayName("topic 에 message 를 발행하면 consume 할 수 있다")
    void name() throws ExecutionException, InterruptedException {
        produce("my-topic", "key1", "hello world!");

        sut.subscribe(List.of("my-topic"));

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(10));

        assertConsumedThat(actual, topic("my-topic")).isEqualTo("hello world!");
    }
}
