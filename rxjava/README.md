# 1. 리액티브

* push: 데이터 변화 발생 시, 변경이 발생한 곳에서 데이터를 보내줌
* pull: 변경된 데이터가 있는지 질의하고 가져옴

# 2. 리액티브 용어

* Observable: datasource
* Operators: 데이터 소스를 처리하는 함수
* Scheduler: 스레드 관리자
* Subscriber: Observable이 발행하는 데이터를 구독하는 구독자
* 함수형 프로그래밍: rxJava 연산자

# 3. Reactive Streams?

four interfaces

1. Publisher: 데이터 생성 및 통지
2. Subscriber: 통지된 데이터 전달받아서 처리
3. Subscription: 전달 받을 데이터의 개수를 요청하고 구독 해지
4. Processor: Publisher, Subscriber기능 모두 있음

## 3.1. Cold Publisher & Hot Publisher

1. cold
    - 생산자는 소비자가 굳고할 때마다 데이터 처음부터 새로 통지
    - 데이터 통지하는 새로운 타임라인
    - 소비자는 구독 시점과 관계 없이 통지된 데이터 처음부터 전달 받을 수 있음 
2. hot
    - 생산자는 소비자 수와 관계 없이 데이터 한 번만 통지
    - 데이터 통지 타임라인이 하나
    - 소비자는 발행된 데이터를 처음부터 전달 받는게 아니라 구독한 시점에 통진된 데이터들만 전달 받을 수 있음

# 4. Observable & Flowable

## 4.1. 배압 전략(Backpressure Strategy)

1. Missing
    - 배압 적용 안함
    - 나중에 onBackpressureXXX()로 적용 가능
2. Error
    - 통지된 데이터가 버퍼 크기 초과하면 MissingBackpressureException 에러 통지
    - 즉 소비자가 생산자의 통지 속도 못따라 잡음
    
ㅇㅇ

- 버퍼 전략: DROP_LATEST
    - 버퍼가 가득 찬 시점에 버퍼 내에서 가장 최근에 버퍼로 들어온 데이터를 drop
    - drop 된 빈 자리에 버퍼 밖에서 대기하던 데이터를 채움
- 버퍼 전략: DROP_OLDEST
    - 버퍼가 가득 찬 시점에 버퍼 내에서 가장 나중에 버퍼로 들어온 데이터를 drop
    - drop 된 빈 자리에 버퍼 밖에서 대기 하던 데이터를 채움
- DROP 전략
    - 버퍼에 데이터가 모두 찬 상태라면, 그 이후의 데이터는 모두 drop
    - 버퍼가 비워지는 시점에 drop 되지 않은 것을 버퍼에 담음
- LATEST 전략
    - 버퍼 데이터가 모두 채워진 상태가 되면 버퍼가 비워질 때까지 통지된 데이터는 버퍼 밖에서 대기, 버퍼가 비워지는 시점에 가장 나중에 통지된 데이터부터 버퍼에 담음
     
# 5. Single, Maybe, Completable

## 5.1. Single

- 데이터 1건만 통지하거나 에러 통지
- 데이터 통지 자체가 완료를 의미하기 때문에 완료 통지는 생략
- 데이터가 단건이기 때문에 데이터 개수 요청 필요 없음
- onNext, onComplete가 없고 이 둘을 합친 onSuccess를 제공
- single의 대표적인 소비자는 SingleObserver
- 클라이언트에 대한 서버 응답이 대표적인 예

## 5.2. Maybe

- 데이터를 1건만 통지하거나 1건도 통지하지 않고 완료 또는 에러를 통지한다.
- 데이터 통지 자체가 완료를 의미하기 때문에 완료 통지는 하지 않음
- 단, 데이터를 1건도 통지하지 않고 처리가 종료될 경우에는 완료를 통지
- Maybe의 대표적인 소비자는 MaybeObserver

## 5.3. Completable

- 데이터 생산자이지만 데이터를 1건도 통지하지 않고 완료 또는 에러를 통지 
- 데이터 통지의 역할 대신 Completable 내에서 특정 작업을 수행 후, 해당 처리가 끝났음을 통지
- 소비자는 CompletableObserver