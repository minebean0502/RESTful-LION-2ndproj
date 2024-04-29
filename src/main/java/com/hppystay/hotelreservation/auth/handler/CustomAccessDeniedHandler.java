package com.hppystay.hotelreservation.auth.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        response.setStatus(HttpStatus.FORBIDDEN.value());

        if (requestURI.startsWith("/api/")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 에러 메시지를 JSON 형식으로 변환하여 클라이언트에 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "요청하신 페이지에 접근할 권한이 없습니다.");
            response.getWriter().print(JSONObject.toJSONString(errorResponse));
        } else {
            request.getRequestDispatcher("/denied").forward(request, response);
        }
    }
}
