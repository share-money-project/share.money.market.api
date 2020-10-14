package share.money.market.api.shared;

public enum TicketStatus {
    TICKET_CREATED("ticket_created");

    private String status;

    TicketStatus(String status) {
        this.status = status;
    }

    public String val() {
        return status;
    }
}
