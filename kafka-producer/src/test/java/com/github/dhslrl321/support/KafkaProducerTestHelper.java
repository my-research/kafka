package com.github.dhslrl321.support;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;

public class KafkaProducerTestHelper {
    public static KafkaProducer<String, String> simpleKafkaStringProducer() {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer"
        );
        return new KafkaProducer<>(props);
    }
}
