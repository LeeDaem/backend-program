package me.dkLee.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
// Repository 테스트할때는 DataJpaTest 어노테이션을 사용해야한다
// 테스트 코드를 실행하고 나서는 롤백시켜준다 여러 테스트를 했을때 다른 테스트에 결과가 안바뀌도록 롤백
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    // 이걸하면 getAllMembers하기전에 실행시켜줌
    @DisplayName("member 테이블의 모든 레코드 가져오기")
    @Test
    public void getAllMembers() {
        // 준비 given (레코드 삽입)

        // 실행 when
        List<Member> members = memberRepository.findAll();

        // 검증 then
        // 객체가 3개이므로 사이즈 3으로 검증
        assertThat(members.size()).isEqualTo(3);    // 옆에 숫자 누르면 브레크해서 디버깅할수있음
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 id가 2인 레코드 가져오기")
    @Test
    public void getMemberById2() {
        // 준비 given

        // 실행 when
        Member member = memberRepository.findById(2L).get();

        // 검증 then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @DisplayName("member 테이블에서 name이 'B'인 레코드 가져오기")
    @Test
    public void getMemberByName() {
        // 준비 given

        // 실행 when
        // List<Member> members = memberRepository.findByName("B");   // 여러개가 나오거나 없을수 있으므로 List로 받아야함
        Member member = memberRepository.findByName("B").get();
        // 검증 then
//        assertThat(members.size()).isEqualTo(1);
//        assertThat(members.get(0).getId()).isEqualTo(2);
        assertThat(member.getId()).isEqualTo(2L);
    }

    //save, saveAll, delete, deleteAll
    @DisplayName("하나의 member 객체를 DB에 저장하기")
    @Test
    public void saveMember() {
        // given 준비 : 데이터 준비
        Member member = new Member("A");

        // when 실행
        memberRepository.save(member);

        // then 검증
        List<Member> members = memberRepository.findAll();
        assertThat(members.get(0).getId()).isEqualTo(1L);
    }

    @DisplayName("여러개의 member 객체를 DB에 저장하기")
    @Test
    public void saveMembers() {
        // given 준비 : 데이터 준비
//        List<Member> member = new ArrayList<>();    // List에 Member가 있어서 ArrayList에는 생략할수있다
//        for (int i = 0; i < 3; i++) {
//            members.add(new Member("A" + i + 1));
//        }

        List<Member> members = List.of(new Member("A1"), new Member("A2"), new Member("A3"));

        // when 실행
        memberRepository.saveAll(members);

        // then 검증
        assertThat(memberRepository.findAll().size()).isEqualTo(3);
//        assertThat(members.size()).isEqualTo(3);

    }
    @Sql("/insert-members.sql")
    @DisplayName("id가 2인 레코드 삭제하기")
    @Test
    public void deleteMemberById() {
        // 준비 given
        // @Sql로 준비단계 생략
        // 실행 when
        memberRepository.deleteById(2L);
        // 검증 then
        Optional<Member> member = memberRepository.findById(2L);
        assertThat(member.isEmpty()).isTrue();
        // assertThat(member).isEmpty();
    }

    @Sql("/insert-members.sql")
    @DisplayName("모든 레코드 삭제하기")
    @Test
    public void deleteAllMembers() {
        // 준비 given
        // @Sql로 준비단계 생략
        // 실행 when
        memberRepository.deleteAll();
        // 검증 then
        List<Member> members = memberRepository.findAll();
        assertThat(members.isEmpty()).isTrue();
    }

    // 내부적으로 사용되는 명속성 컨텍스트으 변경감지(dirty-checking) 기능을 이용한 레코드 변경
    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 이용해 id가 2인 레코드의 name을 '홍길동'으로 바꾸기")
    @Test
    public void updateMember() {
        // 준비 give
        Member member = memberRepository.findById(2L).get();
        // 실행 when
        member.setName("홍길동");
        // 검증 then
        member = memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("홍길동");
    }
}