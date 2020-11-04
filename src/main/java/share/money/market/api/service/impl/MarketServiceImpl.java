package share.money.market.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.stereotype.Service;
import share.money.commons.dto.OfferAllocationState;
import share.money.commons.dto.OfferDto;
import share.money.market.api.repository.OfferRepository;
import share.money.market.api.repository.entity.OfferEntity;
import share.money.market.api.service.MarketService;
import share.money.market.api.shared.ModelMapper;
import share.money.market.api.stateMachine.domain.OfferAllocationEvent;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    public static final String OFFER_ID_HEADER = "OFFER_ID_HEADER";

    private final StateMachineFactory<OfferAllocationState, OfferAllocationEvent> stateMachineFactory;
    private final OfferRepository offerRepository;

    @Override
    @Transactional
    public OfferDto newOffer(OfferDto offerDto) {
        offerDto.setOfferId(UUID.randomUUID().toString());
        offerDto.setOfferAllocationState(OfferAllocationState.NEW);
        final OfferEntity saved = offerRepository.save(ModelMapper.map(offerDto, OfferEntity.class));
        sendEvent(saved, OfferAllocationEvent.VALIDATE_OFFER);
        return ModelMapper.map(saved, OfferDto.class);
    }


    @Override
    @Transactional
    public void deleteOffer(String offerId) {
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerId).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
        offerRepository.delete(offerEntity);
        //TODO: add state machine send update
    }

    @Transactional
    @Override
    public void processOfferValidation(OfferDto offerDto, Boolean errorExist, String validationErrorReason) {
        if (!errorExist) {
            offerValidationPassed(offerDto);
            final OfferEntity offerEntity = offerRepository.findByOfferId(offerDto.getOfferId()).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
            sendEvent(offerEntity, OfferAllocationEvent.RESERVE_OFFER);
        } else offerValidationFailed(offerDto, validationErrorReason);
    }

    @Transactional
    @Override
    public void processWalletReservationResponse(OfferDto offerDto, Boolean reservationErrorExist, String reservationErrorReason) {
        if (!reservationErrorExist) offerReservationPassed(offerDto);
        else offerReservationFailed(offerDto, reservationErrorReason);
    }

    private void offerValidationFailed(OfferDto offerDto, String validationErrorReason) {
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerDto.getOfferId()).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
        sendEvent(offerEntity, OfferAllocationEvent.VALIDATION_FAILED);
        offerEntity.setOfferAllocationState(OfferAllocationState.VALIDATION_EXCEPTION);
        offerRepository.save(offerEntity);
        //TODO: send validation reason to diff service
    }

    private void offerValidationPassed(OfferDto offerDto) {
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerDto.getOfferId()).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
        sendEvent(offerEntity, OfferAllocationEvent.VALIDATION_PASSED);
        offerEntity.setOfferAllocationState(OfferAllocationState.VALIDATED);
        offerRepository.save(offerEntity);
    }

    private void offerReservationFailed(OfferDto offerDto, String reservationErrorReason) {
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerDto.getOfferId()).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
        sendEvent(offerEntity, OfferAllocationEvent.RESERVATION_FAILED);
        offerEntity.setOfferAllocationState(OfferAllocationState.REJECTED);
        offerRepository.save(offerEntity);
        //TODO: send validation reason to diff service
    }

    private void offerReservationPassed(OfferDto offerDto) {
        final OfferEntity offerEntity = offerRepository.findByOfferId(offerDto.getOfferId()).orElseThrow(() -> new RuntimeException("Offer with such id doesn't exist"));
        sendEvent(offerEntity, OfferAllocationEvent.RESERVATION_PASSED);
        offerEntity.setOfferAllocationState(OfferAllocationState.ALLOCATED);
        offerRepository.save(offerEntity);
    }

    private void sendEvent(OfferEntity offerEntity, OfferAllocationEvent event) {
        StateMachine<OfferAllocationState, OfferAllocationEvent> sm = build(offerEntity);
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(OFFER_ID_HEADER, offerEntity.getOfferId())
                .build();
        sm.sendEvent(msg);
    }

    private StateMachine<OfferAllocationState, OfferAllocationEvent> build(OfferEntity offerEntity) {
        StateMachine<OfferAllocationState, OfferAllocationEvent> sm = stateMachineFactory.getStateMachine(offerEntity.getOfferId());

        sm.stop();

        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.resetStateMachine(new DefaultStateMachineContext<>(offerEntity.getOfferAllocationState(), null, null, null));
        });

        sm.start();
        return sm;
    }
}
