# re-balance

- 컨슈머 그룹 내에서 컨슈머의 파티션 소유권이 이전하는 것
  - 컨슈머 그룹의 리밸런스를 통해서 컨슈머를 쉽게 추가하거나 제거할 수 있다
  - 결국 이러한 특성이 high availability 와 high scalability 를 제공하는 것이다
- 리밸런싱을 하는 도중에는 메시지를 가져올 수 없다
- 하나의 파티션에는 하나의 컨슈머 그룹의 컨슈머만 연결할 수 있다

# 파티션 수와 컨슈머 그룹의 컨슈머 수의 관계

- 파티션 수 > 컨슈머 수
  - 특정 컨슈머는 특정 파티션 2개 이상을 컨슘할 수 있음
- 파티션 수 < 컨슈머 수
  - 특정 컨슈머는 놀고있음 (1 파티션:1 컨슈머 원칙때문에)
- 파티션 수 == 컨슈머 수
  - 가장 최적의 처리량

# 컨슈머의 소유권 유지 방법

- 특정 파티션에 컨슈머가 물려있다는 것을 확인하기 위해서 계속해서 하트비트 체크를 함
  - 일정 주기로 체크하는 하트비트가 잘 동작하면 컨슘을 제대로 한다는 뜻
- 하트비트는 컨슈머가 poll 할 때와 가져간 메시지의 오프셋을 커밋할 때 같이 보냄
- 만약 오랜 시간동안 하트비트를 보내지 않으면 세션은 타임아웃이라 판단하고 해당 컨슈머가 다운되었다고 정의
  - 리밸런싱 시작 -> 일정 시간동안 메시지 처리 불가
