package com.cardapp.cardholderapi.handler.exceptions;

public class CreditAnalysisNotApprovedException extends RuntimeException {
    public CreditAnalysisNotApprovedException(String message) {
        super(message);
    }
}
