package com.cardapp.cardholderapi.apiclient;

import com.cardapp.cardholderapi.apiclient.dtos.CreditAnalysisDTO;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "creditAnalysisClient", url = "${credit.analysis.api.url}",
        fallbackFactory = CreditAnalysisClientFallbackFactory.class)
public interface CreditAnalysisClient {
    @GetMapping("/{creditAnalysisId}")
    CreditAnalysisDTO getCreditAnalysisById(@PathVariable(value = "creditAnalysisId") UUID creditAnalysisId);
}
