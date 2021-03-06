<link href="../md_config/style.css" rel="stylesheet">

# 관심사의 분리

## 1) 역할 배정?

- 어플리케이션을 하나의 공연에 대입해보자
- 각각의 인터페이스를 배역(배우의 role)이라고 하자 - 이때, 구현(실제 배우)과 역할(role)까지는 잘 분리되어있음
- 이때, 실제 배역에 맞는 배우를 선택하는 것은 누가 하는 것일까?
  - <span style="color:red">특정 "역할"을 "구현"하는 구현체의 선택을 다른 곳에서 해주어야 함(역할의 분리)</span>
  - 기존 코드는 **`구현체`** 로 역할을 몰아준 개념과 동일

## 2) 구현체 분리의 해결

- <span style="color:red">생성자 주입 - injection</span>
- <span style="color:red">OR 의존관계주입, 의존성 주입</span>
- **`cmd + E`** 과거 사용한 것들 단축키로 보여줌

  > <span style="font-weight:bold">Apps Config : 어플리케이션의 전체 동작 방식을 구성하기 위해, *구현객체*를 생성하고 *연결*하는 책임을 가지는 별도의 클래스를 생성</span>
  >
  > 1. 설정과 매우 유사
  > 2. 어플리케이션 구동의 전반을 책임지는 역할을
  > 3. JAVA에서 실제 동작에 필요한 구현 객체를 이부분에서 생성
  >    > AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.  
  >    > ex) MemberServiceImpl, MemoryMemberRepository, OrderServiceImpl, FixDiscountPolicy  
  >    > AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다.  
  >    > ex) MemberServiceImpl, MemoryMemberRepository, OrderServiceImpl MemoryMemberRepository , FixDiscountPolicy
  > 4. Impl 입장에서 어떤 객체가 주입될지 모르고, 그 주입 대상은 app config에서 주입
  > 5. Impl은 오로지 실행에만 집중, 추상까지만 알고있기 때문에 이런식으로 구현할 수 있는 것

- Example

  <img src="images/2021-08-02-17-15-23.png" />

  <img src="images/2021-08-02-16-47-31.png" />

  - 구체적인 구현 클래스에 대해서 impl 단은 정보를 모르는 상태, 역할을 구현할 구현체를 select하는 곳은 다른 곳에서 한번에!
  - Final : 반드시 할당을 해주어야 함, null point exception

## 3) AppConfig refactoring

- Refacotring 이유

  - 역할에 따른 구현이 명확하지 않음
  - 중복이 있음
  - 기대하는 방식 (한번에 밑에처럼 바로 이해할 수 있는, 드러나는 코드를 작성하고 싶음)

    <img src="images/2021-08-03-22-54-21.png" />

- Refactoring 단축키
  - **`cmd + option + m`** (안되면 두번 type)
- Refactoring

  - 어떤 구현체를 선택하였고, 그렇게 생성한 구현체를 다시 주입하는 과정이 모두 잘 드러나있음
  - Code Example

    ```JAVA
       public class AppConfig {

          public MemberService memberService(){

              return new MemberServiceImpl(memberRepository());
          }

          private MemberRepository memberRepository() {
              return new MemoryMemberRepository();
          }

          public OrderService orderService(){

              return new OrderServiceImpl(memberRepository(), discountPolicy());
          }

          private DiscountPolicy discountPolicy() {
              return new RateDiscountPolicy();
          }

       }
    ```

  - 이 방식의 장점
    - new MemoryMemberRepository() 이 부분이 중복 제거  
      이제 MemoryMemberRepository 를 다 른 구현체로 변경할 때 한 부분만 변경하면 됨(구현체 돌려주는 private 부분)
    - AppConfig 를 보면 역할과 구현 클래스가 한눈에 들어옴, 이때 어플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있음
