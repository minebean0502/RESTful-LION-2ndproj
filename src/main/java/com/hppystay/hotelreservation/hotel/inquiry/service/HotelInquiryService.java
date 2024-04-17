package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelInquiryService {
    Page<HotelInquiryDto> getAllInquiries(Pageable pageable);
    HotelInquiryDto getInquiryById(Integer id);
    HotelInquiryDto createInquiry(HotelInquiryDto hotelInquiryDto, String writerId, Integer hotelId);
    HotelInquiryDto updateInquiry(Integer id, HotelInquiryDto hotelInquiryDto, String currentUsername);
    void deleteInquiry(Integer id, String currentUsername);
}
