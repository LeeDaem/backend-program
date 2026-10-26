package me.dkLee.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // findAll() : select * from member;
    // findById() : select * from member where id = ?
    // deleteAll() : delete * from member;
    // deleteById() : delete * from member where id = ?
    // save() : insert into member(id, name) values (?, ?), update member set ... where id = ?

    // 없는거는 아래처럼 만들면 사용가능
    // 없는 이유는 모든 테이블에 name, email 같은 류가 있는게 아니니까 하지만 ID는 기본으로 만들게 되어있음
    // findByName() : select * from member where name = ?
//    public List<Member> findByName(String name);
    public Optional<Member> findByName(String name);
    // select * from member where name = ? limit 1; TOP-N query의 SQL문은 DBMS마다 다름

    // findByEmail() : select * from member where email = ?
    // public Optional<Member> findByEmail(String email);

    // findByNameAndEmail() :select * from member where name = ? and email = ?
    // public Optional<member> findByNameAndEmail(String name, String email);
}
