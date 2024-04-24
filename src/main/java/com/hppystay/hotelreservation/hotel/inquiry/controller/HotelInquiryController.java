package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 이 컨트롤러는 호텔 문의사항에 관한 API 엔드포인트를 제공합니다.
 * 사용자는 호텔에 대한 문의사항을 제출, 수정, 삭제할 수 있습니다.
 */
@RestController
@RequestMapping("/api/hotel/inquiries")
public class HotelInquiryController {

    private final HotelInquiryService hotelInquiryService;

    @Autowired
    public HotelInquiryController(HotelInquiryService hotelInquiryService) {
        this.hotelInquiryService = hotelInquiryService;
    }

    /**
     * 새로운 호텔 문의사항을 제출합니다.
     * 사용자가 제출한 문의사항은 데이터베이스에 저장됩니다.
     *
     * @param userDetails 현재 로그인한 사용자의 정보
     * @param hotelInquiryDto 문의사항 내용을 담은 DTO
     * @return 성공 시 "Inquiry created successfully" 메시지와 함께 OK 응답을 반환
     */
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

    /**
     * 기존 문의사항을 수정합니다.
     * 문의사항 ID와 새로운 문의 내용을 기반으로 문의사항을 업데이트합니다.
     *
     * @param userDetails 현재 로그인한 사용자의 정보
     * @param id 수정할 문의사항의 ID
     * @param hotelInquiryDto 새로운 문의사항 내용을 담은 DTO
     * @return 성공 시 "Inquiry updated successfully" 메시지와 함께 OK 응답을 반환
     */
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

    /**
     * 사용자가 제출한 문의사항을 삭제합니다.
     * 문의사항 ID를 기반으로 해당 문의사항을 데이터베이스에서 삭제합니다.
     *
     * @param id 삭제할 문의사항의 ID
     * @param currentUser 현재 로그인한 사용자의 정보
     * @return 성공 시 "Inquiry deleted successfully" 메시지와 함께 OK 응답을 반환
     */
    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteInquiry(
            @PathVariable("id") Integer id,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        hotelInquiryService.deleteInquiry(id, currentUser.getUsername());
        return ResponseEntity.ok().body("Inquiry deleted successfully");
    }

    /**
     * 모든 호텔 문의사항의 목록을 페이지네이션하여 조회합니다.
     * 클라이언트는 페이지네이션 파라미터를 이용해 요청할 수 있으며,
     * 응답은 Page<HotelInquiryDto> 형태의 JSON 데이터로 제공됩니다.
     *
     * @param pageable 페이지네이션 정보(페이지 번호, 페이지 크기 등)를 포함하는 Pageable 객체
     * @return 페이지네이션 처리된 호텔 문의사항 목록
     */
    @GetMapping("/list")
    public ResponseEntity<Page<HotelInquiryDto>> listInquiries(Pageable pageable) {
        Page<HotelInquiryDto> inquiries = hotelInquiryService.getAllInquiries(pageable);
        return ResponseEntity.ok(inquiries);
    }

}

