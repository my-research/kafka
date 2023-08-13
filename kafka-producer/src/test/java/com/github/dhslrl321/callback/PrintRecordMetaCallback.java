package com.github.dhslrl321.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import static java.util.Objects.nonNull;

public class PrintRecordMetaCallback implements Callback {

    public static PrintRecordMetaCallback get() {
        return new PrintRecordMetaCallback();
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (nonNull(metadata)) {
            System.out.printf("!! produced record, topic: [%s], partition: [%s], offset: [%s], timestamp: [%s]%n",
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset(),
                    metadata.timestamp());
        }
    }
}
