package SniffStep.controller;

import SniffStep.common.config.oauth.GetSocialOAuthRes;
import SniffStep.common.config.oauth.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;


    @GetMapping("/{type}")
    public void socialLoginRequest(@PathVariable("type")String type, HttpServletResponse response) throws IOException {
        String requestURL = oAuthService.request(type.toUpperCase());
        response.sendRedirect(requestURL);
    }

    @GetMapping("/oauth2/{type}/redirect")
    public ResponseEntity<?> callback(@PathVariable("type")String type, @RequestParam("code") String code) throws IOException {

         GetSocialOAuthRes res = oAuthService.oAuthLogin(code, type.toUpperCase());
         return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/oauth2/authorization/{provider}")
    public void oauth2Authorization(@PathVariable("provider") String provider, HttpServletResponse response) throws IOException {
        String requestURL = oAuthService.request(provider);
        response.sendRedirect(requestURL);
    }

    @GetMapping("/oauth2/code/google")
    public String home() {
        return "redirect:/";
    }

}
