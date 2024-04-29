package com.hppystay.hotelreservation.auth.handler;

import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
                       HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 상태 코드를 401 Unauthorized로 설정

        // 에러 메시지를 JSON 형식으로 변환하여 클라이언트에 반환
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "요청하신 페이지에 접근할 권한이 없습니다.");
        response.getWriter().print(convertMapToJson(errorResponse));

        // request.getRequestDispatcher("/login").forward(request, response);
    }

    // Map을 JSON 형식의 문자열로 변환하는 메서드
    private String convertMapToJson(Map<String, String> map) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}
