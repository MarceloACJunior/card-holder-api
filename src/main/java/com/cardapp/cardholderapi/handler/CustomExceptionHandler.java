package com.cardapp.cardholderapi.handler;

import com.cardapp.cardholderapi.handler.exceptions.CardHolderAlreadyRegisteredException;
import com.cardapp.cardholderapi.handler.exceptions.CardHolderNotFoundException;
import com.cardapp.cardholderapi.handler.exceptions.ClientDoesNotCorrespondToCreditAnalysisException;
import com.cardapp.cardholderapi.handler.exceptions.CreditAnalysisNotFoundException;
import com.cardapp.cardholderapi.handler.exceptions.CreditAnalysisUnavailableException;
import com.cardapp.cardholderapi.handler.exceptions.CreditCardNotFoundException;
import com.cardapp.cardholderapi.handler.exceptions.InvalidCardHolderStatusException;
import com.cardapp.cardholderapi.handler.exceptions.InvalidCreditLimitException;
import com.cardapp.cardholderapi.handler.exceptions.NoCreditCardsFoundException;
import com.cardapp.cardholderapi.handler.exceptions.PathCardHolderDoesNotMatchRequestCardHolderException;
import com.cardapp.cardholderapi.handler.exceptions.RequestedCardLimitUnavailableException;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final URI NOT_FOUND_URI = URI.create("http://cardapp.com/not-found");

    @ExceptionHandler(CreditAnalysisNotFoundException.class)
    public ProblemDetail creditAnalysisNotFoundExceptionHandler(CreditAnalysisNotFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setType(NOT_FOUND_URI);
        problemDetail.setTitle("Credit Analysis Not Found");
        return problemDetail;
    }

    @ExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException.class)
    public ProblemDetail clientDoesNotCorrespondToCreditAnalysisExceptionHandler(ClientDoesNotCorrespondToCreditAnalysisException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/client-does-not-correspond-credit-analysis"));
        problemDetail.setTitle("Client Doesn't Correspond To Credit Analysis");
        return problemDetail;
    }

    @ExceptionHandler(CardHolderAlreadyRegisteredException.class)
    public ProblemDetail cardHolderAlreadyRegisteredExceptionHandler(CardHolderAlreadyRegisteredException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/already-exists"));
        problemDetail.setTitle("Card Holder Already Exists");
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationExceptionHandler(ConstraintViolationException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/invalid-argument"));
        problemDetail.setTitle("Invalid Fields");
        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/invalid-argument"));
        problemDetail.setTitle("Invalid Fields");
        return problemDetail;
    }

    @ExceptionHandler(InvalidCardHolderStatusException.class)
    public ProblemDetail invalidStatusExceptionHandler(InvalidCardHolderStatusException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/invalid-card-holder-status"));
        problemDetail.setTitle("Invalid Status");
        return problemDetail;
    }

    @ExceptionHandler(CardHolderNotFoundException.class)
    public ProblemDetail cardHolderNotFoundExceptionHandler(CardHolderNotFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setType(NOT_FOUND_URI);
        problemDetail.setTitle("Card Holder Not Found");
        return problemDetail;
    }

    @ExceptionHandler(PathCardHolderDoesNotMatchRequestCardHolderException.class)
    public ProblemDetail pathCardHolderDoesNotMatchRequestCardHolderExceptionHandler(PathCardHolderDoesNotMatchRequestCardHolderException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/path-and-body-does-not-match"));
        problemDetail.setTitle("Unmatched fields");
        return problemDetail;
    }

    @ExceptionHandler(InvalidCreditLimitException.class)
    public ProblemDetail invalidCreditLimitExceptionHandler(InvalidCreditLimitException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/invalid-limit"));
        problemDetail.setTitle("Invalid Limit");
        return problemDetail;
    }

    @ExceptionHandler(NoCreditCardsFoundException.class)
    public ProblemDetail creditCardsNotFoundExceptionHandler(NoCreditCardsFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setType(NOT_FOUND_URI);
        problemDetail.setTitle("No Credit Card Found");
        return problemDetail;
    }

    @ExceptionHandler(RequestedCardLimitUnavailableException.class)
    public ProblemDetail requestedCardLimitUnavailableExceptionHandler(RequestedCardLimitUnavailableException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/unavailable-credit"));
        problemDetail.setTitle("Unavailable Credit");
        return problemDetail;
    }

    @ExceptionHandler(CreditCardNotFoundException.class)
    public ProblemDetail creditCardNotFoundExceptionHandler(CreditCardNotFoundException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        problemDetail.setType(NOT_FOUND_URI);
        problemDetail.setTitle("Card Not Found");
        return problemDetail;
    }

    @ExceptionHandler(CreditAnalysisUnavailableException.class)
    public ProblemDetail creditAnalysisUnavailableExceptionHandler(CreditAnalysisUnavailableException e) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getLocalizedMessage());
        problemDetail.setType(URI.create("http://cardapp.com/service-unavailable"));
        problemDetail.setTitle("Credit Analysis Unavailable");
        return problemDetail;
    }

}
