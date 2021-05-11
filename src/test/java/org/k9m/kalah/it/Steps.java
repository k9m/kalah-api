package org.k9m.kalah.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.ErrorObject;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.it.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class Steps {

    @Autowired
    private TestClient testClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private CreateGameResponse lastCreatedGame;
    private GameStatus lastStatus;
    private HttpClientErrorException lastThrownException;

    @Given("the system has started up")
    public void theSystemHasStartedUp() {
        final String healthCheckString = testClient.healthCheck();
        assertThat(healthCheckString).contains("UP");
    }

    @Given("no games are present")
    public void emptyGames() {
        mongoTemplate.getCollectionNames().forEach(cn -> mongoTemplate.dropCollection(cn));
    }

    @When("a new game is created")
    public void gameCreated() {
        lastCreatedGame = testClient.createGame();
    }

    @Then("a response should be returned with a link to the new game")
    public void assertCreatedGame() {
        lastCreatedGame = testClient.createGame();

        assertThat(lastCreatedGame.getLink()).isEqualTo(("http://localhost:" + testClient.getServerPort() + "/games/" + lastCreatedGame.getId()));
    }

    @When("making a move from pit {int} from this game")
    public void executeMove(int pitId) {
        try {
            lastStatus = testClient.executeMove(lastCreatedGame.getId(), pitId);
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @When("^trying to make a move for a game with id (.*) that doesn't exist$")
    public void gameDoesntExist(String id) {
        try {
            lastStatus = testClient.executeMove(id, 1);
        } catch (HttpClientErrorException e) {
            lastThrownException = e;
        }
    }

    @Then("^the state of the game should be (.*)$")
    public void theStateOfTheGameShouldBe(String expectedState) {
        assertThat(lastStatus.getState()).isEqualTo(expectedState);
    }

    @Then("the status of the game should be")
    public void theStatusOfTheGameShouldBe(DataTable table) {
        Map<String, String> expectedStatuses = table.asMaps().get(0);

        assertThat(lastStatus.getId()).isEqualTo(lastCreatedGame.getId());
        assertThat(lastStatus.getLink()).isEqualTo(lastCreatedGame.getLink());
        assertThat(lastStatus.getStatus()).isEqualTo(expectedStatuses);
    }

    @Then("^an error should be returned with message: (.*) and status code (\\d+)$")
    public void anErrorShouldBeReturnedWithMessageAndStatusCode(String message, int statusCode) throws JsonProcessingException {
        assertThat(lastThrownException).isNotNull();
        final ErrorObject errorObject = objectMapper.readValue(lastThrownException.getResponseBodyAsString(), ErrorObject.class);

        assertThat(errorObject.getMessage()).isEqualTo(message);
        assertThat(errorObject.getStatusCode()).isEqualTo(statusCode);
    }


}
