package com.hppystay.hotelreservation.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @GetMapping("/token/callback")
    public String oAuthCallback(
            @RequestParam("uuid")
            String uuid
    ) {
        return "oauth-redirect";
    }

    // 호텔 생성 view 테스트
    @GetMapping("/hotel/view-test")
    public String hotelCreate() {
        return "create-hotel";
    }

    @GetMapping("/is-login")
    public String isLogin() {
        return "is-login";
    }
}
