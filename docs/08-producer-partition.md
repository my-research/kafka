# producing partition

- produce 옵션으로 partitionKey 를 명시할 수 있다
- partitionKey 를 명시하지 않으면 round robin 으로 파티션 할당된다
- partitionKey 가 partition 개수보다 크면 적절한 파티션으로 리밸런싱을 한다

