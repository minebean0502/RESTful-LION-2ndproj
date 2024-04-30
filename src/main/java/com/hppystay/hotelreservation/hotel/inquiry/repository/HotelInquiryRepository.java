package com.hppystay.hotelreservation.hotel.inquiry.repository;

import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelInquiryRepository extends JpaRepository<HotelInquiry, Integer> {
    Page<HotelInquiry> findAllByHotelIdOrderByCreatedAtDesc(Integer hotelId, Pageable pageable);
}
