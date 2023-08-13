# apache kafka

학습하며 정리한 내용과 카프카 사용 예제 코드 저장소입니다.

아래 테스트 코드를 통해 카프카의 기본 사용법에 대한 학습 테스트를 직접 수행할 수 있습니다

## 개념

kafka basics([카프카 기본](https://github.com/my-research/kafka/blob/master/docs/01-kafka.md)),
differences with others([다른 메시지 브로커와의 차이점](https://github.com/my-research/kafka/blob/master/docs/02-kafka-differences.md)),
topics([토픽](https://github.com/my-research/kafka/blob/master/docs/03-topic.md)),
partition([파티션](https://github.com/my-research/kafka/blob/master/docs/04-partition.md)),
replication([리플리케이션](https://github.com/my-research/kafka/blob/master/docs/05-replication.md)),
In-Sync-Replica([ISR](https://github.com/my-research/kafka/blob/master/docs/06-In-Sync-Replica.md)),
producer([프로듀서](https://github.com/my-research/kafka/blob/master/docs/07-producer.md)),

## 학습 테스트

#### kafka producer

- [프로듀싱 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/produce)
  - 토픽에 프로듀싱
  - 토픽 생성 옵션이 꺼져있을 때
- [프로듀서 콜백 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/callback)
  - callback 을 사용한 비동기 처리
- [파티션 프로듀싱 테스트](https://github.com/my-research/kafka/tree/master/kafka-producer/src/test/java/com/github/dhslrl321/partition)
  - 파티션 키를 지정하지 않아 Round Robin 파티션 할당 
  - 파티션 키를 지정하는 프로듀싱
  - 파티션수를 초과하는 producer 

#### kafka consumer

- [컨슈밍 테스트](https://github.com/my-research/kafka/tree/master/kafka-consumer/src/test/java/com/github/dhslrl321/consume)
  - 싱글/멀티 토픽 컨슈밍

#### kafka test supports

- [EmbeddedKafka supports](https://github.com/my-research/kafka/tree/master/kafka-test-supports/src/main/java/com/github/support)
  - `@KafkaTest` : EmbeddedKafka 를 이용하는 테스트 support
  - `@SinglePartitionKafkaTest` : Single partition 의 KafkaTest 
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
