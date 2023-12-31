package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // << 테스트 케이스에 달면 테스트를 실행할 때 트랜잭션을 먼저 실행하고 디비에 데이터를 insert 등 다하고 테스트가 끝나면 rollback함
               // 따라서 디비에 넣었던 데이터가 깔끔하게 반영이 안되고 다 지워짐
class MemberServiceIntegrationTest {  // shift f10 이전 실행을 다시 돌려줌

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test // 메서드 명을 한글로 해도 됨
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring100");

        //when
        Long saveId = memberService.join(member); // 자동 포매팅 ctrl alt v

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
//        Assertions.assertThat >> static import : alt enter
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();      // shift f6 단어 바꿈
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}