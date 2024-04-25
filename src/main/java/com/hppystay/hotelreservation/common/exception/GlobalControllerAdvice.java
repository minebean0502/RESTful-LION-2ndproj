package com.hppystay.hotelreservation.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(GlobalException e) {
        log.error("Exception: {}", e.getErrorCode().toString());

        Map<String, String> response = new HashMap<>();
        response.put("code", e.getErrorCode().getCode());
        response.put("message", e.getErrorCode().getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(response);
    }

    // Validation 에 대한 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();

        response.put("exception", e.getClass().getSimpleName());
        response.put("message", e.getFieldErrors().get(0).getDefaultMessage());

//        Map<String, String> fieldErrors = new HashMap<>();
//        for (FieldError fieldError : e.getFieldErrors()) {
//            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//        response.put("fields", fieldErrors);

        log.error(response.toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> unDefinedExceptionHandler(Exception e) {
        Map<String, Object> response = new HashMap<>();

        response.put("exception", e.getClass().getSimpleName());
        response.put("message", e.getMessage());

        log.error(response.toString());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
