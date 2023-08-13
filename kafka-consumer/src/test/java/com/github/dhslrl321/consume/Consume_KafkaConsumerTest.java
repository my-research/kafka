package com.github.dhslrl321.consume;

import com.github.support.annotation.KafkaConsumerTest;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.github.support.assertions.KafkaAssertions.assertRecordsThat;

@KafkaConsumerTest
public class Consume_KafkaConsumerTest {

    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    void name() throws ExecutionException, InterruptedException {
        KafkaConsumerTestHelper.produce("my-topic", "key1", "hello world!");

        sut.subscribe(List.of("my-topic"));

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(10));

        assertRecordsThat(actual, "my-topic").isEqualTo("hello world!");
    }
}
