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
    EMAIL_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "1003", "이메일과 패스워드가 일치하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "1004", "비밀번호가 일치하지 않습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "1005", "인증에 실패했습니다."),
    USER_DETAILS_INVALID_FORMAT(HttpStatus.UNAUTHORIZED, "1006", "UserDetails의 형식이 올바르지 않습니다."),
    MEMBER_MISMATCH(HttpStatus.UNAUTHORIZED, "1007", "일치하지 않는 회원입니다."),
    PROFILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "1008", "프로필 이미지 저장에 실패했습니다."),
    PROFILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "1009", "프로필 이미지 삭제에 실패했습니다."),
    ROLE_UPGRADE_DENIED(HttpStatus.FORBIDDEN, "1010", "권한이 없습니다. 일반 회원만 가능합니다."),
    DUPLICATE_MANAGER_REQUEST(HttpStatus.BAD_REQUEST, "1011", "이미 매니저 신청이 진행 중입니다."),
    MANAGER_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "1012", "존재하지 않는 매니저 신청입니다."),
    MANAGER_REQUEST_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "1013", "이미 처리된 매니저 신청입니다."),
    ROLE_UNAUTHORIZED(HttpStatus.NOT_FOUND, "1014", "권한이 없습니다. 호텔 관리자 혹은 시스템 관리자만 가능합니다."),
    ROLE_MISMATCH(HttpStatus.BAD_REQUEST, "1015", "권한이 없습니다. 시스템 관리자만 가능합니다."),

    // 이메일 관련 에러
    EMAIL_SENDING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "2001", "이메일 발송에 실패했습니다."),
    VERIFICATION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "2002", "인증 번호가 틀렸습니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "2003", "이메일 인증 정보가 없습니다."),
    VERIFICATION_INVALID_STATUS(HttpStatus.BAD_REQUEST, "2004", "인증의 상태가 올바르지 않습니다."),
    VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "2005", "인증 코드가 만료되었습니다."),

    // 리뷰 관련 에러
    HOTEL_MISMATCH(HttpStatus.BAD_REQUEST, "3001", "해당 호텔의 댓글이 아닙니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "3002", "일치하는 리뷰가 없습니다."),


    // 호텔 관련 에러
    NOT_AUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "4001", "호텔 생성 권한이 없습니다."),
    NOT_AUTHORIZED_MANAGER(HttpStatus.UNAUTHORIZED, "4002", "호텔 수정 권한이 없습니다"),
    HOTEL_ALREADY_CREATED(HttpStatus.CONFLICT,"4003", "이미 호텔 1개를 생성했습니다."),
    HOTEL_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "일치하는 호텔이 없습니다."),
    HOTEL_ROOM_MISMATCH(HttpStatus.BAD_REQUEST,"4005", "요청하신 호텔과 방이 일치하지 않습니다."),
    CHECKIN_AFTER_CHECKOUT(HttpStatus.BAD_REQUEST, "4006", "체크인 시간이 체크아웃 시간보다 빨라야 합니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "4007", "일치하는 방이 없습니다"),

    // 예약 관련 에러
    NOT_HAVE_RESERVATION(HttpStatus.NOT_FOUND, "4100", "사용자가 소유한 예약이 아닙니다"),
    ALREADY_RESERVED(HttpStatus.CONFLICT, "4101", "이미 에약된 방입니다"),

    // 좋아요 관련 에러
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "5001", "이미 좋아요를 생성했습니다.");

    // toss 관련 에러

    // API 관련 에러


    // errorCode는 영역별로 나누기 (EX: 회원 관련 에러는 1000번대)

    private final HttpStatus status;
    private final String code;
    private final String message;
}