package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    void join() {
        final Member member = new Member();
        member.setName("spring");
        final Long saveId = memberService.join(member);
        final Member findMember = memberService.findOne(saveId).get();

        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        final Member member = new Member();
        member.setName("spring");

        final Member member1 = new Member();
        member1.setName("spring");

        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member1));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


  /*      try {
            memberService.join(member1);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
    }

    @Test
    void findMembers() {
        final Member member = new Member();
        member.setName("spring");
        memberService.join(member);
        final List<Member> members = memberService.findMembers();

        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void findOne() {
        final Member member = new Member();
        member.setName("spring");
        memberService.join(member);

        Member one = memberService.findOne(member.getId()).get();

        assertThat(one).isEqualTo(member);

    }
}