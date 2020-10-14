package share.money.market.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import share.money.market.api.controller.model.TicketRequestModel;
import share.money.market.api.service.MarketService;
import share.money.market.api.service.dto.TicketRequestDto;
import share.money.market.api.shared.ModelMapper;

@RestController
@RequestMapping(path = "/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @PostMapping
    public void ticketCreated(@RequestBody TicketRequestModel ticketRequestModel) {
        TicketRequestDto ticketRequestDto = ModelMapper.map(ticketRequestModel, TicketRequestDto.class);
        marketService.considerTicket(ticketRequestDto);
    }
}
