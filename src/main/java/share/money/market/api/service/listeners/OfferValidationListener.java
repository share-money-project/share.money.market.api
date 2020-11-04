package share.money.market.api.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import share.money.commons.events.UserValidationResponseEvent;
import share.money.market.api.config.JmsConstants;
import share.money.market.api.service.MarketService;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfferValidationListener {

    private final MarketService marketService;

    @RabbitListener(queues = JmsConstants.USER_VALIDATE_RESPONSE)
    public void listenForUserValidationResponse(UserValidationResponseEvent responseEvent) {
        marketService.processOfferValidation(responseEvent.getOfferDto(), responseEvent.getValidationErrorExist(), responseEvent.getValidationErrorReason());

        log.debug(String.format("Offer validation for offerId  [%s] is [%s]", responseEvent.getOfferDto().getOfferId(), responseEvent.getValidationErrorExist()));
    }
}
