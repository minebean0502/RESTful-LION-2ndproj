package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.review.ReviewDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class HotelDto {

    private Long id;
    private String title;
    private String address;
    private String area;
    private String firstImage;
    private String tel;
    private String mapX;
    private String mapY;

    private String description; // 사용자가 추가
    private Double avg_score; // 평균 별점 (호텔 개별 조회시 갱신)
    private Long review_count; //총 리뷰 개수
    private Long like_count; // 총 하트 개수
    @Setter
    private List<RoomDto> rooms; // 사용자가 추가

    private Long managerId;

    public static HotelDto fromEntity(Hotel hotel) {
        List<RoomDto> roomDtoList = hotel.getRooms().stream()
                .map(RoomDto::fromEntity)
                .toList();

        return HotelDto.builder()
                .id(hotel.getId())
                .title(hotel.getTitle())
                .address(hotel.getAddress())
                .area(hotel.getArea())
                .description(hotel.getDescription())
                .firstImage(hotel.getFirstImage())
                .avg_score(hotel.getAvg_score())
                .review_count(hotel.getReview_count())
                .mapX(hotel.getMapX())
                .mapY(hotel.getMapY())
                .tel(hotel.getTel())
                .rooms(roomDtoList)
                .managerId(hotel.getManager().getId())
                .build();
    }
}