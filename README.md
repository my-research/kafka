# 내가 만든 Kafka 문서와 실용적 사용법들

### 개념

카프카 기본([kafka basics](docs%2F01-kafka.md)),
다른 메시지 브로커와의 차이점([differences with others](docs%2F02-kafka-differences.md)),
토픽([topics](docs%2F03-topic.md)),
파티션([partition](docs%2F04-partition.md)),
리플리케이션([replication](docs%2F05-replication.md)),
ISR([In-Sync-Replica](docs%2F06-In-Sync-Replica.md)),
프로듀서([producer](docs%2F07-producer.md)),

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
