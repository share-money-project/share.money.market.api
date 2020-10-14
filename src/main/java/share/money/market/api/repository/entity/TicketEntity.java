package share.money.market.api.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tickets_info")
public class TicketEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "ticket_id", nullable = false, length = 100)
    private String ticketId;

    @Column(name = "ticket_type", nullable = false, length = 30)
    private String ticketType;

    @Column(name = "ticket_status", nullable = false, length = 20)
    private String ticketStatus;

    @Column(name = "amount", nullable = false, length = 5)
    private Double amount;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
