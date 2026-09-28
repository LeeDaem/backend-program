package me.dkLee.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }


    // 회원정보를 등록하는 요청
    // hhtp://localhost:8080/member 요청을 POST 방식으로 했을 때 회원 등록을 처리하도록 구현
    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        // 비지니스 로직 호출
        // return ResponseEntity.ok(memberService.saveMember(member));
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(member));
    }
}
