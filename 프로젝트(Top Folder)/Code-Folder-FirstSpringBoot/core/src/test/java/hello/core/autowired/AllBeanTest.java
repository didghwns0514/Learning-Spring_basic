package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

public class AllBeanTest {


    static ApplicationContext ac;
    static DiscountService discountService;

    @BeforeAll
    static void beforeAll() {
        ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        discountService = ac.getBean(DiscountService.class);
    }

    @Test
    void finaAllBean() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    @Configuration
    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> polices;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> polices) {
            this.policyMap = policyMap;
            this.polices = polices;

            System.out.println("policyMap = " + policyMap);
            System.out.println("polices = " + polices);
        }

        public int discount(Member member, int price, String discountMethodCode) {
            DiscountPolicy tmpDiscountPolicy = policyMap.get(discountMethodCode);
            return tmpDiscountPolicy.discount(member, price);
        }
    }
}
