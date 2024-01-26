package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.entity.ActuationResult;
import au.com.telstra.simcardactivator.entity.SimCard;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080";
    private final String ACTIVATION_URL = BASE_URL + "/activate";
    private final String CHECKER_URL = BASE_URL + "/findById";
    private final String ACTUACTOR_URL = "http://localhost:8444/actuate";
    private ResponseEntity<ActuationResult> response;
    private ResponseEntity<SimCard> reponseSimCard;
    private SimCard simCard;
    @Given("a functional sim card")
    public void configureFunctionalSimCard() {
        simCard = new SimCard("12345678901234567890", "test@gmail.com");
    }

    @Given("a broken sim card")
    public void configureBrokenSimCard() {
        simCard = new SimCard("8944500102198304826", "test2@gmail.com");
    }

    @When("I submit an activation request")
    public void submitValidationRequest() {
        response = restTemplate.postForEntity(ACTIVATION_URL, simCard, ActuationResult.class);

    }

    @Then("the activation is marked as true in the database")
    public void activationMarkedTrue() {
        SimCard sC = restTemplate.getForObject(CHECKER_URL + "/1", SimCard.class);
        assertTrue(sC.getActive());
    }
    // Failed activation
    @Then("the activation is marked as false in the database")
    public void activationMarkedFalse() {
        SimCard sC = restTemplate.getForObject(CHECKER_URL + "/2", SimCard.class);
        assertFalse(sC.getActive());
    }
}