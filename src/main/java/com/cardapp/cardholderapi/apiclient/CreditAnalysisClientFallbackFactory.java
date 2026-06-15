package com.cardapp.cardholderapi.apiclient;

import com.cardapp.cardholderapi.apiclient.dtos.CreditAnalysisDTO;
import com.cardapp.cardholderapi.handler.exceptions.CreditAnalysisNotFoundException;
import com.cardapp.cardholderapi.handler.exceptions.CreditAnalysisUnavailableException;
import feign.FeignException;
import java.util.UUID;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CreditAnalysisClientFallbackFactory implements FallbackFactory<CreditAnalysisClient> {
    private static final int CLIENT_ERROR_START = 400;
    private static final int SERVER_ERROR_START = 500;

    @Override
    public CreditAnalysisClient create(Throwable cause) {
        return new CreditAnalysisClient() {
            @Override
            public CreditAnalysisDTO getCreditAnalysisById(UUID creditAnalysisId) {
                if (isClientError(cause)) {
                    throw new CreditAnalysisNotFoundException(
                            "Credit analysis not found by ID %s".formatted(creditAnalysisId));
                }
                throw new CreditAnalysisUnavailableException(
                        "credit-analysis-api is unavailable, please try again later", cause);
            }
        };
    }

    private boolean isClientError(Throwable cause) {
        return cause instanceof FeignException fe && fe.status() >= CLIENT_ERROR_START && fe.status() < SERVER_ERROR_START;
    }
}
