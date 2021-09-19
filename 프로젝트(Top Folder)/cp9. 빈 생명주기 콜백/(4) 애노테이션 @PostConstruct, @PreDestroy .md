<link href="../md_config/style.css" rel="stylesheet">

# 애노테이션, PostConstruct, PreDestroy

## 1) @PostConstruct, @PreDestory 애노테이션 특징

1. 최신 스프링에서 가장 권장하는 방법
2. 애노테이션 하나만 붙이면 되므로 매우 편리
3. 패키지를 잘 보면 javax.annotation.PostConstruct 이다.  
   -> 스프링에 종속적인 기술이 아니라 JSR-250 라는 자바 표준(인터페이스의 모음). 따라서 스프링이 아닌 다른 컨테이너에서도 동작(스프링에 종속이 아님)
4. 컴포넌트 스캔과 잘 어울림(Bean 등록이 아니므로 === 수동이 아님)
5. 유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야 하면 @Bean의 기능을 사용(수동등록 CH9-3 참조)
6. **`이 방식이 최신 method이므로, 외부 라이브러리 등록 제외, 이렇게 수행하도록 하자!`**

## 2) 예제 코드

- Example

  - JAVA

    ```JAVA

      public class NetworkClient {

          private String url;

          public NetworkClient() {
              // default 생성자
              System.out.println("생성자 호출 url = " + url);

          }

          public void setUrl(String url) {
              this.url = url;
          }

          // 서비스 시작시 호출
          public void connect() {
              System.out.println("connect : " + url); // 실제 붙이지는 않고 호출만 확인
          }

          public void call(String message) {
              System.out.println("call : " + url + " message = " + message);
          }

          // 서비스 종료시 호출
          public void disconnect() {
              System.out.println("close : " + url);
          }

          @PostConstruct
          public void init() {
              // 의존관계 주입이 끝나면 호출해준다는 뜻
              System.out.println("NeworkClient.afterPropertiesSet");
              connect();
              call("초기화 연결 메세지");
          }

          @PreDestroy
          public void close() {
              // 작업 완료 이후, 종료 이전에 수행해야할 것들을 호출해주는 부분
              System.out.println("NetworkClient.destroy");
              disconnect();
          }
      }

      ...


      public class BeanLifeCycleTest {

          @Test
          public void lifeCycleTest() {

              ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
              NetworkClient client = ac.getBean(NetworkClient.class);
              ac.close(); // ConfigurableApplicationContext 를 사용해야 사용 가능, AnnotationConfigApplicationContext 보다 상위 context

          }

          @Configuration
          static class LifeCycleConfig {

              //@Bean(initMethod = "init", destroyMethod = "close")
              @Bean
              public NetworkClient networkClient() {
                  NetworkClient networkClient = new NetworkClient();
                  networkClient.setUrl("http://hello-spring.dev"); // 모종의 이유로, 객체 생성이후 메서드들이 실행되어야 하는 경우가 있음
                  return networkClient;
              }
          }

      }

    ```
