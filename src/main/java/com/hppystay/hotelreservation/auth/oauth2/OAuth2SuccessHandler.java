package com.hppystay.hotelreservation.auth.oauth2;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils tokenUtils;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User
                = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);

        String email = oAuth2User.getName();
        String nickname = oAuth2User.getAttribute("nickname");

        // TODO: id는 필요한지 논의 필요
        // String id = oAuth2User.getAttribute("id");
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(email);
        // 해당 이메일로 가입한 회원이 없는 경우
        Member member;
        if (optionalMember.isEmpty()) {
            member = Member.builder()
                    .nickname(nickname)
                    .email(email)
                    // 비밀번호는 임의 UUID 값을 encoding 하여 사용 TODO: 논의 필요
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .role(MemberRole.ROLE_USER)
                    .build();
            member = memberRepository.save(member);
        } else {
            member = optionalMember.get();
        }

        // 토큰 발급
        String jwt = tokenUtils.generateToken(member);

        // TODO: 만들어진 토큰을 어떻게 로그인 과정까지 연결시킬 것인지 논의 필요
        // TODO: Get 방식으로 JWT토큰을 보내는건 안전한지?
        String targetUrl = String.format(
                "http://localhost:8080/test/token?token=%s", jwt);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}