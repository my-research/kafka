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
public class Exceed_Max_Partition_Key_KafkaTest {
    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.getSimpleProducer(1);
    }


    @Test
    @DisplayName("partition key 를 입력하면 특정한 파티션에 들어간다.")
    void name() {
        IntStream.range(0, 6)
                .forEach(i ->
                        sutSend(getPartitionKey(i), "hello ~ " + i));

        sut.close();
    }

    /**
     * partition key 의 개수가 넘어가면 자동으로 리밸런싱을 함
     * [my-topic2], partition: [1], offset: [0], timestamp: [1691903245958]
     * [my-topic2], partition: [0], offset: [0], timestamp: [1691903245963]
     * [my-topic2], partition: [0], offset: [1], timestamp: [1691903245963]
     * [my-topic2], partition: [1], offset: [1], timestamp: [1691903245963]
     * [my-topic2], partition: [0], offset: [2], timestamp: [1691903245966]
     * [my-topic2], partition: [0], offset: [3], timestamp: [1691903245966]
     */

    private static String getPartitionKey(int i) {
        switch (i % 3) {
            case 0: return "pk3";
            case 1: return "pk1";
            case 2: return "pk2";
            default: throw new IllegalArgumentException("Invalid input: " + i);
        }
    }

    private void sutSend(String partitionKey, String messageValue) {
        ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", partitionKey, messageValue);

        sut.send(message, SimpleProduceCallback.newOne());
    }
}
