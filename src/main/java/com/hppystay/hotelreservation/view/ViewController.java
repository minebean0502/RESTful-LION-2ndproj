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
    @Value("${TOSS_CLIENT_KEY}")
    private String tossClientKey;

    @Value("${NCP_CLIENT_ID}")
    private String ncpClientKey;

    @RequestMapping("/login")
    public String login() {
        return "login/login";
    }

    // 호텔 생성 view 테스트
    @GetMapping("/hotel/create-view")
    public String hotelCreate() {
        return "create-hotel";
    }

    @GetMapping("/hotel/update-view/{id}")
    public String hotelUpdate(
            @PathVariable("id")
            Long id
    ) {
        return "update-hotel";
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

    @GetMapping("/my-page/submit-businessNum")
    public String submitPage() {
        return "test/submit-business-num";
    }

    // 애도 윈도우인데 설정 안해도 되나봄?
    @GetMapping("/my-page/reservation/transfer")
    public String transferToMember() {
        return "reservation/transfer/transfer-to-member";
    }

    // 이건 마이페이지에서 결제 이어지는 부분 // 윈도우임
    @GetMapping("/my-page/payment/start")
    public String continuePaymentWindow() {
        return "toss/continue-payment";
    }

    // 이건 호텔 상세 페이지에서 결제 이어지는 부분 // 윈도우임
    // TODO hotel-detail-test 이후에 저거 맞는지 확인 필요함
    @GetMapping("/hotel/search/{hotelId}")
    public String doPaymentWindow(
            @PathVariable("hotelId")
            String hotelId)
    {
        return "toss/do-payment";
    }

    @GetMapping("/my-page/reservation/transfered")
    public String transferedAndDoPay() {
        return "reservation/transfer/transfered-by-member";
    }

    @GetMapping("/hotel/1/details")
    public String oneHotelDetails() {
        return "temphtml/hotelDetails";
    }

    // 호텔 예약 완료(가정) 후 toss 결제용 view
    @GetMapping("/hotel/test")
    public String tossTestHotel1Room1to3(Model model) {
        model.addAttribute("clientKey", tossClientKey);
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
    @GetMapping("/hotel/inquiries/list")
    public String hotelDetailsSomin() {
        return "temphtml/hotelDetails-somin";
    }

    @GetMapping("/hotel/inquiries/submit/{hotelId}")
    public String submitInquiry() {
        return "inquiries/submitInquiry";
    }

    @GetMapping("/hotel/inquiries/update/{id}")
    public String updateInquiry() {
        return "inquiries/submitInquiry";
    }


    //임시
    @GetMapping("/hotel/37/details/m")
    public String hotelDetailsm() {
        return "temphtml/hotelDetails-m1";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

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
        return "hotel-detail-test";
    }

    @GetMapping("/hotel/management")
    public String hotelManagementView() {
        return "hotel-management";
    }
}