package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.entity.SimCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SimCardRepository extends CrudRepository<SimCard, Long> {
    SimCard save(SimCard simCard);
    Optional<SimCard> findById(Long simCardId);
}
