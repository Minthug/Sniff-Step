package SniffStep.controller;

import SniffStep.common.config.oauth.GetSocialOAuthRes;
import SniffStep.common.config.oauth.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class OAuthController {

    private final OAuthService oAuthService;


    @GetMapping("/{type}")
    public void socialLoginRequest(@PathVariable("type")String type, HttpServletResponse response) throws IOException {
        String requestURL = oAuthService.request(type.toUpperCase());
        response.sendRedirect(requestURL);
    }

    @GetMapping("/oauth2/{type}/redirect")
    public ResponseEntity<?> callback(@PathVariable("type")String type, @RequestParam("code") String code,
                                      HttpServletRequest request, HttpServletResponse response) throws IOException {

     try {
         GetSocialOAuthRes res = oAuthService.oAuthLogin(code, type.toUpperCase());

         response.setHeader("Authorization","Bearer " + res.getAccessToken());
         response.setHeader("RefreshToken", res.getJwtToken());

         return ResponseEntity.ok().build();

     } catch (JsonProcessingException e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
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
