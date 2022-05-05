package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    void clear() {
        memberRepository.clear();
    }

    @Test
    void save() {
        final Member member = new Member();
        member.setName("spring");

        memberRepository.save(member);

        final Member result = memberRepository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findByName() {
        final Member member1 = new Member();
        member1.setName("spring1");

        final Member member2 = new Member();
        member2.setName("spring2");

        memberRepository.save(member1);
        final Member findName = memberRepository.findByName("spring1").get();
        assertThat(findName).isEqualTo(member1);
    }

    @Test
    void findAll() {
        final Member member1 = new Member();
        member1.setName("spring1");

        final Member member2 = new Member();
        member2.setName("spring2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> memberAll = memberRepository.findAll();
        assertThat(memberAll.size()).isEqualTo(2);
    }
}