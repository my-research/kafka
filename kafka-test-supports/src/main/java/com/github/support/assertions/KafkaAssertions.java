package com.github.support.assertions;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.assertj.core.api.AbstractStringAssert;

import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaAssertions {
    public static AbstractStringAssert<?> assertConsumedThat(ConsumerRecords<String, String> record, Topic topic) {
        String value = StreamSupport.stream(record.spliterator(), false)
                .filter(i -> i.topic().equals(topic.getValue()))
                .findFirst()
                .orElseGet(null)
                .value();
        return assertThat(value);
    }

    public static AbstractStringAssert<?> assertConsumedThat(ConsumerRecords<String, String> record, Partition partition) {
        String value = StreamSupport.stream(record.spliterator(), false)
                .filter(i -> i.partition() == partition.getValue())
                .findFirst()
                .orElseGet(null)
                .value();
        return assertThat(value);
    }
}
