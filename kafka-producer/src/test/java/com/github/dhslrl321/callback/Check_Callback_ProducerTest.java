package com.github.dhslrl321.callback;

import com.github.support.annotation.KafkaProducerTest;
import com.github.support.helper.KafkaProducerTestHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@KafkaProducerTest
public class Check_Callback_ProducerTest {

    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.getSimpleProducer();
    }

    @Test
    @DisplayName("카프카 메시지 발행 후 callback 을 실행시킬 수 있음")
    void name() {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic", "hello world~!");

        sut.send(message, PrintRecordMetaCallback.get());

        sut.close();
    }
}
