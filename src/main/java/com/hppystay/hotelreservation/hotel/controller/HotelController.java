package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import com.hppystay.hotelreservation.hotel.dto.*;
import com.hppystay.hotelreservation.hotel.service.HotelService;
import com.hppystay.hotelreservation.hotel.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final ApiService apiService;
    private final HotelService hotelService;
    private final ReservationService reservationService;

    // API 기능
    @GetMapping("/areaCode")
    public List<TourInfoApiDto> findHotelByRegion(
            @RequestParam("area")
            String area
    ) {
        return apiService.callHotelByRegionApi(area);
    }

    @GetMapping("/keyword")
    public List<TourInfoApiDto> findHotelByKeyWord(
            @RequestParam("keyword") String keyword
    ) {
        return apiService.callHotelByKeywordApi(keyword);
    }

    @GetMapping("/location")
    public List<TourInfoApiDto> findSpotByLocation(
            @RequestParam("mapX")
            String mapX,
            @RequestParam("mapY")
            String mapY,
            @RequestParam("pageNum")
            Integer pageNum
    ) {
        return apiService.callSpotByLocationApi(mapX, mapY, pageNum);
    }

    // 호텔 기능
    @PostMapping
    public HotelDto createHotel(
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.createHotel(dto);
    }

    @GetMapping("/search")
    public List<HotelDto> searchAllHotel(
            @RequestParam(value = "keyword")
            String keyword,
            @RequestParam(value = "checkIn")
            LocalDate checkIn,
            @RequestParam(value = "checkOut")
            LocalDate checkOut,
            @RequestParam(value = "sort", required = false, defaultValue = "name")
            String sort
    ) {
        return hotelService.searchHotelsAvailable(keyword, checkIn, checkOut, sort);
    }

    @GetMapping("/search/{id}")
    public HotelDto searchOneHotel(
            @PathVariable("id")
            Long id,
            @RequestParam(required = true)
            LocalDate checkIn,
            @RequestParam(required = true)
            LocalDate checkOut
    ) {
        return hotelService.readOneHotelReservationPossible(id, checkIn, checkOut);
    }

    @GetMapping("/{id}")
    public HotelDto readHotel(
            @PathVariable("id")
            Long id
    ) {
        return hotelService.readOneHotel(id);
    }

    @PutMapping("/{id}")
    public HotelDto updateHotel(
            @PathVariable("id")
            Long id,
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.updateHotel(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(
            @PathVariable("id")
            Long id
    ) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("{}");
    }

    // 예약 기능
    @PostMapping("/reservation")
    public ReservationDto addReservation(
            @RequestBody
            ReservationDto dto
    ) {
        return reservationService.createReservation(dto);
    }

    // 예약에 대해 조회
    @GetMapping("/reservation/my")
    public List<ReservationInfoDto> readAllMyReservation() {
        return reservationService.readAllMyReservation();
    }

    // 양도에 대해 조회
    @GetMapping("/reservation/pending")
    public List<ReservationInfoDto> readAllPendingReservation() {
        return reservationService.readAllPendingReservation();
    }

    // 좋아요 기능
    @PostMapping("/{hotelId}/like")
    public ResponseEntity<String> toggleLike(
            @PathVariable("hotelId")
            Long hotelId
    ) {
        hotelService.toggleLike(hotelId);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/my")
    public HotelDto getMyHotel() {
        return hotelService.getMyHotel();
    }
}
