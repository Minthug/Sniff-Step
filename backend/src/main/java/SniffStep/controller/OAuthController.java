package SniffStep.controller;

import SniffStep.common.config.oauth.GetSocialOAuthRes;
import SniffStep.common.config.oauth.OAuthService;
import SniffStep.common.jwt.handler.LoginSuccessHandler;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class OAuthController {

    private final OAuthService oAuthService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final LoginSuccessHandler loginSuccessHandler;


    @GetMapping("/{type}")
    public void socialLoginRequest(@PathVariable("type")String type, HttpServletResponse response) throws IOException {
        String requestURL = oAuthService.request(type.toUpperCase());
        response.sendRedirect(requestURL);
    }

    @GetMapping("/{type}/redirect")
    public ResponseEntity<?> callback(@PathVariable("type")String type, @RequestParam("code") String code,
                                      HttpServletRequest request, HttpServletResponse response) throws IOException {

     try {
         GetSocialOAuthRes res = oAuthService.oAuthLogin(code, type.toUpperCase());

         String email = res.getEmail();
         String name = res.getName();

         Member member = memberRepository.findByEmail(email).orElseGet(() -> {
             Member newMember = Member.builder()
                     .email(email)
                     .name(name)
                     .role(MemberRole.USER)
                     .build();
             return memberRepository.save(newMember);
         });
         loginSuccessHandler.onAuthenticationSuccess(request, response, null);

         return ResponseEntity.ok(res);
     } catch (JsonProcessingException e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
    }

    @GetMapping("/oauth2/code/google")
    public String home()
    {
        return "home";
    }

}
