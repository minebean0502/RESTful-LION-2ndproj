package com.hppystay.hotelreservation.hotel.inquiry.service.exception;

public class OperationNotAllowedException extends RuntimeException {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
