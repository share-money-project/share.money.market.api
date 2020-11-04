package share.money.market.api.stateMachine;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import share.money.commons.dto.OfferAllocationState;
import share.money.market.api.repository.OfferRepository;
import share.money.market.api.repository.entity.OfferEntity;
import share.money.market.api.service.impl.MarketServiceImpl;
import share.money.market.api.stateMachine.domain.OfferAllocationEvent;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class OfferAllocationStateChangeInterceptor extends StateMachineInterceptorAdapter<OfferAllocationState, OfferAllocationEvent> {

    private final OfferRepository offerRepository;

    @Override
    public void preStateChange(State<OfferAllocationState, OfferAllocationEvent> state, Message<OfferAllocationEvent> message, Transition<OfferAllocationState, OfferAllocationEvent> transition, StateMachine<OfferAllocationState, OfferAllocationEvent> stateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(MarketServiceImpl.OFFER_ID_HEADER, " ")))
                .ifPresent(offerId -> {
                    log.debug("Saving state for order id: " + offerId + " Status: " + state.getId());

                    final OfferEntity offerEntity = offerRepository.findByOfferId(offerId).orElseThrow(() -> new RuntimeException("No such offer"));
                    offerEntity.setOfferAllocationState(state.getId());
                    offerRepository.save(offerEntity);
                });
    }

}
