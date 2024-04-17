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
    public ResponseEntity<HotelInquiryDto> submitInquiry(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody
            HotelInquiryDto hotelInquiryDto
    ) {

        String writerId = userDetails.getUsername();
        Integer hotelId = 107;  // 임시로 정적 설정.

        HotelInquiryDto createdInquiry = hotelInquiryService.createInquiry(hotelInquiryDto, writerId, hotelId);
        return ResponseEntity.ok(createdInquiry);
    }

    //TODO CORS -> PUT
    @PostMapping("/update/{id}")
    public ResponseEntity<HotelInquiryDto> updateInquiry(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("id") Integer id,
            @RequestBody  HotelInquiryDto hotelInquiryDto
    ) {
        String currentUsername = userDetails.getUsername();
        HotelInquiryDto updatedInquiry = hotelInquiryService.updateInquiry(id, hotelInquiryDto, currentUsername);
        return ResponseEntity.ok(updatedInquiry);
    }

    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInquiry(
            @PathVariable("id") Integer id,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        hotelInquiryService.deleteInquiry(id, currentUser.getUsername());
        return ResponseEntity.ok().build();
    }


}

