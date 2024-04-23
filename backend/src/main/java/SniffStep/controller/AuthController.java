package SniffStep.controller;

import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;



}
