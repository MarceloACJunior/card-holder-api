package com.cardapp.cardholderapi.service.creditcard;

import com.cardapp.cardholderapi.controller.request.CreditCardRequest;
import com.cardapp.cardholderapi.controller.response.CreditCardResponse;
import com.cardapp.cardholderapi.handler.exceptions.PathCardHolderDoesNotMatchRequestCardHolderException;
import com.cardapp.cardholderapi.handler.exceptions.RequestedCardLimitUnavailableException;
import com.cardapp.cardholderapi.mapper.CardHolderMapper;
import com.cardapp.cardholderapi.mapper.CreditCardMapper;
import com.cardapp.cardholderapi.model.cardholder.CardHolderModel;
import com.cardapp.cardholderapi.model.creditcard.CreditCardModel;
import com.cardapp.cardholderapi.repository.CreditCardRepository;
import com.cardapp.cardholderapi.repository.entity.cardholder.CardHolderEntity;
import com.cardapp.cardholderapi.repository.entity.creditcard.CreditCardEntity;
import com.cardapp.cardholderapi.service.ServiceVerifications;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCreditCardService {
    private final CardHolderMapper cardHolderMapper;
    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;
    private final ServiceVerifications serviceVerifications;

    public CreditCardResponse createCreditCard(UUID pathCardHolderId, CreditCardRequest creditCardRequest) {
        final CardHolderEntity cardHolderEntity = getCardHolderById(pathCardHolderId, creditCardRequest.cardHolderId());
        final CardHolderModel cardHolderModel = cardHolderMapper.modelFromEntity(cardHolderEntity);
        final CreditCardModel creditCardModel = creditCardMapper.modelFromRequest(creditCardRequest);
        final CreditCardModel creditCardGenerated = verifyAvailableCardHolderLimit(creditCardModel, cardHolderModel.creditLimit());
        final CreditCardEntity creditCardEntity = creditCardMapper.entityFromModel(creditCardGenerated);
        final CreditCardEntity creditCardEntitySaved = creditCardRepository.save(creditCardEntity);
        return creditCardMapper.responseFromEntity(creditCardEntitySaved);
    }

    private CreditCardModel verifyAvailableCardHolderLimit(CreditCardModel creditCardModel, BigDecimal cardHolderCreditLimit) {
        final BigDecimal availableLimit =
                serviceVerifications.verifyAvailableCardHolderLimit(creditCardModel.cardHolderId(), cardHolderCreditLimit);
        if (creditCardModel.limit().compareTo(availableLimit) > 0) {
            throw new RequestedCardLimitUnavailableException(
                    "Requested limit %s is greater than available limit %s.".formatted(creditCardModel.limit(), availableLimit));
        }
        return creditCardModel.generateCreditCard();
    }

    private CardHolderEntity getCardHolderById(UUID pathCardHolderId, UUID cardHolderId) {
        if (!pathCardHolderId.equals(cardHolderId)) {
            throw new PathCardHolderDoesNotMatchRequestCardHolderException("Path cardholderId doesn't match body cardHolderId");
        }
        return serviceVerifications.getCardHolderById(cardHolderId);
    }
}
