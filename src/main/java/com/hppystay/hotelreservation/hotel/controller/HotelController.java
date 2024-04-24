package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import com.hppystay.hotelreservation.hotel.dto.HotelDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.RoomDto;
import com.hppystay.hotelreservation.hotel.dto.SearchDto;
import com.hppystay.hotelreservation.hotel.service.HotelService;
import com.hppystay.hotelreservation.hotel.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final ApiService apiService;
    private final HotelService hotelService;
    private final ReservationService reservationService;

    // API 기능
    @GetMapping("/api/hotel/areaCode/{area}")
    public List<TourInfoApiDto> findHotelByRegion(@PathVariable("area") String area){
        return apiService.callHotelByRegionApi(area);
    }

    @GetMapping("/api/hotel/keyword/{keyword}")
    public List<TourInfoApiDto> findHotelByKeyWord(@PathVariable("keyword") String keyword) {
        return apiService.callHotelByKeywordApi(keyword);
    }

    @GetMapping("/api/hotel/location/{mapX}/{mapY}")
    public List<TourInfoApiDto> findSpotByLocation(
            @PathVariable("mapX")
            String mapX,
            @PathVariable("mapY")
            String mapY
    ) {
        return apiService.callSpotByLocationApi(mapX, mapY);
    }

    // 호텔 기능
    @PostMapping("/api/hotel")
    public HotelDto createHotel(
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.createHotel(dto);
    }

    @GetMapping("/api/hotel")
    public List<HotelDto> readAllHotel(
            @RequestBody
            SearchDto dto
    ) {
        LocalDate checkIn = dto.getCheckIn();
        LocalDate checkOut = dto.getCheckOut();
        return hotelService.readHotelsReservationPossible(checkIn, checkOut);
    }

    @GetMapping("/api/hotel/{id}")
    public HotelDto readHotel(
            @PathVariable("id")
            Long id
    ) {
        return hotelService.readOneHotel(id);
    }

    @PutMapping("/api/hotel/{id}")
    public HotelDto updateHotel(
            @PathVariable("id")
            Long id,
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.updateHotel(id, dto);
    }

    @DeleteMapping("api/hotel/{id}")
    public void deleteHotel(
            @PathVariable("id")
            Long id
    ) {
        hotelService.deleteHotel(id);
    }

    // 기존 호텔에 방만 추가하는 경우
    @PostMapping("/api/hotel/room/{id}")
    public HotelDto addRoom(
            @PathVariable("id")
            Long hotelId,
            @RequestBody
            RoomDto roomDto
    ) {
        return hotelService.addRoom(roomDto, hotelId);
    }

    // 예약 기능
    @PostMapping("/api/hotel/reservation")
    public ReservationDto addReservation(
            @RequestBody
            ReservationDto dto
    ) {
        return reservationService.createReservation(dto);
    }
}
