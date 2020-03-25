package org.k9m.kalah.it.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonSteps {

    @Autowired
    private TestClient testClient;

    private CreateGameResponse lastCreatedGame;

    @Given("the system has started up")
    public void theSystemHasStartedUp() {
        final String healthCheckString = testClient.healthCheck();
        assertThat(healthCheckString, containsString("UP"));
    }

    @Given("the database is empty")
    public void theDatabaseIsEmpty() {

    }

    @When("a new game is created")
    public void gameCreated() {
        lastCreatedGame = testClient.createGame();
    }

    @Then("a response should be returned with a link to the new game")
    public void assertCreatedGame() {
        lastCreatedGame = testClient.createGame();

        assertThat(lastCreatedGame.getLink(), is("http://localhost:" + testClient.getServerPort() + "/games/" + lastCreatedGame.getId()));
    }


//    @DataTableType
//    public Account authorEntry(Map<String, String> entry) {
//        return new Account()
//                .setAccountNumber(Long.parseLong(entry.get("accountNumber")))
//                .setBalance(Double.parseDouble(entry.get("balance")))
//                .setSortCode(entry.get("sortCode"));
//    }
}
