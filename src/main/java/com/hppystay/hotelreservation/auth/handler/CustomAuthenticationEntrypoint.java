package com.hppystay.hotelreservation.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (requestURI.startsWith("/api/")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 에러 메시지를 JSON 형식으로 변환하여 클라이언트에 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "로그인이 필요합니다.");
            response.getWriter().print(JSONObject.toJSONString(errorResponse));
        } else {
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }
}
