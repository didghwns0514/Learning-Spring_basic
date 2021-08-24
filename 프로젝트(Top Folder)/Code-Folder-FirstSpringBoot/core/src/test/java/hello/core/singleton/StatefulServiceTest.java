package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Configuration
    static class TestConfig{
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("상태를 가지는 싱글톤, 스레드에서 문제되는 경우")
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //Thread A 사용자 10000원 주문
        int localPriceA = statefulService1.order("userA", 10000);

        //Thread B 사용자 20000원 주문
        int localPriceB = statefulService2.order("userB", 20000);

        //Thread A 사용자 자신의 주문 금액 조회 - 조회 이전에 B가 끼어들어 주문
//        int priceA = statefulService1.getPrice();
        System.out.println("localPriceA = " + localPriceA); // 20000이 나옴, 10000이 아니라 -> 인스턴스가 스프링 싱글톤 컨테이너로 같기 때문

        Assertions.assertThat(localPriceA).isEqualTo(10000);
    }

}