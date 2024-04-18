package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 이 컨트롤러는 호텔 문의사항 목록을 웹 페이지에 표시하기 위한 엔드포인트를 제공합니다.
 * 사용자는 페이징 처리된 문의사항 목록을 볼 수 있습니다.
 */
@Controller
@RequestMapping("/hotel/inquiries")
public class InquiryViewController {
    private final HotelInquiryService hotelInquiryService;

    @Autowired
    public InquiryViewController(HotelInquiryService hotelInquiryService) {this.hotelInquiryService = hotelInquiryService;}

    /**
     * 모든 호텔 문의사항의 목록을 페이징하여 표시합니다.
     * 문의사항은 페이지 당 기본적으로 10개씩 표시됩니다.
     *
     * @param model 뷰에 데이터를 전달하기 위한 모델 객체
     * @param pageable 페이징 처리를 위한 Pageable 객체
     * @return "inquiries/list" 뷰 이름을 반환합니다. 이 뷰는 문의사항 목록을 사용자에게 표시합니다.
     */
    @GetMapping
    public String listInquiries(Model model, @PageableDefault(size = 10) Pageable pageable) {
        model.addAttribute("inquiries", hotelInquiryService.getAllInquiries(pageable));
        return "inquiries/list";
    }

}
