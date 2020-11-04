package share.money.market.api.stateMachine.action;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import share.money.commons.dto.OfferAllocationState;
import share.money.commons.dto.OfferDto;
import share.money.commons.events.WalletReservationRequestEvent;
import share.money.market.api.config.JmsConstants;
import share.money.market.api.repository.OfferRepository;
import share.money.market.api.repository.entity.OfferEntity;
import share.money.market.api.service.impl.MarketServiceImpl;
import share.money.market.api.shared.ModelMapper;
import share.money.market.api.stateMachine.domain.OfferAllocationEvent;

@Slf4j
@Component(value = "offerReservationAction")
@RequiredArgsConstructor
public class OfferReservationAction implements Action<OfferAllocationState, OfferAllocationEvent> {

    private final AmqpTemplate amqpTemplate;
    private final OfferRepository offerRepository;

    @Override
    public void execute(StateContext<OfferAllocationState, OfferAllocationEvent> context) {
        final String offerId = ((String) context.getMessageHeaders().getOrDefault(MarketServiceImpl.OFFER_ID_HEADER, ""));
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerId).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));

        amqpTemplate.convertAndSend(JmsConstants.WALLET_EXCHANGE, JmsConstants.WALLET_RESERVE_REQUEST_KEY,
                WalletReservationRequestEvent.builder().offerDto(ModelMapper.map(offerEntity, OfferDto.class)).build());

        log.debug("Offer validation request sent. Offer id: " + offerId);
    }
}
