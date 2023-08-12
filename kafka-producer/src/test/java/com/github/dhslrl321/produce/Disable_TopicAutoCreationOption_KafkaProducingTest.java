package com.github.dhslrl321.produce;

import com.github.dhslrl321.support.EmptyTopicKafkaProducerTest;
import com.github.dhslrl321.support.KafkaProducerTestHelper;
import com.github.dhslrl321.support.KafkaNetworkTimeoutExtension;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(KafkaNetworkTimeoutExtension.class)
@EmptyTopicKafkaProducerTest(brokerProps = "auto.create.topics.enable=false")
public class Disable_TopicAutoCreationOption_KafkaProducingTest {

    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.simpleKafkaStringProducer();
    }

    @Test
    @DisplayName("토픽 생성이 자동으로 되지 않아서 예외가 발생함")
    @Timeout(value = 2)
    void name() {
        ProducerRecord<String, String> message = new ProducerRecord<>("topic", "abc");

        sut.send(message); // topic 생성이 불가함
    }
}
