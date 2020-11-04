package share.money.market.api.stateMachine;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import share.money.commons.dto.OfferAllocationState;
import share.money.market.api.stateMachine.domain.OfferAllocationEvent;

import java.util.EnumSet;

import static share.money.commons.dto.OfferAllocationState.*;
import static share.money.market.api.stateMachine.domain.OfferAllocationEvent.*;

@Configuration
@EnableStateMachineFactory
@AllArgsConstructor
public class OfferAllocationStateMachineConfig extends StateMachineConfigurerAdapter<OfferAllocationState, OfferAllocationEvent> {

    private Action<OfferAllocationState, OfferAllocationEvent> userValidationAction;
    private Action<OfferAllocationState, OfferAllocationEvent> offerReservationAction;

    @Override
    public void configure(StateMachineStateConfigurer<OfferAllocationState, OfferAllocationEvent> states) throws Exception {
        states.withStates()
                .initial(NEW)
                .states(EnumSet.allOf(OfferAllocationState.class))
                .end(ALLOCATED)
                .end(REJECTED)
                .end(VALIDATION_EXCEPTION);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OfferAllocationState, OfferAllocationEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(NEW).target(VALIDATION_PENDING).event(VALIDATE_OFFER).action(userValidationAction)
                .and().withExternal()
                .source(VALIDATION_PENDING).target(VALIDATED).event(VALIDATION_PASSED)
                .and().withExternal()
                .source(VALIDATION_PENDING).target(VALIDATION_EXCEPTION).event(VALIDATION_FAILED)
                .and().withExternal()
                .source(VALIDATED).target(OfferAllocationState.RESERVATION_PENDING).event(RESERVE_OFFER).action(offerReservationAction)
                .and().withExternal()
                .source(RESERVATION_PENDING).target(REJECTED).event(RESERVATION_FAILED)
                .and().withExternal()
                .source(RESERVATION_PENDING).target(ALLOCATED).event(RESERVATION_PASSED);
    }
}
