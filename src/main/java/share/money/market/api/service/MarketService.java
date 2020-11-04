package share.money.market.api.service;

import share.money.commons.dto.OfferDto;

public interface MarketService {

    OfferDto newOffer(OfferDto offerDto);

    void deleteOffer(String offerId);

    void processOfferValidation(OfferDto offerDto, Boolean isValid, String validationErrorReason);

    void processWalletReservationResponse(OfferDto offerDto, Boolean reservationErrorExist, String reservationErrorReason);
}
