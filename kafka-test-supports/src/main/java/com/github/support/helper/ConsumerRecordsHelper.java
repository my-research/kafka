package com.github.support.helper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ConsumerRecordsHelper {
    public static List<ConsumerRecord<String, String>> recordListFrom(ConsumerRecords<String, String> records) {
        return StreamSupport.stream(records.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static List<String> messagesFrom(ConsumerRecords<String, String> records) {
        return StreamSupport.stream(records.spliterator(), false)
                .map(ConsumerRecord::value)
                .collect(Collectors.toList());
    }
}
