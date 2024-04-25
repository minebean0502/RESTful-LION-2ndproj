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
     * 마이페이지에서 '양도하기'버튼을 눌렀을 때,
     * 양수인 검색 페이지로 이동합니다.
     * @return transfer-to-member.html 페이지를 반환합니다.
     */
    @GetMapping("/mypage/hotel/reservation/transfer")
    public String transferToMember() { return "reservation/transfer/transfer-to-member"; }


    /**
     * 마이페이지에서 '양도현황' 탭을 클릭하면
     * 양도받을 수 있는 호텔의 목록을 조회합니다.
     * @return transfer-status.html 페이지를 반환합니다.
     */
    @GetMapping("/mypage/hotel/reservation/transfer/status")
    public String transferStatus() { return "reservation/transfer/transfer-status"; }

    @GetMapping("/mypage/reservations")
    public String myReservations() { return "mypage/my-reservations"; }

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

