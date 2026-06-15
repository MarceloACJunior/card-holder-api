package com.cardapp.cardholderapi.handler.exceptions;

public class RequestedCardLimitUnavailableException extends RuntimeException {
    public RequestedCardLimitUnavailableException(String message) {
        super(message);
    }
}
