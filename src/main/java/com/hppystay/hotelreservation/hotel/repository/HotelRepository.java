package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {}
