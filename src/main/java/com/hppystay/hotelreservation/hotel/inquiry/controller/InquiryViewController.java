package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel/inquiries")
public class InquiryViewController {
    private final HotelInquiryService hotelInquiryService;

    @Autowired
    public InquiryViewController(
            HotelInquiryService hotelInquiryService
    ) {
        this.hotelInquiryService = hotelInquiryService;
    }

    @GetMapping
    public String listInquiries(Model model) {
        model.addAttribute("inquiries", hotelInquiryService.getAllInquiries());
        return "inquiries/list"; // templates/inquiries/list.html
    }

}
