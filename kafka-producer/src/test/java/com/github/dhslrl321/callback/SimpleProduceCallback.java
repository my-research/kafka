package com.github.dhslrl321.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import static java.util.Objects.nonNull;

@Slf4j
public class SimpleProduceCallback implements Callback {

    public static SimpleProduceCallback newOne() {
        return new SimpleProduceCallback();
    }

    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if (nonNull(metadata)) {
            log.info("✅ 메시지 발행 성공, topic: {}, partition: {}, offset: {}, timestamp: {}",
                    metadata.topic(),
                    metadata.partition(),
                    metadata.offset(),
                    metadata.timestamp());

        } else {
            log.error("❌ 메시지 발행 실패", exception);
        }
    }
}
