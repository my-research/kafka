package com.github.dhslrl321.support;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;

public class KafkaProducerTestHelper {
    public static KafkaProducer<String, String> kafkaStringProducer() {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer"
        );
        return new KafkaProducer<>(props);
    }

    public static KafkaProducer<String, String> kafkaStringProducer(int ack) {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "acks", String.valueOf(ack)
        );
        return new KafkaProducer<>(props);
    }
}
