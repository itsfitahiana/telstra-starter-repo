package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.entity.ActuationResult;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.service.ISimCardService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimActivationController {
    private final ISimCardService simCardActuationHandler;

    public SimActivationController(ISimCardService simCardActuationHandler) {
        this.simCardActuationHandler = simCardActuationHandler;
    }

    @PostMapping(value = "/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        ActuationResult actuationResult = simCardActuationHandler.actuate(simCard);
        System.out.println(actuationResult.getSuccess());
    }

    @GetMapping(value = "/findById/{simCardId}")
    public SimCard findById(@PathVariable int simCardId) {
        Long simCardIdLong = Long.valueOf(simCardId);
        return simCardActuationHandler.findById(simCardIdLong);
    }
}
