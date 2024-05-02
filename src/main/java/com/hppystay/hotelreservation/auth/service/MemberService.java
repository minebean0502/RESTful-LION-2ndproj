package com.hppystay.hotelreservation.auth.service;

import com.hppystay.hotelreservation.auth.dto.*;
import com.hppystay.hotelreservation.auth.entity.*;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import com.hppystay.hotelreservation.auth.repository.ManagerRequestRepository;
import com.hppystay.hotelreservation.auth.repository.VerificationRepository;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.common.util.FileService;
import com.hppystay.hotelreservation.common.util.GlobalConstants;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final VerificationRepository verificationRepository;
    private final ManagerRequestRepository managerRequestRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final FileService fileService;
    private final AuthenticationFacade facade;

    // 회원 가입
    @Transactional
    public MemberDto signUp(CreateMemberDto createMemberDto) {
        // 이메일 중복 체크
        if (userExists(createMemberDto.getEmail()))
            throw new GlobalException(GlobalErrorCode.EMAIL_ALREADY_EXISTS);

        EmailVerification verification = verificationRepository.findByEmail(createMemberDto.getEmail())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.VERIFICATION_NOT_FOUND));
        if (!verification.getStatus().equals(VerificationStatus.VERIFIED)) {
            // 상태가 적절하지 않은 경우
            throw new GlobalException(GlobalErrorCode.VERIFICATION_INVALID_STATUS);
        }

        Member member = Member.builder()
                .nickname(createMemberDto.getNickname())
                .email(createMemberDto.getEmail())
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .role(MemberRole.ROLE_USER)
                .build();

        verificationRepository.deleteByEmail(createMemberDto.getEmail());

        return MemberDto.fromEntity(memberRepository.save(member));
    }

    // 해당 이메일로 가입한 아이디 확인
    public boolean userExists(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void signIn(LoginDto dto, HttpServletResponse response) {
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(dto.getEmail());
        if (optionalMember.isEmpty())
            throw new GlobalException(GlobalErrorCode.EMAIL_PASSWORD_MISMATCH);

        Member member = optionalMember.get();

        // 비밀번호 같은지 확인
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword()))
            throw new GlobalException(GlobalErrorCode.EMAIL_PASSWORD_MISMATCH);

        issueTokens(member, response);
    }

    public void issueTokens(Member member, HttpServletResponse response) {
        // 토큰 발급
        String accessToken = jwtTokenUtils.generateAccessToken(member);
        String refreshToken = jwtTokenUtils.generateRefreshToken(member);

        // 방법 1. 토큰을 쿠키로 저장
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(GlobalConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(GlobalConstants.REFRESH_TOKEN_EXPIRE_SECOND)
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

//    //로그인 (jwt 토큰 발급)
//    public JwtResponseDto issueToken(JwtRequestDto dto) {
//        Optional<Member> optionalMember = memberRepository.findMemberByEmail(dto.getEmail());
//        if (optionalMember.isEmpty())
//            throw new GlobalException(GlobalErrorCode.EMAIL_PASSWORD_MISMATCH);
//
//        Member member = optionalMember.get();
//
//        // 비밀번호 같은지 확인
//        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword()))
//            throw new GlobalException(GlobalErrorCode.EMAIL_PASSWORD_MISMATCH);
//
//        String jwt = jwtTokenUtils.generateAccessToken(member);
//        JwtResponseDto response = new JwtResponseDto();
//        response.setToken(jwt);
//
//        return response;
//    }


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
            throw new GlobalException(GlobalErrorCode.EMAIL_SENDING_FAILED);
        }

        // 기존 진행중인 인증 코드 발송 내역은 삭제
        verificationRepository.deleteByEmail(receiverEmail);

        EmailVerification verification = EmailVerification.builder()
                .email(receiverEmail)
                .verifyCode(verifyCode)
                .status(VerificationStatus.SENT)
                .build();

        // 인증 코드 발송 내역 저장
        verificationRepository.save(verification);
    }

    public void verifyEmail(String email, String code) {
        EmailVerification verification = verificationRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.VERIFICATION_NOT_FOUND));

        if (!verification.getVerifyCode().equals(code)) {
            // 이메일로 전송된 인증 코드와 DB에 저장된 인증 코드가 일치하는지 확인
            throw new GlobalException(GlobalErrorCode.VERIFICATION_CODE_MISMATCH);
        } else if (!verification.getStatus().equals(VerificationStatus.SENT)) {
            // 상태가 적절하지 않은 경우
            throw new GlobalException(GlobalErrorCode.VERIFICATION_INVALID_STATUS);
        } else if (verification.getCreatedAt().isBefore(
                LocalDateTime.now().minusMinutes(GlobalConstants.EMAIL_VERIFY_CODE_EXPIRE_SECOND))) {
            // 인증 시간 만료
            throw new GlobalException(GlobalErrorCode.VERIFICATION_EXPIRED);
        }

        verification.setStatus(VerificationStatus.VERIFIED);
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

    @Transactional
    public ResponseEntity<String> signUpSendCode(String email) {
        if (userExists(email))
            throw new GlobalException(GlobalErrorCode.EMAIL_ALREADY_EXISTS);
        sendVerifyCode(email);
        return ResponseEntity.ok("{}");
    }

    @Transactional
    public ResponseEntity<String> passwordSendCode(String email) {
        // email 이 존재하는 멤버인지 확인
        if (!userExists(email))
            throw new GlobalException(GlobalErrorCode.EMAIL_NOT_FOUND);
        sendVerifyCode(email);
        return ResponseEntity.ok("{}");
    }

    // 비밀번호 인증 코드 확인 메서드
    @Transactional
    public ResponseEntity<String> resetPassword(PasswordChangeRequestDto requestDto) {
        String email = requestDto.getEmail();
        String code = requestDto.getCode();
        String newPassword = requestDto.getNewPassword();

        EmailVerification verification = verificationRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.VERIFICATION_NOT_FOUND));

        if (!verification.getVerifyCode().equals(code)) {
            // 이메일로 전송된 인증 코드와 DB에 저장된 인증 코드가 일치하는지 확인
            throw new GlobalException(GlobalErrorCode.VERIFICATION_CODE_MISMATCH);
        } else if (!verification.getStatus().equals(VerificationStatus.VERIFIED)) {
            // 상태가 적절하지 않은 경우
            throw new GlobalException(GlobalErrorCode.VERIFICATION_INVALID_STATUS);
        }

        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.EMAIL_NOT_FOUND));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        verificationRepository.deleteByEmail(email);

        return ResponseEntity.ok("Success");
    }


    // 비밀번호 변경 메서드
    @Transactional
    public ResponseEntity<String> changePassword(PasswordDto dto) {
        Member member = facade.getCurrentMember();

        //비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(dto.getCurrentPassword(), member.getPassword())) {
            throw new GlobalException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        member.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        memberRepository.save(member);

        return ResponseEntity.ok("{}");
    }

    public void uploadProfileImage(MultipartFile image) {
        Member member = facade.getCurrentMember();

        String rootDir = "media/";
        String relativeDir = "img/profiles/";

        // 기존 프로필 이미지 삭제
        String oldProfile = member.getProfileImage();
        if (oldProfile != null) {
            fileService.deleteFile(rootDir + oldProfile);
        }

        // 새로운 이미지 저장
        String newProfile = fileService.saveImage(rootDir, relativeDir, image);
        member.setProfileImage(newProfile);

        memberRepository.save(member);
    }

    public MemberProfileDto getMyProfile() {
        Member member = facade.getCurrentMember();
        return MemberProfileDto.fromEntity(member);
    }

    public void requestManagerRole(String businessNumber) {
        Member member = facade.getCurrentMember();

        // 진행 중인 신청이 있을 경우 예외 처리
        Optional<ManagerRequest> optionalRequest = managerRequestRepository
                .findByMemberAndStatus(member, ManagerRequestStatus.PENDING);
        if (optionalRequest.isPresent())
            throw new GlobalException(GlobalErrorCode.DUPLICATE_MANAGER_REQUEST);

        // 새로운 요청 생성
        ManagerRequest request = ManagerRequest.builder()
                .member(member)
                .businessNumber(businessNumber)
                .status(ManagerRequestStatus.PENDING)
                .build();

        managerRequestRepository.save(request);
    }

    public List<ManagerRequestDto> readAllManagerRequests() {
        return managerRequestRepository.findAll().stream()
                .map(ManagerRequestDto::fromEntity)
                .toList();
    }

    public void approveManagerRole(Long requestId) {
        ManagerRequest request = managerRequestRepository.findById(requestId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MANAGER_REQUEST_NOT_FOUND));

        // 상태가 Pending이 아니라면 오류
        if (request.getStatus() != ManagerRequestStatus.PENDING)
            throw new GlobalException(GlobalErrorCode.MANAGER_REQUEST_ALREADY_PROCESSED);

        request.getMember().setRole(MemberRole.ROLE_MANAGER);
        request.getMember().setBusinessNumber(request.getBusinessNumber());
        request.setStatus(ManagerRequestStatus.APPROVED);
        managerRequestRepository.save(request);
    }

    public void rejectManagerRole(Long requestId) {
        ManagerRequest request = managerRequestRepository.findById(requestId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MANAGER_REQUEST_NOT_FOUND));

        // 상태가 Pending이 아니라면 오류
        if (request.getStatus() != ManagerRequestStatus.PENDING)
            throw new GlobalException(GlobalErrorCode.MANAGER_REQUEST_ALREADY_PROCESSED);

        request.setStatus(ManagerRequestStatus.REJECTED);
        managerRequestRepository.save(request);
    }

    public ResponseEntity<UserInfoDto> getLoginInfo() {
        UserInfoDto userInfoDto = UserInfoDto.fromEntity(facade.getCurrentMember());
        return ResponseEntity.ok(userInfoDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(username);

        if (optionalMember.isEmpty())
            throw new GlobalException(GlobalErrorCode.EMAIL_NOT_FOUND);
        Member member = optionalMember.get();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }

}


