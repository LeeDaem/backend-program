package me.dkLee.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 전체 회원 조회
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 멤버 저장
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }



}
