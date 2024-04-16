package com.hppystay.hotelreservation.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OpenApiException extends RuntimeException {
    public OpenApiException(String message) {
        super(message);
    }
}
