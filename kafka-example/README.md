# Requirements

# 목적

분산 시스템을 의식하여 프로젝트 설계를 해보고 싶었다.  
슬랙을 카피해서 만들어보는데 요구사항은 다음과 같다.  

- (당연히) 실시간 채팅
- 그러면서 채팅 내용은 영속화

이 경우 필요한 시스템은 몇 개인지 대충 생각을 해보면

- message broker: 실시간성, 연관 시스템에게 전파
- kafka: 메세지 큐
- redis: 메세지 읽기 캐시
- RDB: 메세지 영속화

# 1. 메세지 스펙

1. 슬랙처럼 메세지 하나하나가 url로 접근 가능해야함
2. 팀 - 채널 - 메세지 구조

# 2. 메세지 브로커

- 메세지 읽기/쓰기 요청 받음
- 웹소켓 세션 처리

# 3. 카프카

- 각 서비스들에게 메세지 전파
- 