package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.entity.ActuationResult;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class SimCardService implements ISimCardService {

    private final RestTemplate restTemplate;
    private final String incentiveApiUrl;
    private final SimCardRepository simCardRepository;

    public SimCardService(RestTemplateBuilder builder, SimCardRepository simCardRepository) {
        this.restTemplate = builder.build();
        this.incentiveApiUrl = "http://localhost:8444/actuate";
        this.simCardRepository = simCardRepository;
    }
    public ActuationResult actuate(SimCard simCard) {
        Optional<ActuationResult> result  = Optional.ofNullable(restTemplate.postForObject(incentiveApiUrl, simCard,
                ActuationResult.class));
        if(!result.isPresent()) {
            throw new RuntimeException("Actuation failed");
        }
        simCard.setActive(result.get().getSuccess());
        this.simCardRepository.save(simCard);
        return result.get();
    }

    public SimCard findById(Long simCardId) {
        Optional<SimCard> simCard = this.simCardRepository.findById(simCardId);
        if(!simCard.isPresent()) {
            throw new RuntimeException("Sim card not found");
        }
        return simCard.get();
    }
}
