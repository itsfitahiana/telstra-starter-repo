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
    private String BASE_URL;
    private String ACTIVATION_URL;
    private String CHECKER_URL;
    private String ACTUACTOR_URL;
    private String mail;

    private ResponseEntity<ActuationResult> response;
    private ResponseEntity<SimCard> reponseSimCard;
    @Given("the SIM Card Actuator is running")
    public void simCardActuatorIsRunning() {
        BASE_URL = "http://localhost:8080";
        ACTIVATION_URL = BASE_URL + "/activate";
        CHECKER_URL = BASE_URL + "/findById";
        ACTUACTOR_URL = "http://localhost:8444/actuate";
        mail = "test@gmail.com";
    }

    @ParameterType("\"([^\"]*)\"")
    public String iccid(String iccid) {
        return iccid;
    }

    @ParameterType("\"([^\"]*)\"")
    public String id(String id) {
        return id;
    }
    @When("I submit an activation request with ICCID {iccid} to the microservice")
    public void submitValidationRequest(String iccid) {
        SimCard simCard = new SimCard();
        simCard.setIccid(iccid);
        simCard.setCustomerEmail(mail);
        response = restTemplate.postForEntity(ACTIVATION_URL, simCard, ActuationResult.class);
    }

    @Then("the activation request is successfully processed")
    public void activationRequestProcessedSuccessfully() {
        assertEquals(200, response.getStatusCodeValue());
    }

    @When("I submit a request to check the ID {id} activation status")
    public void submitValidationChecker(String id) {
        String FIND_BY_ID = CHECKER_URL+"/"+id;
        reponseSimCard = restTemplate.getForEntity(FIND_BY_ID, SimCard.class);
    }

    @Then("the activation is marked as true in the database")
    public void activationMarkedTrue() {
        assertTrue(reponseSimCard.getBody().getActive());
    }
    // Failed activation
    @Then("the activation is marked as false in the database")
    public void activationMarkedFalse() {
        assertFalse(reponseSimCard.getBody().getActive());
    }
}