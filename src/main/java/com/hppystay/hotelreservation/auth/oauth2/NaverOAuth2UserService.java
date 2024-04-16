package com.hppystay.hotelreservation.auth.oauth2;

import lombok.extern.slf4j.Slf4j;
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
public class NaverOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        Map<String, Object> attributes = new HashMap<>();
        String nameAttribute = "";

        if (registrationId.equals("naver")) {
            attributes.put("provider", "naver");

            Map<String, Object> responseMap
                    = oAuth2User.getAttribute("response");
            attributes.put("id", responseMap.get("id"));
            attributes.put("email", responseMap.get("email"));
            attributes.put("nickname", responseMap.get("nickname"));

            nameAttribute = "email";
        }

        // kakao 아이디로 로그인
        if (registrationId.equals("kakao")) {

            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

            attributes.put("provider", "kakao");
            attributes.put("id", oAuth2User.getAttribute("id"));
            attributes.put("email", kakaoAccount.get("email"));
            attributes.put("nickname", kakaoProfile.get("nickname"));
            nameAttribute = "email";
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                nameAttribute
        );
    }
}
