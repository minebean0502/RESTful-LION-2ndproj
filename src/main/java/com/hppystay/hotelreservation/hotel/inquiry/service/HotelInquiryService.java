package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HotelInquiryService {
    Page<HotelInquiryDto> getAllInquiries(Pageable pageable);
    HotelInquiryDto getInquiryById(Integer id);
    void createInquiry(HotelInquiryDto hotelInquiryDto, String writerId, Integer hotelId);
    void updateInquiry(Integer id, HotelInquiryDto hotelInquiryDto, String currentUsername);
    void deleteInquiry(Integer id, String currentUsername);
}
