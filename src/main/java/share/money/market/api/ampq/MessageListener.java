package share.money.market.api.ampq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.money.market.api.config.SpringRabbitConfig;
import share.money.market.api.externalservice.dto.TicketReservedDto;
import share.money.market.api.service.MarketService;

@Service
public class MessageListener {

    public static final String QUEUE_TICKET_RESERVE = SpringRabbitConfig.QUEUE_TICKET_RESERVE;

    @Autowired
    private MarketService marketService;

    @RabbitListener(queues = QUEUE_TICKET_RESERVE)
    public void receiveMessage(TicketReservedDto ticketReservedDto) {
        marketService.ticketReserved(ticketReservedDto);
    }
}
