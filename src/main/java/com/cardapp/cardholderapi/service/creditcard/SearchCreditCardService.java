package com.cardapp.cardholderapi.service.creditcard;

import com.cardapp.cardholderapi.controller.response.CreditCardResponse;
import com.cardapp.cardholderapi.mapper.CreditCardMapper;
import com.cardapp.cardholderapi.repository.CreditCardRepository;
import com.cardapp.cardholderapi.repository.entity.creditcard.CreditCardEntity;
import com.cardapp.cardholderapi.service.ServiceVerifications;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchCreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;
    private final ServiceVerifications serviceVerifications;

    public List<CreditCardResponse> getAllCardsByCardHolderId(UUID cardHolderId) {
        final List<CreditCardEntity> creditCardEntities = creditCardRepository.findAllByCardHolderId(cardHolderId);
        return creditCardEntities.stream().map(creditCardMapper::responseFromEntity).toList();
    }

    public CreditCardResponse getCreditCardById(UUID cardHolderId, UUID id) {
        final CreditCardEntity creditCardEntity = serviceVerifications.getCreditCardById(cardHolderId, id);
        return creditCardMapper.responseFromEntity(creditCardEntity);
    }
}
