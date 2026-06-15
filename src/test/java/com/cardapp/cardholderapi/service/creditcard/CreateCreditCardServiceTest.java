package com.cardapp.cardholderapi.service.creditcard;

import static com.cardapp.cardholderapi.service.cardholder.CardHolderFactory.cardHolderEntityFactory;
import static com.cardapp.cardholderapi.service.creditcard.CreditCardFactory.creditCardEntityFactory;
import static com.cardapp.cardholderapi.service.creditcard.CreditCardFactory.creditCardRequestFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.cardapp.cardholderapi.controller.request.CreditCardRequest;
import com.cardapp.cardholderapi.controller.response.CreditCardResponse;
import com.cardapp.cardholderapi.handler.exceptions.CardHolderNotFoundException;
import com.cardapp.cardholderapi.handler.exceptions.PathCardHolderDoesNotMatchRequestCardHolderException;
import com.cardapp.cardholderapi.handler.exceptions.RequestedCardLimitUnavailableException;
import com.cardapp.cardholderapi.mapper.CardHolderMapper;
import com.cardapp.cardholderapi.mapper.CardHolderMapperImpl;
import com.cardapp.cardholderapi.mapper.CreditCardMapper;
import com.cardapp.cardholderapi.mapper.CreditCardMapperImpl;
import com.cardapp.cardholderapi.repository.CardHolderRepository;
import com.cardapp.cardholderapi.repository.CreditCardRepository;
import com.cardapp.cardholderapi.repository.entity.creditcard.CreditCardEntity;
import com.cardapp.cardholderapi.service.ServiceVerifications;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CreateCreditCardServiceTest {

    @Captor
    ArgumentCaptor<CreditCardEntity> cardEntityCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidCaptor;
    @Captor
    ArgumentCaptor<BigDecimal> creditLimitCaptor;

    @Mock
    private ServiceVerifications serviceVerifications;
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private CardHolderRepository cardHolderRepository;

    @Spy
    private CardHolderMapperImpl cardHolderMapper;
    @Spy
    private CreditCardMapperImpl creditCardMapper;

    @InjectMocks
    private CreateCreditCardService createCreditCardService;

    @Test
    void should_create_credit_card() {
        when(serviceVerifications.getCardHolderById(uuidCaptor.capture())).thenReturn(cardHolderEntityFactory());
        when(serviceVerifications.verifyAvailableCardHolderLimit(uuidCaptor.capture(),creditLimitCaptor.capture())).thenReturn(BigDecimal.valueOf(100000));
        when(creditCardRepository.save(cardEntityCaptor.capture())).thenReturn(creditCardEntityFactory());
        final CreditCardResponse creditCardResponse =
                createCreditCardService.createCreditCard(creditCardRequestFactory().cardHolderId(), creditCardRequestFactory());
        assertNotNull(creditCardResponse);
        assertEquals(creditCardResponse.limit(), creditCardRequestFactory().limit());
    }

    @Test
    void should_throw_RequestedCardLimitUnavailableException_when_requested_limit_is_greater_than_available_limit() {
        final BigDecimal creditLimit = cardHolderEntityFactory().getCreditLimit();
        when(serviceVerifications.getCardHolderById(uuidCaptor.capture())).thenReturn(cardHolderEntityFactory());
        when(serviceVerifications.verifyAvailableCardHolderLimit(uuidCaptor.capture(),creditLimitCaptor.capture())).thenReturn(creditLimit);
        final CreditCardRequest creditCardRequest = creditCardRequestFactory().toBuilder().limit(BigDecimal.valueOf(100_000)).build();

        final RequestedCardLimitUnavailableException exception = assertThrows(RequestedCardLimitUnavailableException.class,
                () -> createCreditCardService.createCreditCard(creditCardRequest.cardHolderId(), creditCardRequest));
        assertEquals("Requested limit %s is greater than available limit %s.".formatted(creditCardRequest.limit(),
                creditLimit), exception.getMessage());
    }

    @Test
    void should_throw_PathCardHolderDoesNotMatchRequestCardHolderException() {
        final PathCardHolderDoesNotMatchRequestCardHolderException exception =
                assertThrows(PathCardHolderDoesNotMatchRequestCardHolderException.class,
                        () -> createCreditCardService.createCreditCard(UUID.randomUUID(), creditCardRequestFactory()));
        assertEquals("Path cardholderId doesn't match body cardHolderId", exception.getMessage());
    }

}