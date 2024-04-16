package com.hppystay.hotelreservation.payment.toss.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempRootController {
    @GetMapping("/reservations")
    public String root() {
        return "toss/reservations";
    }
}
