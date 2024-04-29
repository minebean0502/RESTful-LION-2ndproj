package com.hppystay.hotelreservation.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ViewController {
    @Value("${TOSS_CLIENT_KEY}")
    private String clientKey;

    @RequestMapping("/login")
    public String login() {
        return "login/login";
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

    @RequestMapping("/denied")
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

    @GetMapping("/my-page")
    public String myPage() {
        return "test/mypagetest";
    }

    @GetMapping("/my-page/reservation/transfer")
    public String transferToMember() {
        return "reservation/transfer/transfer-to-member";
    }

    @GetMapping("/my-page/reservation/transfered")
    public String transferedAndDoPay() {
        return "reservation/transfer/transferd-by-member";
    }

    @GetMapping("/hotel/1/details")
    public String oneHotelDetails() {
        return "temphtml/hotelDetails";
    }

    // 호텔 예약 완료(가정) 후 toss 결제용 view
    @GetMapping("/hotel/test")
    public String tossTestHotel1Room1to3(Model model){
        model.addAttribute("clientKey", clientKey);
        return "toss/reservation";
    }

    // 결제 성공 후 toss redirect view
    @GetMapping("/hotel/test/paymentComplete")
    public String tossTestHotelPaySuccess() {
        return "toss/success";
    }
    // 결제 실패 후 toss redirect view
    @GetMapping("/hotel/test/paymentFail")
    public String tossTestHotelPayFail() {
        return "toss/Fail";
    }


    //임시로 만듦
    @GetMapping("/hotel/1/details/somin")
    public String hotelDetailsSomin() {
        return "temphtml/hotelDetails-somin";
    }

    @GetMapping("/hotel/inquiries/submit")
    public String submitInquiry() { return "inquiries/submitInquiry"; }

    //임시
    @GetMapping("/hotel/37/details/m")
    public String hotelDetailsm() {
        return "temphtml/hotelDetails-m1";
    }
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}