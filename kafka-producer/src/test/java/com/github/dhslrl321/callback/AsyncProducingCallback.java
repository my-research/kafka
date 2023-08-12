package com.github.dhslrl321.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;

public class AsyncProducingCallback implements Callback {

    public static AsyncProducingCallback defaultCallback() {
        return new AsyncProducingCallback();
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (metadata != null) {
            System.out.printf("topic: [%s], partition: [%s], offset: [%s], timestamp: [%s]%n", metadata.topic(), metadata.partition(), metadata.offset(), metadata.serializedValueSize());
        }
    }
}
