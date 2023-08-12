package com.github.dhslrl321.partition;

import com.github.dhslrl321.callback.AsyncProducingCallback;
import com.github.dhslrl321.support.KafkaProducerTest;
import com.github.dhslrl321.support.KafkaProducerTestHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@KafkaProducerTest
public class RoundRobin_KafkaProducerTest {
    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.simpleKafkaStringProducer();
    }

    @Test
    void name() {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", "hello world~");

        sut.send(message, AsyncProducingCallback.defaultCallback());
        sut.send(message, AsyncProducingCallback.defaultCallback());
        sut.send(message, AsyncProducingCallback.defaultCallback());
        sut.send(message, AsyncProducingCallback.defaultCallback());
        sut.send(message, AsyncProducingCallback.defaultCallback());

        sut.close();
    }
}
