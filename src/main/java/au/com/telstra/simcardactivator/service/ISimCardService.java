package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.entity.ActuationResult;
import au.com.telstra.simcardactivator.entity.SimCard;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ISimCardService {
    ActuationResult actuate(SimCard simCard);
    SimCard findById(Long simCardId);
}
