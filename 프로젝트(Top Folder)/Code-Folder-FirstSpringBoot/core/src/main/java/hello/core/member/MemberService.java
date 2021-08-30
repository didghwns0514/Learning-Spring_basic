package hello.core.member;

import org.springframework.stereotype.Component;

@Component
public interface MemberService {
    /* 회원 가입, 회원 조회의 2 기능이 있어야 함 */
    void join(Member member);

    Member findMember(Long memberId);
}
