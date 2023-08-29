package practice.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import practice.jdbc.domain.Member;


import java.sql.SQLException;


@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void cruTest() throws SQLException {

        //저장
        Member member = new Member("1", 10000);
        repository.save(member);

        //조회
        Member findMember = repository.findById(member.getMemberId());
        log.info("찾은 멤버={}", findMember);
        Assertions.assertThat(findMember).isEqualTo(member); //@Data의 toString때문에 통과 가능 (인스턴스 주소 비교 x)

        //수정
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        Assertions.assertThat(updatedMember.getMoney()).isEqualTo(20000);
    }

    @Test
    void deleteTest() throws SQLException {
        repository.delete("1");
    }
}