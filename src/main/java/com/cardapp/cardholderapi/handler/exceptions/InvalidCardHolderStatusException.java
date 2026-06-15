package com.cardapp.cardholderapi.handler.exceptions;

public class InvalidCardHolderStatusException extends RuntimeException {
    public InvalidCardHolderStatusException(String message) {
        super(message);
    }
}
