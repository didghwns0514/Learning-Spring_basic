package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {


    MemberService memberService;
    OrderService orderService;

    @BeforeEach // 각 테스트 전 돌아가는 함수
    public void beforeEach(){

        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();

    }

    @Test
    void createOrder() {

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "ItemA", 10000);

        Assertions.assertEquals(order.getDiscountPrice(), 1000);

    }

    @Test
    @DisplayName("더미 테스트")
    void createOrderDummyTest() {
        OrderServiceImpl orderService = new OrderServiceImpl( new MemoryMemberRepository(), new RateDiscountPolicy());
    }
}
