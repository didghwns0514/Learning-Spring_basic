package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac;

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }

    @BeforeEach
    void beforeEach() {
        ac = new AnnotationConfigApplicationContext(TestConfig.class);
    }

    @Test
    @DisplayName("부모 타입 조회시, 자식이 둘 이상 있으면 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate(){
        assertThrows(NoUniqueBeanDefinitionException.class,
                ()->ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입 조회시, 자식이 둘 이상 있으면 이름을 사용하여 조회한다.")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(bean).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    // 상속을 받았기 때문에, 특정 implement된 구체적인 클래스를 써도 됨
    void findBeanByParentTypeCertainBeanType(){
        DiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
    
    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findAllBeanByParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);

        for (String bean : beansOfType.keySet()) {

            assertThat(beansOfType.get(bean)).isInstanceOf(DiscountPolicy.class);
            System.out.println("key = " + bean + " value = " + beansOfType.get(bean));
        }
        System.out.println("beansOfType = " + beansOfType);
    }

    @Test
    @DisplayName("Object 타입으로 모두 조회")
    // Java class는 모두 Object 상속하기 때문에, 모두 조회가 됨
    void findAllBeanByObjectType(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);

        for (String bean : beansOfType.keySet()) {

            System.out.println("key = " + bean + " value = " + beansOfType.get(bean));
        }
        System.out.println("beansOfType = " + beansOfType);
    }
}
