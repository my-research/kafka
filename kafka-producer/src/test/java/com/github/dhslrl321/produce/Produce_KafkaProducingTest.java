package com.github.dhslrl321.produce;

import com.github.support.annotation.KafkaTest;
import com.github.support.helper.KafkaProducerTestHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@KafkaTest
public class Produce_KafkaProducingTest {

    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.getSimpleProducer();
    }

    @Test
    @DisplayName("동기 전송, 메시지를 보내고 future 로 성공 실패 확인")
    void name() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic", "hello world~!");

        Future<RecordMetadata> future = sut.send(message);

        RecordMetadata actual = future.get();

        assertThat(actual.topic()).isEqualTo("my-topic");
    }

    @Test
    @DisplayName("전송 후 확인하지 않기, 일부 메시지 누락 가능")
    void name2() {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic", "hello world~!");

        assertDoesNotThrow(() -> sut.send(message));
    }
}
