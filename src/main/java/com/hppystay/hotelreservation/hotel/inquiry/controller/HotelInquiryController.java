package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/hotel/inquiries")
@RequiredArgsConstructor
public class HotelInquiryController {

    private final HotelInquiryService hotelInquiryService;

    @GetMapping("/{hotelId}")
    public ResponseEntity<Page<HotelInquiryDto>> getInquiriesByHotelId(
            @PathVariable("hotelId") Integer hotelId,
            @PageableDefault(size = 10 ) Pageable pageable
    ) {
        return hotelInquiryService.getInquiriesByHotelId(hotelId, pageable);
    }

    @GetMapping("/submit/{id}")
    public String submitInquiry(@PathVariable("id") Integer hotelId, Model model) {
        model.addAttribute("hotelId", hotelId);
        return "hotel/hotel-submit-Inquiry";
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitInquiry(@RequestBody HotelInquiryDto hotelInquiryDto) {
        return hotelInquiryService.createInquiry(hotelInquiryDto);
    }

    @GetMapping("/update/{id}")
    public HotelInquiryDto getInquiryById(@PathVariable("id") Integer inquiryId) {
        return hotelInquiryService.getInquiryById(inquiryId);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateInquiry(@RequestBody  HotelInquiryDto hotelInquiryDto) {
        return hotelInquiryService.updateInquiry(hotelInquiryDto);
    }

    @DeleteMapping("/inquiry/{id}")
    public ResponseEntity<?> deleteInquiry(@PathVariable("id") Integer id) {
        return hotelInquiryService.deleteInquiry(id);
    }

}

