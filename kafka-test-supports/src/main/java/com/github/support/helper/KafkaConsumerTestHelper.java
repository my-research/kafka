package com.github.support.helper;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaConsumerTestHelper {

    public static KafkaConsumer<String, String> simpleConsumer(String groupIdKey, String groupIdValue) {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                groupIdKey, groupIdValue,
                "enable.auto.commit", "true",
                "auto.offset.reset", "earliest",
                "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"
        );

        return new KafkaConsumer<>(props);
    }

    public static KafkaConsumer<String, String> simpleConsumer() {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                "group.id", "my-consumer",
                "enable.auto.commit", "true",
                "auto.offset.reset", "earliest",
                "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"
        );

        return new KafkaConsumer<>(props);
    }

    public static KafkaConsumer<String, String> autoCommitConsumer() {
        Map<String, Object> props = Map.of(
                "bootstrap.servers", "localhost:9092",
                "group.id", "my-consumer",
                "max.poll.records", "3",
                "enable.auto.commit", "true",
                "auto.offset.reset", "earliest",
                "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"
        );

        return new KafkaConsumer<>(props);
    }

    public static KafkaConsumer<String, String> consumerOf(Map<String, Object> additional) {

        HashMap<String, Object> base = new HashMap<>();

        base.put("bootstrap.servers", "localhost:9092");
        base.put("group.id", "my-consumer");
        base.put("auto.offset.reset", "earliest");
        base.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        base.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        base.putAll(additional);

        return new KafkaConsumer<>(base);
    }

    public static void produce(String topic, String... messages) {
        Arrays.stream(messages).forEach(message -> {
            try {
                produce(new ProducerRecord<>(topic, message));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void produce(String topic, String message) {
        try {
            produce(new ProducerRecord<>(topic, message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void produce(String topic, String partitionKey, String message) {
        try {
            produce(new ProducerRecord<>(topic, partitionKey, message));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void produce(ProducerRecord<String, String> record) throws InterruptedException, ExecutionException {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "acks", "1"
        ))) {
            Future<RecordMetadata> result = producer.send(record);

            RecordMetadata recordMetadata = result.get(); // blocking
            System.out.println("successfully produced to " + recordMetadata.topic());
        }
    }

}
