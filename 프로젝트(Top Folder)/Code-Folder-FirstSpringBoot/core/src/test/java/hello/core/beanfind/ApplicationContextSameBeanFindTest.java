package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac;

    // Spring AppConfig마냥 새로운 Configuration 추가하기 위해 테스트코드 안에서 수행
    // Class 안의 class로 해당 class의 context, scope을 포함된 class안에서까지만 사용하겠다는 것
    @BeforeEach
    void beforeEach() {
        ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
    }

    @Configuration
    static class SameBeanConfig{

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }


    @Test
    @DisplayName("타입으로 조회시, 같은 타입이 있으면 중복 오류 발생")
    void findSameTypeByType() {

        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class)); // lambda: 익명함수

    }

    @Test
    @DisplayName("타입으로 조회시, 같은 타입이 있으면 빈 이름 지정으로 오류 피함")
    void findSameTypeByName() {

        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository1).isInstanceOf(MemberRepository.class);

    }

    @Test
    @DisplayName("특정 타입을 모두 조회")
    void findBeanTypeByType() {

        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value = " + beansOfType.get(s));

        }
        System.out.println("beans of type = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);

    }



}
