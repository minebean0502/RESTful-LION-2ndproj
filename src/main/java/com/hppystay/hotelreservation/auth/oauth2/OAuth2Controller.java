package com.hppystay.hotelreservation.auth.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
public class OAuth2Controller {
    private final OAuth2UserService oAuth2UserService;

    @GetMapping("/get-token")
    public TokenDto getAccessToken(
            @RequestParam("uuid")
            String uuid
    ) {
        return oAuth2UserService.getAccessToken(uuid);
    }
}
