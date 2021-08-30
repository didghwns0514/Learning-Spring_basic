package hello.core.member;

public class MemberServiceImpl implements MemberService{
    /* 관례상 interface를 impl하는게 1개면 클래스 뒤에 impl붙여줌 */

    /* 이거 지정 안해주면 null point exception 나서 코드가 터짐(당연) */
    private final MemberRepository memberRepository; // 이 부분에서 생성자는 구현체에 대한 코드가 1줄도 없음

    public MemberServiceImpl(MemberRepository memberRepository) { // 이부분에서 생성하여 주입해줌
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {

        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
