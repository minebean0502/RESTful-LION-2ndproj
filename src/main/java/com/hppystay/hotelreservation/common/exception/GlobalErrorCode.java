package com.hppystay.hotelreservation.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public enum GlobalErrorCode {
    // 회원 관련 에러
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "1001", "이미 가입된 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "1002", "존재하지 않는 이메일입니다."),
    EMAIL_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "1003", "이메일과 패스워드가 일치하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "1004", "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "1005", "인증에 실패했습니다."),
    USER_DETAILS_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "1006", "UserDetails의 형식이 올바르지 않습니다."),
    MEMBER_MISMATCH(HttpStatus.UNAUTHORIZED, "1007", "일치하지 않는 회원입니다."),
    PROFILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "1008", "프로필 이미지 저장에 실패했습니다."),
    PROFILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "1009", "프로필 이미지 삭제에 실패했습니다."),



    // 이메일 관련 에러
    EMAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "2001", "이메일 발송에 실패했습니다."),
    VERIFICATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "2002", "인증 번호가 틀렸습니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "2003", "이메일 인증 정보가 없습니다."),
    VERIFICATION_INVALID_STATUS(HttpStatus.BAD_REQUEST, "2004", "인증의 상태가 올바르지 않습니다."),
    VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "2005", "인증 코드가 만료되었습니다."),

    // 리뷰 관련 에러
    MISMATCH(HttpStatus.BAD_REQUEST, "3001", "일치하지 않습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "3002", "존재하지 않습니다."),

    // 호텔 생성 관련 에러
    NOT_AUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "4001", "호텔생성 권한이 없습니다."),
    ALREADY_MANAGER(HttpStatus.CONFLICT,"4002", "이미 호텔 1개를 생성했습니다.");


    // errorCode는 영역별로 나누기 (EX: 회원 관련 에러는 1000번대)

    private final HttpStatus status;
    private final String code;
    private final String message;
}