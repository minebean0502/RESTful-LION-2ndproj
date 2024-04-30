package com.hppystay.hotelreservation.hotel.inquiry.service.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
