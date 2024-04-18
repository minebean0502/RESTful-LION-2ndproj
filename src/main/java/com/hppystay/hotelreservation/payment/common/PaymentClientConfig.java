package com.hppystay.hotelreservation.payment.common;

import com.hppystay.hotelreservation.payment.kakao.service.KakaoHttpService;
import com.hppystay.hotelreservation.payment.toss.service.TossHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Base64;

@Configuration
public class PaymentClientConfig {
    // toss_secret키
    @Value("${toss.secret}")
    private String tossSecret;
    // kakaoPay_secret키
    @Value("${KAKAO_PAY_SECRET}")
    private String kakaoPaySecret;

    // 토스 기초 설정
    @Bean
    public RestClient tossClient() {
        String basicAuth = Base64.getEncoder().encodeToString((tossSecret + ":").getBytes());
        return RestClient
                .builder()
                .baseUrl("https://api.tosspayments.com/v1")
                .defaultHeader("Authorization", String.format("Basic %s", basicAuth))
                .build();
    }

    @Bean
    public TossHttpService TossHttpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(tossClient()))
                .build()
                .createClient(TossHttpService.class);
    }

    // 기초 설정
    @Bean
    public RestClient kakaoClient() {
        return RestClient
                .builder()
                .baseUrl("https://open-api.kakaopay.com/online/v1")
                .defaultHeader("Authorization", String.format("SECRET_KEY %s", kakaoPaySecret))
                .build();
    }

    @Bean
    public KakaoHttpService KakaoHttpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(kakaoClient()))
                .build()
                .createClient(KakaoHttpService.class);
    }





}
