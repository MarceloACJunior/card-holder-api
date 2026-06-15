package com.jazztech.cardholderapi.handler.exceptions;

public class CreditAnalysisUnavailableException extends RuntimeException {
    public CreditAnalysisUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
