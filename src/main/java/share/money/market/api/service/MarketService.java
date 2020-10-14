package share.money.market.api.service;

import share.money.market.api.externalservice.dto.TicketReservedDto;
import share.money.market.api.service.dto.TicketRequestDto;

public interface MarketService {

    void considerTicket(TicketRequestDto ticketRequestDto);

    void ticketReserved(TicketReservedDto ticketReservedDto);
}
