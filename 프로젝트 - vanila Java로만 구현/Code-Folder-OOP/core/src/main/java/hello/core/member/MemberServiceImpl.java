package hello.core.member;

public class MemberServiceImpl implements MemberService{
    /* 관례상 interface를 impl하는게 1개면 클래스 뒤에 impl붙여줌 */

    /* 이거 지정 안해주면 null point exception 나서 코드가 터짐(당연) */
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {

        return memberRepository.findById(memberId);
    }
}
