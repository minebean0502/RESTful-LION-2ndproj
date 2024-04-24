package com.hppystay.hotelreservation.hotel.inquiry.repository;

import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByHotelInquiryId(Long hotelInquiryId);
}
