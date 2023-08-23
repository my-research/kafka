package com.github.dhslrl321.order;

import com.github.support.annotation.SinglePartitionKafkaTest;
import com.github.support.helper.KafkaConsumerTestHelper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.github.support.helper.ConsumerRecordsHelper.messagesFrom;
import static com.github.support.helper.KafkaConsumerTestHelper.produce;
import static org.assertj.core.api.Assertions.assertThat;

@SinglePartitionKafkaTest(testDescriptions = "토픽에 파티션이 하나인 테스트")
public class Consume_Single_Partition_OrderingTest {
    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    @DisplayName("partition 이 하나라서 발행한 순서대로 consume 된다")
    void name() {
        // 1
        produce("my-topic", "a", "b", "c");
        produce("my-topic", "🔥", "✅", "🥶");

        sut.subscribe(List.of("my-topic")); // 2

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(2)); // 3

        List<String> messages = messagesFrom(actual); // 4

        // 발행 순서대로 consume 된다
        assertThat(messages)
                .isEqualTo(
                        List.of("a", "b", "c", "🔥", "✅", "🥶") // 5
                );
    }
}
