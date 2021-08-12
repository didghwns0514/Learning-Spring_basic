package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // ApplicationContext가 @Bean의 메서드들로 만들어지는 객체를 모두 관리해줌
        // 생성은 AnnotationConfigApplicationContext 를 구현체로 하여 만듦, parameter는 AppConfig클래스
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // getBean으로 객체를 가져오는데, 관리는 객체 이름들로 되고, 선택도 이름으로 해야 함, 타입도 지정
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // bean에서 가져올 때는 bean으로

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }

}
