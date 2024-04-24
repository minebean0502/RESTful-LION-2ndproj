package com.hppystay.hotelreservation.auth.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        Map<String, Object> attributes = new HashMap<>();

        // 네이버 아이디로 로그인
        if (registrationId.equals("naver")) {
            attributes.put("provider", "naver");

            Map<String, Object> responseMap
                    = oAuth2User.getAttribute("response");
            attributes.put("id", responseMap.get("id"));
            attributes.put("email", responseMap.get("email"));
            attributes.put("nickname", responseMap.get("nickname"));
        }

        // kakao 아이디로 로그인
        else if (registrationId.equals("kakao")) {
            attributes.put("provider", "kakao");

            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

            attributes.put("provider", "kakao");
            attributes.put("id", oAuth2User.getAttribute("id"));
            attributes.put("email", kakaoAccount.get("email"));
            attributes.put("nickname", kakaoProfile.get("nickname"));
        }
        // google 아이디로 로그인
        else if (registrationId.equals("google")) {
            attributes.put("provider", "google");
            attributes.put("id", oAuth2User.getAttribute("id"));
            attributes.put("email", oAuth2User.getAttribute("email"));
            attributes.put("nickname", oAuth2User.getAttribute("name"));
        }

        String nameAttribute = "email";

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                nameAttribute
        );
    }
}
