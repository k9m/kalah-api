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
import org.k9m.kalah.api.model.GameHistoryResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.it.util.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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

    @Then("a response should be returned with an id generated")
    public void assertCreatedGame() {
        lastCreatedGame = testClient.createGame();
        assertThat(lastCreatedGame.getId()).isNotNull();
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

    @Then("^the state of the game should be (.*) and player turn (.*)$")
    public void theStateOfTheGameShouldBe(String expectedState, String playerTurn) {
        assertThat(lastStatus.getState()).isEqualTo(expectedState);
        assertThat(lastStatus.getPlayerTurn()).isEqualTo(playerTurn);
    }


    @When("^checked via the API, the state of the game should be (.*)$")
    public void stateViaAPI(String expectedState) {
        GameStatus status = testClient.getStatus(lastCreatedGame.getId());
        assertThat(status.getState()).isEqualTo(expectedState);
    }

    @Then("the status of the game should be")
    public void theStatusOfTheGameShouldBe(DataTable table) {
        Map<String, String> expectedStatuses = table.asMaps().get(0);

        assertThat(lastStatus.getId()).isEqualTo(lastCreatedGame.getId());
        assertThat(lastStatus.getStatus()).isEqualTo(expectedStatuses);
    }

    @Then("^an error should be returned with message: (.*) and status code (\\d+)$")
    public void anErrorShouldBeReturnedWithMessageAndStatusCode(String message, int statusCode) throws JsonProcessingException {
        assertThat(lastThrownException).isNotNull();
        final ErrorObject errorObject = objectMapper.readValue(lastThrownException.getResponseBodyAsString(), ErrorObject.class);

        assertThat(errorObject.getMessage()).isEqualTo(message);
        assertThat(errorObject.getStatusCode()).isEqualTo(statusCode);
    }


    @Then("checking the history for this game should display the following")
    public void checkingTheHistoryForThisGameShouldDisplayTheFollowing(DataTable table) {
        GameHistoryResponse historyResponse = testClient.getHistory(lastCreatedGame.getId());
        AtomicInteger index = new AtomicInteger();
        table.asMaps().forEach(
                row -> assertThat(historyResponse
                    .getHistory()
                    .get(index.getAndIncrement()).getMoves())
                    .isEqualTo(new ArrayList(row.values().stream().map(Integer::parseInt).collect(Collectors.toList())))
        );
    }
}
