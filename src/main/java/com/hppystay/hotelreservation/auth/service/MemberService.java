package com.hppystay.hotelreservation.auth.service;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.auth.entity.EmailVerification;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.auth.repository.VerificationRepository;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final VerificationRepository verificationRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public MemberDto signUp(CreateMemberDto createMemberDto) {
        // 이메일 중복 체크
        if (userExists(createMemberDto.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Member member = Member.builder()
                .nickname(createMemberDto.getNickname())
                .email(createMemberDto.getEmail())
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .role(MemberRole.ROLE_USER)
                .build();
        return MemberDto.fromEntity(memberRepository.save(member));
    }

    // 해당 이메일로 가입한 아이디 확인
    public boolean userExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    //로그인 (jwt 발급)
    public JwtResponseDto issueToken(JwtRequestDto dto) {
        //아이디 확인
        if (!userExists(dto.getEmail()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        Optional<Member> optionalMember = memberRepository.findMemberByEmail(dto.getEmail());
        if (optionalMember.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Member member = optionalMember.get();

        //비밀번호 같은지 확인
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String jwt = jwtTokenUtils.generateToken(member);
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);

        return response;
    }


    @Value("${SMTP_USERNAME}")
    private String senderEmail;

    @Transactional
    public void sendVerifyCode(String receiverEmail) {
        String verifyCode = generateRandomNumber(6);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(senderEmail);
            message.setRecipients(Message.RecipientType.TO, receiverEmail);

            message.setSubject("[RESTful Lion] 인증 코드입니다.");

            StringBuilder textBody = new StringBuilder();
            textBody.append("<div style=\"max-width: 600px;\">")
                    .append("   <h2>Email Verification</h2>")
                    .append("   <p>아래의 인증 코드를 사용하여 이메일 주소를 인증해주세요.</p>")
                    .append("   <p><strong>인증 코드:</strong> <span style=\"font-size: 18px; font-weight: bold;\">")
                    .append(verifyCode)
                    .append("</span></p>")
                    .append("   <p>이 코드는 5분간 유효합니다.</p>")
                    .append("   <p>감사합니다.</p>")
                    .append("</div>");
            message.setText(textBody.toString(), "UTF-8", "HTML");

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 기존 진행중인 인증 코드 발송 내역은 삭제
        verificationRepository.deleteByEmail(receiverEmail);

        EmailVerification verification = EmailVerification.builder()
                .email(receiverEmail)
                .verifyCode(verifyCode)
                .build();

        // 인증 코드 발송 내역 저장
        verificationRepository.save(verification);
    }

    // 랜덤 숫자코드를 생성하는 메서드
    public String generateRandomNumber(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(username);

        if (optionalMember.isEmpty())
            throw new UsernameNotFoundException("email not found");
        Member member = optionalMember.get();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }
}
