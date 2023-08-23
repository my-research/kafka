package com.github.dhslrl321.partition;

import com.github.dhslrl321.callback.SimpleProduceCallback;
import com.github.support.annotation.KafkaTest;
import com.github.support.helper.KafkaProducerTestHelper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

@KafkaTest(testDescriptions = "partition 의 수는 2개")
public class RoundRobin_PartitionKey_KafkaTest {

    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.getSimpleProducer(1);
    }


    @Test
    @DisplayName("partition key 를 입력 하면 특정한 partition 에 들어 간다.")
    void name() {
        for (int i = 0; i < 6; i++) { // 1
            ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", "hello ~ " + i); // 2

            sut.send(message, SimpleProduceCallback.newOne()); // 3
        }

        sut.close(); // 4
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
