package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.mapper.HotelInquiryMapper;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.PermissionDeniedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class HotelInquiryServiceImpl implements HotelInquiryService {
    private final HotelInquiryRepository hotelInquiryRepository;

    @Autowired
    public HotelInquiryServiceImpl(HotelInquiryRepository hotelInquiryRepository) {
        this.hotelInquiryRepository = hotelInquiryRepository;
    }

    @Override
    public Page<HotelInquiryDto> getAllInquiries(Pageable pageable) {
        Page<HotelInquiry> inquiryPage = hotelInquiryRepository.findAllByOrderByCreatedAtDesc(pageable);
        return inquiryPage.map(HotelInquiryMapper::toDto);
    }

    @Override
    public HotelInquiryDto getInquiryById(Integer id) {
        HotelInquiry hotelInquiry = findInquiryById(id);
        return HotelInquiryMapper.toDto(hotelInquiry);
    }

    @Override
    public void createInquiry(HotelInquiryDto hotelInquiryDto, String writerId, Integer hotelId) {
        hotelInquiryDto.setWriterId(writerId);
        hotelInquiryDto.setHotelId(hotelId);

        HotelInquiry hotelInquiry = HotelInquiryMapper.toEntity(hotelInquiryDto);
        hotelInquiryRepository.save(hotelInquiry);

    }

    @Override
    public void updateInquiry(Integer id, HotelInquiryDto hotelInquiryDto, String currentUsername) {
        HotelInquiry hotelInquiry = findInquiryById(id);
        checkPermission(hotelInquiry.getWriterId(), currentUsername);

        if (hotelInquiryDto.getTitle() != null) {hotelInquiry.setTitle(hotelInquiryDto.getTitle());}
        if (hotelInquiryDto.getContent() != null) {hotelInquiry.setContent(hotelInquiryDto.getContent());}
        if (hotelInquiryDto.getWriterId() != null) {hotelInquiry.setWriterId(hotelInquiryDto.getWriterId());}
        if (hotelInquiryDto.getHotelId() != null) {hotelInquiry.setHotelId(hotelInquiryDto.getHotelId());}
    }

    @Override
    public void deleteInquiry(Integer id, String currentUsername) {
        HotelInquiry inquiry = findInquiryById(id);
        checkPermission(inquiry.getWriterId(), currentUsername);
        hotelInquiryRepository.deleteById(id);
    }

    private HotelInquiry findInquiryById(Integer id) {
        return hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry with id " + id + " does not exist."));
    }

    private void checkPermission(String writerId, String currentUsername) {
        if (!writerId.equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to perform this action.");
        }
    }

}
