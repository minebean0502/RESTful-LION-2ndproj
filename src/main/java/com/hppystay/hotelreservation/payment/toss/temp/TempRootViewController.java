package com.hppystay.hotelreservation.payment.toss.temp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TempRootViewController {
    @Value("${TOSS_CLIENT_KEY}")
    private String clientKey;

    @GetMapping("/reservations")
    public String root() {
        return "toss/reservations";
    }

    @GetMapping("/reservation")
    public String payReservation(
            @RequestParam("id")
            Long id,
            Model model
    ) {
        model.addAttribute("clientKey", clientKey);
        // 작성중
        return "toss/reservation";
    }

//    @GetMapping("/reservation/success")
//    public String successPay() {
//        return "toss/success";
//    }
}
