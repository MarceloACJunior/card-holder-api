package com.cardapp.cardholderapi.mapper;

import com.cardapp.cardholderapi.controller.request.LimitUpdateRequest;
import com.cardapp.cardholderapi.controller.response.LimitUpdateResponse;
import com.cardapp.cardholderapi.model.LimitUpdateModel;
import com.cardapp.cardholderapi.repository.entity.creditcard.CreditCardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LimitUpdateMapper {


    LimitUpdateModel modelFromRequest(LimitUpdateRequest limitUpdateRequest);

    @Mapping(source = "id", target = "cardId")
    @Mapping(source = "cardLimit", target = "updatedLimit")
    LimitUpdateResponse responseFromEntity(CreditCardEntity creditCardEntity);
}
