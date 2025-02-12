package SniffStep.controller;

import SniffStep.dto.member.MemberDetailResponse;
import SniffStep.dto.member.MemberResponse;
import SniffStep.dto.member.MemberUpdateResponse;
import SniffStep.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<MemberDetailResponse>> findAllMember() {
        List<MemberDetailResponse> members = memberService.findAllMember();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDetailResponse> findMember(@PathVariable(value = "id") Long id) {
        MemberDetailResponse member = memberService.findMember(id);
        return ResponseEntity.ok(member);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editMember(@PathVariable(value = "id") Long id,
                                        @Valid @ModelAttribute MemberUpdateResponse updateResponse) {
        MemberResponse response = memberService.editMember(id, updateResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(value = "id") Long id) {

        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
