package com.hppystay.hotelreservation.api.service;


import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.KNTO.utils.AreaCode;
import com.hppystay.hotelreservation.api.KNTO.utils.ContentCode;
import com.hppystay.hotelreservation.api.exception.OpenApiException;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ApiService {

    @Value("${api.key}")
    private String apiKey;

    // 지역으로 숙소 검색
    public List<TourInfoApiDto> callHotelByRegionApi(String area) {
        Integer areaCode = AreaCode.getAreaCode(area);

        String url = UriComponentsBuilder.fromUriString("https://apis.data.go.kr/B551011/KorService1/searchStay1?MobileOS=WIN&MobileApp=RL&_type=JSON&arrange=O&areaCode={areaCode}&serviceKey={api_Key}")
                .buildAndExpand(areaCode,this.apiKey)
                .toUriString();

        log.info(url);

        return jsonToDtoList(url);
    }

    // 키워드로 숙소 검색
    public List<TourInfoApiDto> callHotelByKeywordApi(String keyword) {
        try {
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
            String url = UriComponentsBuilder.fromUriString("https://apis.data.go.kr/B551011/KorService1/searchKeyword1?MobileOS=WIN&MobileApp=RL&_type=JSON&arrange=O&keyword={keyword}&contentTypeId=32&serviceKey={apiKey}")
                    .buildAndExpand(encodedKeyword, apiKey)
                    .toString();

            log.info(url);

            return jsonToDtoList(url);
        }
        catch (Exception e) {
            throw new OpenApiException("오픈 API 예외 = 키워드 인코딩 불가");
        }
    }


    // 숙소 반경 1km 이내 관광지 검색
    // 관광타입 중 숙소(32), 여행코스(25) 제외하고 불러오기
    public List<TourInfoApiDto> callSpotByLocationApi(String mapX, String mapY, int pageNum) {
        String url = UriComponentsBuilder.fromUriString("https://apis.data.go.kr/B551011/KorService1/locationBasedList1?numOfRows=5&pageNo={pageNum}&MobileOS=WIN&MobileApp=RL&_type=json&arrange=O&mapX={mapX}&mapY={mapY}&radius=1000&serviceKey={apiKey}")
                .buildAndExpand(pageNum, mapX, mapY, apiKey)
                .toString();

        log.info(url);

        List<TourInfoApiDto> spotApiDtos = new ArrayList<>();

        try {
            JSONArray jsonItemList = getJsonArray(url);
            log.info("response->body->items->item 파싱 완료 = {}", jsonItemList);

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                TourInfoApiDto dto = makeLocationDto(item);
                if (dto == null) {
                    log.info("DTO null");
                    continue;
                }
                // 숙소, 여행코스 제외
                if (dto.getContentTypeId() == 32 || dto.getContentTypeId() == 25)
                    continue;
                // 사진 없는 관광지부터 제외
                if (dto.getFirstImage().isEmpty())
                    break;
                spotApiDtos.add(dto);
                log.info("{}", dto);
            }

            log.info("fetch 완료");
            return spotApiDtos;
        } catch (Exception e) {
            throw new OpenApiException("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        }
    }


    private JSONArray getJsonArray(String url) throws IOException, ParseException {
        URL url2 = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
        String result = br.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject jsonResponse = (JSONObject)jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");

        return (JSONArray) jsonItems.get("item");
    }

    private TourInfoApiDto makeLocationDto(JSONObject item) {
        // 가끔 좌표 데이터가 타입이 다른 경우 null 반환
        // 지역코드는 null은 아니지만, ""인 경우 AreaCode로 변환 시 예외 발생
        if (!(item.get("mapx") instanceof String) || !(item.get("mapy") instanceof String)
                || item.get("addr1") == null || item.get("firstimage") == null || item.get("tel") == null
                || item.get("areacode") == null || item.get("areacode") == "" || item.get("contenttypeid") == null || item.get("title") == null) {
            return null;
        }
        return TourInfoApiDto.builder()
                .title(item.get("title").toString())
                .address(item.get("addr1").toString())
                .areaCode(Integer.parseInt(item.get("areacode").toString()))
                .area(AreaCode.getAreaName(Integer.parseInt(item.get("areacode").toString())))
                .contentTypeId(Integer.parseInt(item.get("contenttypeid").toString()))
                .content(ContentCode.getContentName(Integer.parseInt(item.get("contenttypeid").toString())))
                .firstImage(item.get("firstimage").toString())
                .tel(item.get("tel").toString())
                .mapX(item.get("mapx").toString())
                .mapY(item.get("mapy").toString())
                .build();
    }

    private List<TourInfoApiDto> jsonToDtoList(String url) {
        List<TourInfoApiDto> HotelApiDtos = new ArrayList<>();

        try {
            JSONArray jsonItemList = getJsonArray(url);
            log.info("response->body->items->item 파싱 완료 = {}", jsonItemList);

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                TourInfoApiDto dto = makeLocationDto(item);
                if (dto == null) {
                    log.info("DTO null");
                    continue;
                }
                HotelApiDtos.add(dto);
//                log.info("{}", dto);
            }

            log.info("fetch 완료");
            return HotelApiDtos;
        } catch (Exception e) {
            throw new OpenApiException("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        }
    }
}
