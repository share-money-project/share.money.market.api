package share.money.market.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import share.money.market.api.repository.entity.TicketEntity;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, Long> {
    TicketEntity findByTicketId(String ticketId);
}
