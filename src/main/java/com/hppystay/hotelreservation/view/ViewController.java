package com.hppystay.hotelreservation.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Slf4j
@Controller
public class ViewController {
    @Value("${NCP_CLIENT_ID}")
    private String ncpClientKey;
    @GetMapping("/admin")
    public String admin() {
        return "common/admin";
    }
    @RequestMapping("/denied")
    public String denied() {
        return "common/denied";
    }
    @GetMapping("/main")
    public String mainPage() {
        return "common/main";
    }

    @RequestMapping("/login")
    public String login() {
        return "login/login";
    }
    @RequestMapping("/password-reset")
    public String passwordReset() {
        return "login/pwfind";
    }


    // 마이 페이지 관련
    @GetMapping("/my-page")
    public String myPage() {return "/mypage/my-page";}

    // 마이 페이지 - 사업자 번호 제출
    @GetMapping("/my-page/submit-businessNum")
    public String submitPage() {
        return "mypage/submit-business-num";
    }

    // 마이페이지 - 양도 송신 - A -> B
    @GetMapping("/my-page/reservation/transfer")
    public String transferToMember() {
        return "mypage/transfer-to-member";
    }

    // 마이페이지 - 양도 수신 - B <- A
    @GetMapping("/my-page/reservation/transfered")
    public String transferedAndDoPay() {
        return "mypage/transfered-by-member";
    }

    // 호텔 - 검색
    @GetMapping("/hotel/search")
    public String hotelSearch(
            @RequestParam("keyword")
            String keyword,
            @RequestParam("checkIn")
            LocalDate checkIn,
            @RequestParam("checkOut")
            LocalDate checkOut,
            @RequestParam(value = "sort", required = false, defaultValue = "title")
            String sort,
            Model model
    ) {
        if (checkIn.isAfter(checkOut)) checkOut = checkIn.plusDays(1);
        model.addAttribute("keyword", keyword);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("sort", sort);
        return "hotel/hotel-list-search";
    }

    // 호텔 매니저 페이지
    @GetMapping("/hotel/management")
    public String hotelManagementView() {
        return "/hotel/hotel-management";
    }

    // 호텔 - 상세 페이지
    @GetMapping("/hotel/{hotelId}")
    public String hotelDetailView(
            @PathVariable("hotelId")
            Long hotelId,
            @RequestParam("checkIn")
            LocalDate checkIn,
            @RequestParam("checkOut")
            LocalDate checkOut,
            Model model
    ) {
        if (checkIn.isAfter(checkOut)) checkOut = checkIn.plusDays(1);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("clientId", ncpClientKey);
        return "hotel/hotel-list-detail";
    }

    // 호텔 - 문의사항
    @GetMapping("/hotel/inquiries/submit/{hotelId}")
    public String submitInquiry() {
        return "hotel/hotel-submit-Inquiry";
    }

    // 호텔 - 문의사항 업데이트
    @GetMapping("/hotel/inquiries/update/{id}")
    public String updateInquiry() {
        return "hotel/hotel-update-Inquiry";
    }

    // 호텔 생성
    @GetMapping("/hotel/create-view")
    public String hotelCreate() {
        return "hotel/create-hotel";
    }

    // 호텔 수정 [매니저]
    @GetMapping("/hotel/update-view/{id}")
    public String hotelUpdate(
            @PathVariable("id")
            Long id
    ) {
        return "hotel/update-hotel";
    }

    // TOSS - 호텔 결제
    @GetMapping("/hotel/payment/start")
    public String doPaymentWindow() {
        return "toss/do-payment";
    }

    // TOSS - 미결제의 결제 (미결제의 결제)
    @GetMapping("/my-page/payment/start")
    public String continuePaymentWindow() {return "toss/continue-payment";}

}