package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.HotelInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/hotel/inquiries")
public class HotelInquiryController {

    private final HotelInquiryService hotelInquiryService;

    @Autowired
    public HotelInquiryController(HotelInquiryService hotelInquiryService) {
        this.hotelInquiryService = hotelInquiryService;
    }

    @GetMapping
    public String listInquiries(Model model) {
        model.addAttribute("inquiries", hotelInquiryService.getAllInquiries());
        return "inquiries/list"; // templates/inquiries/list.html
    }

    @GetMapping("/new")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiryDto", new HotelInquiryDto());
        return "inquiries/form"; // templates/inquiries/form.html
    }

    @PostMapping
    public String submitInquiry(
            //@AuthenticationPrincipal CustomUserDetails userDetails,
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

        // 임시로 정적 설정.
        Integer writerId = 23;


        // hotelId는 특정 호텔을 가리키는 ID로,
        // 임시로 정적 설정.
        Integer hotelId = 107;


        hotelInquiryService.createInquiry(hotelInquiryDto, writerId, hotelId);
        return "redirect:/api/hotel/inquiries";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        HotelInquiryDto inquiry = hotelInquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        return "inquiries/edit";
    }

    @PostMapping("/update")
    public String updateInquiry(HotelInquiryDto hotelInquiryDto) {
        hotelInquiryService.updateInquiry(hotelInquiryDto.getId(), hotelInquiryDto);
        return "redirect:/api/hotel/inquiries";
    }

    // 문의사항 삭제 처리
    @DeleteMapping("/delete/{id}")
    public String deleteInquiry(@PathVariable("id") Integer id) {
        hotelInquiryService.deleteInquiry(id);
        return "redirect:/api/hotel/inquiries";
    }


}

