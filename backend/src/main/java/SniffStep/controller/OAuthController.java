package SniffStep.controller;

import SniffStep.common.config.oauth.GetSocialOAuthRes;
import SniffStep.service.OAuthService;
import SniffStep.common.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;
    private final JwtService jwtService;


    @GetMapping("/{type}")
    public void socialLoginRequest(@PathVariable("type")String type, HttpServletResponse response) throws IOException {
        String requestURL = oAuthService.request(type.toUpperCase());
        response.sendRedirect(requestURL);
    }

    @GetMapping("/oauth2/{type}/redirect")
    public void callback(@PathVariable("type")String type, @RequestParam("code") String code, HttpServletResponse response) throws IOException {

         GetSocialOAuthRes res = oAuthService.oAuthLogin(code, type.toUpperCase());
         jwtService.sendAccessAndRefreshTokenCookie(response, res.getAccessToken(), res.getRefreshToken());
         response.sendRedirect("http://localhost:3000");
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
