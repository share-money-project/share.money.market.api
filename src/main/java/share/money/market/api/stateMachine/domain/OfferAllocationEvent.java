package share.money.market.api.stateMachine.domain;

public enum OfferAllocationEvent {
    VALIDATE_OFFER, VALIDATION_PASSED, VALIDATION_FAILED,
    RESERVE_OFFER, RESERVATION_PASSED, RESERVATION_FAILED;
}
