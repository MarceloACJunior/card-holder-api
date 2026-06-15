package com.cardapp.cardholderapi.handler.exceptions;

public class NoCreditCardsFoundException extends RuntimeException {
    public NoCreditCardsFoundException(String message) {
        super(message);
    }
}
