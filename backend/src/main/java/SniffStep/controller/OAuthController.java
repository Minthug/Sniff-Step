package SniffStep.controller;

import SniffStep.common.config.oauth.GetSocialOAuthRes;
import SniffStep.common.jwt.service.JwtService;
import SniffStep.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/oauth")
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;
    private final JwtService jwtService;


    @GetMapping("/{type}")
    public ResponseEntity<?> socialLoginRequest(@PathVariable("type")String type) throws IOException {
        String requestURL = oAuthService.request(type.toUpperCase());
        Map<String, String> response = new HashMap<>();

        response.put("url", requestURL);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/oauth2/{type}/redirect")
    public void callback(@PathVariable("type")String type, @RequestParam("code") String code, HttpServletResponse response) throws IOException {

         GetSocialOAuthRes res = oAuthService.oAuthLogin(code, type.toUpperCase());
         jwtService.sendAccessAndRefreshTokenCookie(response, res.getAccessToken(), res.getRefreshToken());

         response.sendRedirect("https://sniffstep.com");
    }

    @GetMapping("/oauth2/authorization/{provider}")
    public ResponseEntity<?> oauth2Authorization(@PathVariable("provider") String provider) throws IOException {
        String requestURL = oAuthService.request(provider);
        Map<String, String> response = new HashMap<>();

        response.put("url", requestURL);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/oauth2/code/google")
    public String home() {
        return "redirect:/";
    }

}
