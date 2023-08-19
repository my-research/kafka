# apache kafka

학습하며 정리한 내용 & 개념과 카프카 사용 예제 코드 저장소입니다.

아래 테스트 코드를 통해 카프카의 기본 사용법에 대한 학습 테스트를 직접 수행할 수 있습니다

## Kafka 101, 개념

kafka basics([카프카 기본](https://github.com/my-research/kafka/blob/master/docs/01-kafka.md)),
differences with others([다른 메시지 브로커와의 차이점](https://github.com/my-research/kafka/blob/master/docs/02-kafka-differences.md)),
topics([토픽](https://github.com/my-research/kafka/blob/master/docs/03-topic.md)),
partition([파티션](https://github.com/my-research/kafka/blob/master/docs/04-partition.md)),
replication([리플리케이션](https://github.com/my-research/kafka/blob/master/docs/05-replication.md)),
In-Sync-Replica([ISR](https://github.com/my-research/kafka/blob/master/docs/06-In-Sync-Replica.md)),
producer([프로듀서](https://github.com/my-research/kafka/blob/master/docs/07-producer.md)),
producer & partition([프로듀서와 파티션](https://github.com/my-research/kafka/blob/master/docs/08-producer-partition.md)),
producer & acknowledgement([프로듀서와 ack](https://github.com/my-research/kafka/blob/master/docs/09-producer-ack.md)),
consumer([컨슈머](https://github.com/my-research/kafka/blob/master/docs/10-consumer.md)),
partition ordering([파티션과 순서 보장](https://github.com/my-research/kafka/blob/master/docs/11-partition-order.md)),
consumer group([컨슈머 그룹](https://github.com/my-research/kafka/blob/master/docs/12-consumer-group.md)),
re balancing([리밸런싱](https://github.com/my-research/kafka/blob/master/docs/13-re-balance.md)),
commit & offset([커밋과 오프셋](https://github.com/my-research/kafka/blob/master/docs/14-commit-offset.md)),

## 학습 테스트

테스트는 기본적으로 spring embedded kafka 를 사용합니다. 또한 테스트를 위한 supports 를 제공하여 더욱 깔끔한 테스트를 제공합니다

### kafka producer

- [프로듀싱 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/produce)
  - 토픽에 프로듀싱
  - 토픽 생성 옵션이 꺼져있을 때
- [프로듀서 콜백 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/callback)
  - callback 을 사용한 비동기 처리
- [파티션 프로듀싱 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/partition)
  - 파티션 키를 지정하지 않아 Round Robin 파티션 할당 
  - 파티션 키를 지정하는 프로듀싱
  - 파티션수를 초과하는 producer 

### kafka consumer

- [컨슈밍 테스트](https://github.com/my-research/kafka/tree/master/kafka-consumer/src/test/java/com/github/dhslrl321/consume)
  - 싱글/멀티 토픽 컨슈밍
- [컨슈머 순서 보장](#)
  - 단일 파티션의 순서
  - 다중 파티션의 순서
- [컨슈머 그룹 테스트](#)
  - 동일/다른 컨슈머 그룹의 consuming
  - 파티션 수에 따른 컨슈머 상관관계
- [커밋과 오프셋](#)
  - auto commit mode
  - manual commit mode

### kafka test supports

- [EmbeddedKafka supports](https://github.com/my-research/kafka/tree/master/kafka-test-supports/src/main/java/com/github/support)
  - `@KafkaTest` : EmbeddedKafka 를 이용하는 테스트 support
  - `@SinglePartitionKafkaTest` : Single partition 의 KafkaTest 
  - `@TriplePartitionKafkaTest` : 3 개의 partition 을 가지는 KafkaTest
  - `@EmptyTopicKafkaTest` : Topic 자동 생성 옵션이 꺼진 KafkaTes
- [Junit supports](https://github.com/my-research/kafka/tree/master/kafka-test-supports/src/main/java/com/github/support)
  - consumer record 에 대한 Junit Assertions
  - 카프카 connection error 를 위한 Junit Tiemout Extension
- [Helper](https://github.com/my-research/kafka/tree/master/kafka-test-supports/src/main/java/com/github/support)
  - producer 테스트를 위한 helper 클래스
  - consumer 테스트를 위한 helper 클래스

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
