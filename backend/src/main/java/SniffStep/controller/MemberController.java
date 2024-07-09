package SniffStep.controller;

import SniffStep.common.exception.AccessDeniedException;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberResponseDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PatchMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editMember(@PathVariable(value = "id") Long id,
                                        @Valid @ModelAttribute MemberUpdateDTO memberUpdateDTO) {
        memberService.editMemberV2(id, memberUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(value = "id") Long id) {

        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
