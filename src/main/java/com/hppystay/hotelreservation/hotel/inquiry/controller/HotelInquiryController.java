package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel/inquiries")
public class HotelInquiryController {

    private final HotelInquiryService hotelInquiryService;

    @Autowired
    public HotelInquiryController(HotelInquiryService hotelInquiryService) {
        this.hotelInquiryService = hotelInquiryService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitInquiry(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody
            HotelInquiryDto hotelInquiryDto
    ) {
        String writerId = userDetails.getUsername();
        //TODO hotelId 받아오기
        Integer hotelId = 107;  // 임시로 정적 설정.
        hotelInquiryService.createInquiry(hotelInquiryDto, writerId, hotelId);
        return ResponseEntity.ok().body("Inquiry created successfully");
    }

    //TODO CORS -> PUT
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateInquiry(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Integer id,
            @RequestBody  HotelInquiryDto hotelInquiryDto
    ) {
        String currentUsername = userDetails.getUsername();
        hotelInquiryService.updateInquiry(id, hotelInquiryDto, currentUsername);
        return ResponseEntity.ok().body("Inquiry updated successfully");
    }

    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteInquiry(
            @PathVariable("id") Integer id,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        hotelInquiryService.deleteInquiry(id, currentUser.getUsername());
        return ResponseEntity.ok().body("Inquiry deleted successfully");
    }


}

