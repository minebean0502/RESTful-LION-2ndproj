package com.hppystay.hotelreservation.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
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

    // 호텔 생성 view 테스트
    @GetMapping("/hotel/create-view")
    public String hotelCreate() {
        return "create-hotel";
    }

    @GetMapping("/is-login")
    public String isLogin() {
        return "is-login";
    }

    /**
     * 마이페이지 -> 예약현황
     */
    @GetMapping("/mypage/reservations")
    public String myReservations() { return "mypage/my-reservations"; }

    /**
     * 마이페이지 -> 예약현황 -> 양수인 검색
     */
    @GetMapping("/mypage/hotel/reservation/transfer")
    public String transferToMember() { return "reservation/transfer/transfer-to-member"; }

    /**
     * 마이페이지 -> 양도현황
     */
    @GetMapping("/mypage/hotel/reservation/transfer/status")
    public String transferStatus() { return "reservation/transfer/transfer-status"; }


    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/hotel/search")
    public String hotelSearch() {
        return "hotelSearch/hotel-list-search";
    }

    @GetMapping("/password-reset")
    public String passwordReset() {
        return "login/pwfind";
    }
}

