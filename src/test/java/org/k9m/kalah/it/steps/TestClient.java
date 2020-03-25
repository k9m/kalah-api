package org.k9m.kalah.it.steps;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestClient {

    @LocalServerPort
    private int serverPort;

    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;

    @PostConstruct
    private void init(){
        baseUrl = "http://localhost:" + serverPort;
    }

    public String healthCheck(){
        return restTemplate.getForObject(baseUrl + "/actuator/health", String.class);
    }

//    public CurrentBalance balanceCheck(final Long accountNumber){
//        return restTemplate.getForObject(baseUrl + "/accounts/"+ accountNumber + "/balance", CurrentBalance.class);
//    }
//
//    public TransactionResponse execute(final Long accountNumber, Transaction.TypeEnum type, final double amount){
//        final Transaction transaction = new Transaction();
//        transaction.setAmount(amount);
//        transaction.setType(type);
//        transaction.setTimestamp(OffsetDateTime.now());
//
//        return restTemplate.postForObject(baseUrl + "/accounts/"+ accountNumber + "/transaction", transaction, TransactionResponse.class);
//    }

}
