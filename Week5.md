### Week5 2021-02-07

279p ~ 315p

# 4장 예외
- JdbcTemplate을 대표로 하는 스프링의 데이터 액세스 기능에 담겨 있는 예외처리에 대해 알아본다

## 4.1 사라진 SQLException
### 4.1.1 초난감 예외처리
#### 예외 블랙홀
- catch and doNothing
    - 예외는 `복구`되든지, 작업을 중단시키고 분명하게 `통보`돼야 한다
#### 무의미하고 무책임한 throws

### 4.1.2 예외의 종류와 특징
- Error
    - OOM, ThreadDeath
    - 주로 JVM에서 발생시키며 애플리케이션 레벨에서 처리X
- Exception
    - Exception (checked)
        - 명시적인 예외처리
    - RuntimeException (unchecked)
        - 예외처리 강제 X

### 4.1.3 예외처리 방법
#### 예외 복구
- 예외상황을 정상상태로 복구
    - 재시도 처리 등
#### 예외처리 회피
- 자신이 담당하지 않고 호출부로 던지기
    - 명확하게 분리된 역할이 뒷받침되어야만 회피가 정당해짐
#### 예외 전환
1. 적절한 의미를 가진 예외로 변경하여 던지기
    - 보다 구체적이고 명시적인 의미 전달
2. 예외처리를 위해 포장
    - 주로 체크 예외를 언체크 예외로
        - 어차피 복구하지 못할 예외라면, 애플리케이션 전역으로 일관되게 처리할 수 있도록 런타임 예외로 포장해서 던진다
- 중첩 예외(nested)로 만드는 것이 좋음(최초 예외를 인지할 수 있도록)

### 4.1.4 예외처리 전략
#### 런타임 예외의 보편화
- 최초의 환경과 현재의 자바 엔터프라이즈 서버환경은 다르다
    - 수많은 사용자의 각 요청은 독립적인 작업으로 취급
        - 하나하나의 작업을 모두 중단시키지 않고 복구하는 개념이 아님
        - 체크 예외의 활용도와 가치가 떨어짐
#### add() 메소드의 예외처리
#### 애플리케이션 예외
- 애플리케이션 로직에서 의도적으로 발생시키는 예외
    - 정상흐름을 두고, 예외 상황에서 비즈니스적인 의미를 띤 예외를 던진다
    - 이 때 checked로 만드는 것을 권장❓
- 리턴값의 분기 (ex, success Y/N) <- 장/단점❓

### 4.1.5 SQLException은 어떻게 됐나?
- 스프링은 복구 불가능한 SQLException을 DataAccessExcepion으로 `포장`해서 던진다
    - checked 예외를 unchecked로 포장
    - 명시적인 의미를 가진 예외로 전환

## 4.2 예외 전환
- DataAccessException
    1. checked를 unchecked로 포장해서 애플리케이션 레벨에서 신경쓰지 않도록
    2. SQLException에 담기 어려운 상세한 예외정보를 의미 있고 일관성 있는 예외로 전환해서 추상화
### 4.1.2 JDBC의 한계
#### 비표준 SQL
1. 표준 SQL만 사용
    - 당장의 페이징 쿼리에서부터 문제 💩
2. DAO를 DB별로 만들어 사용하거나, SQL을 외부에서 독립시켜서 대체가능하도록 ⭐
    - 7장에서 보자 ✈️
#### 호환성 없는 SQLException의 DB 에러정보

### 4.2.2 DB 에러 코드 매핑을 통한 전환
- 각 벤더별 에러코드가 제각각
    - 에러코드 매핑정보 테이블
- DataAccessException을 확장한 각각의 상황에 맞는 예외를 던진다
### 4.2.3 DAO 인터페이스와 DataAccessException 계층구조
- JDBC, JPA, JDO 등 데이터 액세스 기술의 종류와 상관없이 일관되게 DataAccessException으로 처리
#### DAO 인터페이스와 구현의 분리
- 각 데이터 액세스 기술이 던지는 예외 때문에 DAO의 인터페이스 설계에 장애가 있을 수 있다
    - 특정 예외를 throw 해야한다면, 추상화 된 인터페이스가 결국 특정 데이터 액세스 기술에 종속
#### 데이터 액세스 예외 추상화와 DataAccessException 계층구조
- 그래서 스프링은 다양한 데이터 액세스 기술에서 발생하는 예외를 DataAccessException 계층구조로 정리
    - 데이터 액세스 기술과 상관없이 일관되게 처리하는 코드 구현 가능
    - 자세한 내용은 11장에서 보자 ✈️

### 4.2.4 기술에 독립적인 UserDao 만들기
#### 인터페이스 적용
#### 테스트 보완
#### DataAccessException 활용 시 주의사항
- 모든 데이터 액세스 기술에 대해 완벽하게 동일한 처리를 하기엔 한계가 존재
    - 직접 예외를 정의하여 전환하여 일관되게 처리하는 방식의 대처가 필요할 수 있음

### 4.3 정리

