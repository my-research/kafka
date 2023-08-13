package com.github.support.helper;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaConsumerTestHelper {

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

    public static void produce(String topic, String message) throws ExecutionException, InterruptedException {
        produce(new ProducerRecord<>(topic, message));
    }

    public static void produce(String topic, String partitionKey, String message) throws ExecutionException, InterruptedException {
        produce(new ProducerRecord<>(topic, partitionKey, message));
    }

    private static void produce(ProducerRecord<String, String> record) throws InterruptedException, ExecutionException {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props())) {
            Future<RecordMetadata> result = producer.send(record);

            RecordMetadata recordMetadata = result.get(); // blocking
            System.out.println("successfully produced to " + recordMetadata.topic());
        }
    }

    private static Map<String, Object> props() {
        return Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "value.serializer", "org.apache.kafka.common.serialization.StringSerializer",
                "acks", "1"
        );
    }
}