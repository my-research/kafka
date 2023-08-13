package com.github.dhslrl321.partition;

import com.github.dhslrl321.callback.PrintRecordMetaCallback;
import com.github.dhslrl321.support.KafkaProducerTest;
import com.github.dhslrl321.support.KafkaProducerTestHelper;
import com.github.dhslrl321.support.SinglePartitionKafkaProducerTest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

@KafkaProducerTest(testDescriptions = "partition 의 수는 2개")
public class RoundRobin_PartitionKey_KafkaProducerTest {
    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.kafkaStringProducer(1);
    }


    @Test
    @DisplayName("partition key 를 입력하면 특정한 파티션에 들어간다.")
    void name() {
        IntStream.range(0, 6)
                .forEach(i ->
                        sutSend("hello ~ " + i));

        sut.close();
    }

    private void sutSend(String messageValue) {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", messageValue);

        sut.send(message, PrintRecordMetaCallback.get());
    }

    /**
     * 파티션을 명시적으로 입력하지 않으면 RR 로 할당됨
     *
     * [my-topic2], partition: [0], offset: [0], timestamp: [1691903812289]
     * [my-topic2], partition: [0], offset: [1], timestamp: [1691903812294]
     * [my-topic2], partition: [0], offset: [2], timestamp: [1691903812294]
     * [my-topic2], partition: [0], offset: [3], timestamp: [1691903812294]
     * [my-topic2], partition: [1], offset: [0], timestamp: [1691903812295]
     * [my-topic2], partition: [1], offset: [1], timestamp: [1691903812297]
     */
}
