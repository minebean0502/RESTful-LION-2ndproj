package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import java.util.List;

public interface HotelInquiryService {
    List<HotelInquiryDto> getAllInquiries();
    HotelInquiryDto getInquiryById(Integer id);
    HotelInquiryDto createInquiry(HotelInquiryDto hotelInquiryDto, Integer writerId, Integer hotelId);
    HotelInquiryDto updateInquiry(Integer id, HotelInquiryDto hotelInquiryDto);
    void deleteInquiry(Integer id);
}
