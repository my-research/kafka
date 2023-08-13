package com.github.dhslrl321.support.assertions;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.assertj.core.api.AbstractStringAssert;

import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaAssertions {
    public static AbstractStringAssert<?> assertRecordsThat(ConsumerRecords<String, String> record, String topic) {
        String value = StreamSupport.stream(record.spliterator(), false)
                .filter(i -> i.topic().equals(topic))
                .findFirst()
                .orElseGet(null)
                .value();
        return assertThat(value);
    }
}
