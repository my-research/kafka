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
public class Set_Partition_Key_KafkaTest {
    KafkaProducer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaProducerTestHelper.getSimpleProducer(1);
    }


    @Test
    @DisplayName("partition key 를 입력하면 특정한 파티션에 들어간다.")
    void name() {
        for (int i = 0; i < 6; i++) {
            String partitionKey = isEven(i) ? "1" : "2";
            ProducerRecord<String, String> message = new ProducerRecord<>("my-topic2", partitionKey, "hello ~ " + i);

            sut.send(message, SimpleProduceCallback.newOne());
        }

        sut.close();
    }

    /**
     * 균등하게 파티션에 들어감
     * topic: [my-topic2], partition: [1], offset: [0], timestamp: [1691902926768]
     * topic: [my-topic2], partition: [0], offset: [0], timestamp: [1691902926773]
     * topic: [my-topic2], partition: [1], offset: [1], timestamp: [1691902926773]
     * topic: [my-topic2], partition: [1], offset: [2], timestamp: [1691902926776]
     * topic: [my-topic2], partition: [0], offset: [1], timestamp: [1691902926775]
     * topic: [my-topic2], partition: [0], offset: [2], timestamp: [1691902926776]
     */

    private static boolean isEven(int i) {
        return i % 2 == 0;
    }

}
