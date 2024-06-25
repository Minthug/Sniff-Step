package SniffStep.controller;

import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<MemberDTO>> findAllMember() {
        List<MemberDTO> members = memberService.findAllMember();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findMember(@PathVariable(value = "id") Long id) {
        MemberDTO member = memberService.findMember(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editMember(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody MemberUpdateDTO memberUpdateDTO) {
        memberService.editMember(id, memberUpdateDTO);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(value = "id") Long id) {

        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
