package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelInquiryServiceImpl implements HotelInquiryService {
    private final HotelInquiryRepository hotelInquiryRepository;

    @Autowired
    public HotelInquiryServiceImpl(HotelInquiryRepository hotelInquiryRepository) {
        this.hotelInquiryRepository = hotelInquiryRepository;
    }

    @Override
    public List<HotelInquiryDto> getAllInquiries() {
        return hotelInquiryRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HotelInquiryDto getInquiryById(Integer id) {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Inquiry with id " + id + " does not exist."));
        return convertEntityToDto(hotelInquiry);
    }

    @Override
    public HotelInquiryDto createInquiry(HotelInquiryDto hotelInquiryDto, Integer writerId, Integer hotelId) {
        hotelInquiryDto.setWriterId(writerId);
        hotelInquiryDto.setHotelId(hotelId);

        HotelInquiry hotelInquiry = convertDtoToEntity(hotelInquiryDto);
        hotelInquiry = hotelInquiryRepository.save(hotelInquiry);

        return convertEntityToDto(hotelInquiry);
    }

    @Override
    public HotelInquiryDto updateInquiry(Integer id, HotelInquiryDto hotelInquiryDto) {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Inquiry with id " + id + " does not exist."));

        if (hotelInquiryDto.getTitle() != null) {
            hotelInquiry.setTitle(hotelInquiryDto.getTitle());
        }

        if (hotelInquiryDto.getContent() != null) {
            hotelInquiry.setContent(hotelInquiryDto.getContent());
        }

        if (hotelInquiryDto.getWriterId() != null) {
            hotelInquiry.setWriterId(hotelInquiryDto.getWriterId());
        }

        if (hotelInquiryDto.getHotelId() != null) {
            hotelInquiry.setHotelId(hotelInquiryDto.getHotelId());
        }

        return convertEntityToDto(hotelInquiryRepository.save(hotelInquiry));
    }

    @Override
    public void deleteInquiry(Integer id) {
        if (!hotelInquiryRepository.existsById(id)) {
            throw new IllegalStateException("Inquiry with id " + id + " does not exist.");
        }
        hotelInquiryRepository.deleteById(id);
    }

    private HotelInquiryDto convertEntityToDto(HotelInquiry hotelInquiry) {
        CommentDto commentDto = null;
        if (hotelInquiry.getComment() != null) {
            Comment comment = hotelInquiry.getComment();
            commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .comment(comment.getComment())
                    .writerId(comment.getWriterId())
                    .inquiryId(hotelInquiry.getId())
                    .build();
        }

        return HotelInquiryDto.builder()
                .id(hotelInquiry.getId())
                .title(hotelInquiry.getTitle())
                .content(hotelInquiry.getContent())
                .writerId(hotelInquiry.getWriterId())
                .hotelId(hotelInquiry.getHotelId())
                .comment(commentDto) // 단일 댓글 객체를 설정
                .build();
    }

    private HotelInquiry convertDtoToEntity(HotelInquiryDto hotelInquiryDto) {
        return HotelInquiry.builder()
                .id(hotelInquiryDto.getId())
                .title(hotelInquiryDto.getTitle())
                .content(hotelInquiryDto.getContent())
                .writerId(hotelInquiryDto.getWriterId())
                .hotelId(hotelInquiryDto.getHotelId())
                .build();
    }
}
