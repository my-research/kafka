# apache kafka

학습하며 정리한 내용과 카프카 사용 예제 코드 저장소

## 개념

kafka basics([카프카 기본](https://github.com/my-research/kafka/blob/master/docs/01-kafka.md)),
differences with others([다른 메시지 브로커와의 차이점](https://github.com/my-research/kafka/blob/master/docs/02-kafka-differences.md)),
topics([토픽](https://github.com/my-research/kafka/blob/master/docs/03-topic.md)),
partition([파티션](https://github.com/my-research/kafka/blob/master/docs/04-partition.md)),
replication([리플리케이션](https://github.com/my-research/kafka/blob/master/docs/05-replication.md)),
In-Sync-Replica([ISR](https://github.com/my-research/kafka/blob/master/docs/06-In-Sync-Replica.md)),
producer([프로듀서](https://github.com/my-research/kafka/blob/master/docs/07-producer.md)),

## 실전 코드

### kafka producer

basic usage of kafka producer([기본 카프카 프로듀서 사용](#)),
producer callback([프로듀서 콜백](#)),
produce to partition([파티션 프로듀싱](#)),

### kafka consumer

### kafka test supports

EmbeddedKafka 를 이용하는 테스트([@KafkaTest](#)),
Single partition 의 KafkaTest([@SinglePartitionKafkaTest](#)),
Topic 자동 생성 옵션이 꺼진 KafkaTest([@EmptyTopicKafkaTest](#)),
consumer record 에 대한 Junit Assertions([KafkaAssertions.java](#)),
producer helper([KafkaProducerHelper.java](#)),
consumer helper([KafkaConsumerHelper.java](#)),
카프카 연결 실패에 대한 Junit Extension([KafkaNetworkTimeoutExtension.java](#)),

# run

Makefile 을 통해 간단히 kafka-ui 가 포함된 kafka 클러스터를 실행시킬 수 있습니다

### prerequisites

- docker
- docker-compose

### run kafka cluster with kafka ui 

```shell
### cluster 실행
make up

### cluster 종료
make down

### cluster 확인
make ps
```
