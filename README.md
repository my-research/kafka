# 내가 만든 Kafka 문서와 실용적 사용법들

### 개념

kafka basics([카프카 기본](docs%2F01-kafka.md)),
differences with others([다른 메시지 브로커와의 차이점](docs%2F02-kafka-differences.md)),
topics([토픽](docs%2F03-topic.md)),
partition([파티션](docs%2F04-partition.md)),
replication([리플리케이션](docs%2F05-replication.md)),
In-Sync-Replica([ISR](docs%2F06-In-Sync-Replica.md)),
producer([프로듀서](docs%2F07-producer.md)),

### 실전 코드

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
