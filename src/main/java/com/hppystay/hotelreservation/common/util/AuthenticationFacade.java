package com.hppystay.hotelreservation.common.util;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Member getCurrentMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // auth 정보가 존재하지 않는 경우
        if (auth == null) {
            throw new GlobalException(GlobalErrorCode.AUTHENTICATION_FAILED);
        } else if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new GlobalException(GlobalErrorCode.USER_DETAILS_INVALID_FORMAT);
        }
        return ((CustomUserDetails) auth.getPrincipal()).getMember();
    }
}
