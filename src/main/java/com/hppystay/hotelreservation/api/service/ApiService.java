package com.hppystay.hotelreservation.api.service;


import com.hppystay.hotelreservation.api.KNTO.dto.hotel.HotelApiDto;
import com.hppystay.hotelreservation.api.exception.OpenApiException;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ApiService {

    @Value("${api.key}")
    private String apiKey;


    public List<HotelApiDto> callHotelRegionApi(Integer areaCode) throws IOException {

        String url = UriComponentsBuilder.fromUriString("https://apis.data.go.kr/B551011/KorService1/searchStay1?MobileOS=WIN&MobileApp=RR&_type=JSON&areaCode={areaCode}&serviceKey={api_Key}")
                .buildAndExpand(areaCode,this.apiKey)
                .toUriString();

        log.info(url);

        List<HotelApiDto> HotelApiDtos = new ArrayList<>();

        try {

            JSONArray jsonItemList = getJsonArray(url);

            log.info("response->body->items->item 파싱 완료 = {}", jsonItemList);

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                HotelApiDto dto = makeLocationDto(item);
                if (dto == null) {
                    log.info("DTO null");
                    continue;
                }
                HotelApiDtos.add(dto);
                log.info("{}", makeLocationDto(item));
            }

            log.info("fetch 완료");
            return HotelApiDtos;
        } catch (Exception e) {
            throw new OpenApiException("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        } finally {
            return HotelApiDtos;
        }

    }

    private static JSONArray getJsonArray(String url) throws IOException, ParseException {
        URL url2 = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
        String result = br.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

        JSONObject jsonResponse = (JSONObject)jsonObject.get("response");

        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");

        JSONObject jsonItems = (JSONObject) jsonBody.get("items");

        JSONArray jsonItemList = (JSONArray) jsonItems.get("item");
        return jsonItemList;
    }

    private HotelApiDto makeLocationDto(JSONObject item) {
        // 가끔 좌표 데이터가 타입이 다른 경우 null 반환
        if (!(item.get("mapx") instanceof String) || !(item.get("mapy") instanceof String)
                || item.get("addr1") == null || item.get("firstimage") == null
                || item.get("areacode") == null || item.get("contenttypeid") == null || item.get("title") == null) {
            return null;
        }
        return HotelApiDto.builder().
                title(item.get("title").toString()).
                address(item.get("addr1").toString()).
                areaCode(Integer.parseInt(item.get("areacode").toString())).
                contentTypeId(Integer.parseInt(item.get("contenttypeid").toString())).
                firstImage(item.get("firstimage").toString()).
                tel(item.get("tel").toString()).
                mapX(item.get("mapx").toString()).
                mapY(item.get("mapy").toString()).
                build();
    }
}
