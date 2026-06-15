package com.cardapp.cardholderapi.handler.exceptions;

public class PathCardHolderDoesNotMatchRequestCardHolderException extends RuntimeException {
    public PathCardHolderDoesNotMatchRequestCardHolderException(String message) {
        super(message);
    }
}
