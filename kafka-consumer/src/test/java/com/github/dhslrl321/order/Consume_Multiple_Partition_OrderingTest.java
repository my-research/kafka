package com.github.dhslrl321.order;

import com.github.support.annotation.TriplePartitionKafkaTest;
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

@TriplePartitionKafkaTest(testDescriptions = "파티션이 3개인 카프카 브로커 테스트")
public class Consume_Multiple_Partition_OrderingTest {

    KafkaConsumer<String, String> sut;

    @BeforeEach
    void setUp() {
        sut = KafkaConsumerTestHelper.simpleConsumer();
    }

    @Test
    @DisplayName("partition 이 여러개라면 발행한 순서대로 consume 하지 않는다")
    void name() {
        // 1
        produce("my-topic", "a", "b", "c");
        produce("my-topic", "🔥", "✅", "🥶");

        sut.subscribe(List.of("my-topic")); // 2

        ConsumerRecords<String, String> actual = sut.poll(Duration.ofSeconds(2)); // 3

        List<String> messages = messagesFrom(actual); // 4

        // 순서 대로 consume 되지 않는다
        assertThat(messages)
                .isNotEqualTo( // 5
                        List.of("a", "b", "c", "🔥", "✅", "🥶")
                );
    }
}
