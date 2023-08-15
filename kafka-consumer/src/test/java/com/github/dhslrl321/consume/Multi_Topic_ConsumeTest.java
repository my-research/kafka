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
public class Multi_Topic_ConsumeTest {

    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    @DisplayName("kafka 의 consumer 는 두개 혹은 그 이상의 토픽을 consume 할 수 있다")
    void name() {
        produce("my-topic-1", "hello~");
        produce("my-topic-2", "bye~");

        sut.subscribe(List.of("my-topic-1", "my-topic-2"));

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(10));

        assertConsumedThat(actual, topic("my-topic-1")).isEqualTo("hello~");
        assertConsumedThat(actual, topic("my-topic-2")).isEqualTo("bye~");
    }
}
