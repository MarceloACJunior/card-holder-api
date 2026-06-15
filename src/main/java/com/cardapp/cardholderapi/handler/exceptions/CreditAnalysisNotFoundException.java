package com.cardapp.cardholderapi.handler.exceptions;

public class CreditAnalysisNotFoundException extends RuntimeException {
    public CreditAnalysisNotFoundException(String message) {
        super(message);
    }
}
