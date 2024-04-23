package com.hppystay.hotelreservation.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) boolean error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model
    ) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
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

    @GetMapping("/is-login")
    public String isLogin() {
        return "is-login";
    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }
}
