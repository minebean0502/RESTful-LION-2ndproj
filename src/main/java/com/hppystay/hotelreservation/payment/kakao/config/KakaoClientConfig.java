package com.hppystay.hotelreservation.payment.kakao.config;

import com.hppystay.hotelreservation.payment.kakao.service.KakaoHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class KakaoClientConfig {
    @Value("${KAKAO_PAY_SECRET}")
    private String kakaoPaySecret;

    @Bean
    public RestClient kakaoClient() {
        return RestClient
                .builder()
                .baseUrl("https://open-api.kakaopay.com/online/v1")
                .defaultHeader("Authorization", String.format("SECRET_KEY %s", kakaoPaySecret))
                .build();
    }

    @Bean
    public KakaoHttpService httpService() {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(kakaoClient()))
                .build()
                .createClient(KakaoHttpService.class);
    }
}
