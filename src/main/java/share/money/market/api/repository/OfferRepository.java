package share.money.market.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import share.money.market.api.repository.entity.OfferEntity;

import java.util.Optional;

@Repository
public interface OfferRepository extends CrudRepository<OfferEntity, Long> {
    Optional<OfferEntity> findByOfferId(String offerId);
}
