package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.mapper.HotelInquiryMapper;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.PermissionDeniedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
public class HotelInquiryService {
    private final HotelInquiryRepository hotelInquiryRepository;
    private final AuthenticationFacade facade;

    public HotelInquiryDto getInquiryById(Integer inquiryId) {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry with id " + inquiryId + " does not exist."));

        Long memberId = facade.getCurrentMember().getId();
        checkPermission(hotelInquiry.getWriterId(), memberId);

        return HotelInquiryMapper.toDto(hotelInquiry);
    }

    public ResponseEntity<?> createInquiry(HotelInquiryDto hotelInquiryDto) {
        Long memberId = facade.getCurrentMember().getId();
        hotelInquiryDto.setWriterId(memberId);

        String memberName = facade.getCurrentMember().getEmail();
        hotelInquiryDto.setWriter(memberName);

        HotelInquiry hotelInquiry = HotelInquiryMapper.toEntity(hotelInquiryDto);
        hotelInquiryRepository.save(hotelInquiry);

        return ResponseEntity.ok(Map.of("message", "Inquiry created successfully"));
    }

    public ResponseEntity<?> updateInquiry(HotelInquiryDto hotelInquiryDto) {
        Long memberId = facade.getCurrentMember().getId();
        HotelInquiry hotelInquiry = findInquiryById(hotelInquiryDto.getId());
        checkPermission(hotelInquiry.getWriterId(), memberId);

        if (hotelInquiryDto.getTitle() != null) {hotelInquiry.setTitle(hotelInquiryDto.getTitle());}
        if (hotelInquiryDto.getContent() != null) {hotelInquiry.setContent(hotelInquiryDto.getContent());}
        if (hotelInquiryDto.getWriterId() != null) {hotelInquiry.setWriterId(hotelInquiryDto.getWriterId());}
        if (hotelInquiryDto.getHotelId() != null) {hotelInquiry.setHotelId(hotelInquiryDto.getHotelId());}

        return ResponseEntity.ok(Map.of("message", "Inquiry updated successfully"));
    }

    public ResponseEntity<?> deleteInquiry(Integer id) {
        Long memberId = facade.getCurrentMember().getId();
        HotelInquiry inquiry = findInquiryById(id);
        checkPermission(inquiry.getWriterId(), memberId);
        hotelInquiryRepository.deleteById(id);

        return ResponseEntity.ok().body("Inquiry deleted successfully");
    }

    private HotelInquiry findInquiryById(Integer id) {
        return hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry with id " + id + " does not exist."));
    }

    private void checkPermission(Long writerId, Long memberId) {
        if (!writerId.equals(memberId)) {
            throw new PermissionDeniedException("You do not have permission to perform this action.");
        }
    }

    public ResponseEntity<Page<HotelInquiryDto>> getInquiriesByHotelId(Integer hotelId, Pageable pageable) {
        Page<HotelInquiry> inquiryPage = hotelInquiryRepository.findAllByHotelIdOrderByCreatedAtDesc(hotelId, pageable);
        return ResponseEntity.ok(inquiryPage.map(HotelInquiryMapper::toDto));
    }

}
