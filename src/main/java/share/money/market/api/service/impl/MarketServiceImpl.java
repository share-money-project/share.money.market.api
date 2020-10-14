package share.money.market.api.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.money.market.api.config.SpringRabbitConfig;
import share.money.market.api.externalservice.dto.TicketReservedDto;
import share.money.market.api.repository.TicketRepository;
import share.money.market.api.repository.entity.TicketEntity;
import share.money.market.api.service.MarketService;
import share.money.market.api.service.dto.TicketRequestDto;
import share.money.market.api.shared.ModelMapper;
import share.money.market.api.shared.TicketStatus;

import java.util.UUID;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TicketRepository repository;

    @Override
    public void considerTicket(TicketRequestDto ticketRequestDto) {

        ticketRequestDto.setTicketId(UUID.randomUUID().toString());
        ticketRequestDto.setTicketStatus(TicketStatus.TICKET_CREATED.val());
        TicketEntity entity = ModelMapper.map(ticketRequestDto, TicketEntity.class);
        repository.save(entity);

        amqpTemplate.convertAndSend(SpringRabbitConfig.EXCHANGE_TICKET, SpringRabbitConfig.KEY_TICKET_CREATE, ticketRequestDto);
    }

    @Override
    public void ticketReserved(TicketReservedDto ticketReservedDto) {
        TicketEntity byTicketId = repository.findByTicketId(ticketReservedDto.getTicketId());
        if (byTicketId == null) throw new RuntimeException(String.format("Ticket with such ticket id [%s] doesn't exist", ticketReservedDto.getTicketId()));
        byTicketId.setTicketStatus(ticketReservedDto.getTicketStatus());
        repository.save(byTicketId);
    }
}
