package com.hppystay.hotelreservation.payment.common.config;

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

    // 토스 설정
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

    // 카카오 설정
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

    /* 04-18 리팩토링 하려고 했으나
    toss는 base64 암호화로 header 전송 // kakao는 암호화 없이 header 전송이라 그냥 일일히 구현했음
    @Bean
    public RestClient tossClient() {
        return createRestClient("https://api.tosspayments.com/v1", tossSecret);
    }

    @Bean
    public RestClient kakaoClient() {
        return createRestClient("https://open-api.kakaopay.com/online/v1", kakaoPaySecret);
    }

    private RestClient createRestClient(String baseUrl, String secret) {
        return RestClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString((secret + ":").getBytes())))
                .build();
    }

    @Bean
    public TossHttpService tossHttpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(tossClient()))
                .build()
                .createClient(TossHttpService.class);
    }

    @Bean
    public KakaoHttpService kakaoHttpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(kakaoClient()))
                .build()
                .createClient(KakaoHttpService.class);
    }
     */


}
