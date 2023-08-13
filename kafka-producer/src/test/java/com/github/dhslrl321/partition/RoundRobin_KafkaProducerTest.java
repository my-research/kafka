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
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                sutSend("1", "hello ~ " + i);
            } else {
                sutSend("0", "hello ~ " + i);

            }
        }

        sut.close();
    }

    private ProducerRecord<String, String> sutSend(String partitionKey, String messageValue) {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", partitionKey, messageValue);

        sut.send(message, AsyncProducingCallback.defaultCallback());
        return message;
    }
}
