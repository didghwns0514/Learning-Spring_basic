package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmout = 1000;

    @Override
    public int discount(Member member, int price) {

        // Enum type 은 비교할 때 == 사용하는 것이 맞음
        if (member.getGrade() == Grade.VIP){
            return discountFixAmout;
        } else{
            return 0;
        }
    }
}
