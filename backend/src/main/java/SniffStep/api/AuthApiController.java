package SniffStep.api;

import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/auth")
public class AuthApiController {

    private final MemberRepository memberRepository;

    public AuthApiController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<Member> login(@RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        return ResponseEntity.ok(member);
    }
}
