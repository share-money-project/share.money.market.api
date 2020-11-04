package share.money.market.api.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import share.money.commons.events.UserValidationResponseEvent;
import share.money.commons.events.WalletReservationResponseEvent;
import share.money.market.api.config.JmsConstants;
import share.money.market.api.service.MarketService;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletReservationListener {

    private final MarketService marketService;

    @RabbitListener(queues = JmsConstants.WALLET_RESERVATION_RESPONSE)
    public void listenForWalletReservationResponse(WalletReservationResponseEvent responseEvent) {
        marketService.processWalletReservationResponse(responseEvent.getOfferDto(), responseEvent.getReservationErrorExist(), responseEvent.getReservationErrorReason());

        log.debug(String.format("Wallet reservation for offerId  [%s] is [%s]", responseEvent.getOfferDto().getOfferId(), responseEvent.getReservationErrorReason()));
    }
}
