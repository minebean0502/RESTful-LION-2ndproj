package com.hppystay.hotelreservation.hotel.inquiry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelInquiryViewController {

    /**
     * 호텔 문의사항 목록 페이지로 이동합니다.
     * @return inquiriesList.html 페이지를 반환합니다.
     */
    @GetMapping("/hotel/inquiries")
    public String showInquiriesList() {
        return "inquiries/inquiriesList";
    }
}
