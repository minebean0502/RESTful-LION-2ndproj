package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import com.hppystay.hotelreservation.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@Service
@RequestMapping("search")
@RequiredArgsConstructor
@Slf4j
public class HotelSearchController {

    private final HotelService hotelService;
    private final ApiService apiService;

    @PostMapping("/region")
    public String searchRegion(@RequestParam("regionName") String regionName)
    {

        log.info("!!");

        boolean exists = hotelService.checkRegion(regionName);

        if (!exists) {
            return "search-fail";
        }

        String encodedRegionName = URLEncoder.encode(regionName, StandardCharsets.UTF_8);

        log.info(encodedRegionName);

        return "redirect:/search/" + encodedRegionName;
    }

    @GetMapping("/{regionName}")
    public String searchPage(@PathVariable String regionName, Model model)
    {
        String decodeRegionName = URLDecoder.decode(regionName, StandardCharsets.UTF_8);

        List<TourInfoApiDto> tourInfoApiDtos = apiService.callHotelByRegionApi(decodeRegionName);

        model.addAttribute("tourInfoApiDtos", tourInfoApiDtos);

        return "hotel-search";
    }

    @PostMapping("/{regionName}")
    public String userPage(@PathVariable String regionName, Model model) {

        return "redirect:/search/region/{regionName}";
    }
}
