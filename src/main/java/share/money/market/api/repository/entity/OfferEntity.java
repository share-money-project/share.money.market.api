package share.money.market.api.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import share.money.commons.dto.OfferAllocationState;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "offer_id", nullable = false)
    private String offerId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "offer_allocation_state", nullable = false)
    private OfferAllocationState offerAllocationState;

    @CreationTimestamp
    @Column(name = "create_Date", updatable = false)
    private Timestamp createDate;

    @UpdateTimestamp
    @Column(name = "last_Modified_Date")
    private Timestamp lastModifiedDate;

    @Column(name = "amount")
    private Double amount;

}
