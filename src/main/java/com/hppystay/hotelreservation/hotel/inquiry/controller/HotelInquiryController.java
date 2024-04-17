package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            //TODO 로그인한 사용자 ID 입력
            //@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody
            HotelInquiryDto hotelInquiryDto
    ) {

        // userDetails에서 사용자 ID를 가져옴.
        //Integer writerId = userDetails.getId();

        // 또는 SecurityContextHolder 활용
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String username = authentication.getName(); // 현재 로그인한 사용자의 이름(또는 아이디)를 가져옵니다.
        // username을 바탕으로 사용자의 ID를 조회
        // 실제로는 사용자 엔티티에서 ID를 가져와야 한다.
        //Integer writerId = userService.findIdByUsername(username);


        Integer writerId = 23;  // 임시로 정적 설정.
        Integer hotelId = 107;  // 임시로 정적 설정.


        HotelInquiryDto createdInquiry = hotelInquiryService.createInquiry(hotelInquiryDto, writerId, hotelId);
        return ResponseEntity.ok(createdInquiry);
    }

    //TODO CORS -> PUT
    @PostMapping("/update/{id}")
    public ResponseEntity<HotelInquiryDto> updateInquiry(
            @PathVariable("id") Integer id,
            @RequestBody  HotelInquiryDto hotelInquiryDto
    ) {
        HotelInquiryDto updatedInquiry = hotelInquiryService.updateInquiry(id, hotelInquiryDto);
        return ResponseEntity.ok(updatedInquiry);
    }

    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable("id") Integer id) {
        hotelInquiryService.deleteInquiry(id);
        return ResponseEntity.ok().build();
    }


}

