package com.cardapp.cardholderapi.mapper;

import com.cardapp.cardholderapi.controller.request.CardHolderRequest;
import com.cardapp.cardholderapi.controller.response.CardHolderResponse;
import com.cardapp.cardholderapi.model.cardholder.CardHolderModel;
import com.cardapp.cardholderapi.repository.entity.cardholder.CardHolderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardHolderMapper {

    CardHolderModel modelFromRequest(CardHolderRequest cardHolderRequest);

    CardHolderEntity entityFromModel(CardHolderModel cardHolderModel);

    CardHolderResponse responseFromEntity(CardHolderEntity cardHolderEntity);

    CardHolderModel modelFromEntity(CardHolderEntity cardHolderEntity);

}
