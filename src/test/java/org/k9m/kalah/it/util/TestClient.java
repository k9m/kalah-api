package org.k9m.kalah.it.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.ApiClient;
import org.k9m.kalah.api.DefaultApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Slf4j
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestClient {

    @LocalServerPort
    @Getter
    private int serverPort;

    @Autowired private DefaultApi api;
    @Autowired private ApiClient apiClient;
    @Autowired private RestTemplate restTemplate;

    private String baseUrl;

    @PostConstruct
    private void init(){
        baseUrl = "http://localhost:" + serverPort;
        apiClient.setBasePath(baseUrl);
        log.info("FPS API Basepath set to ${appClient.basePath}");
    }

    public String healthCheck(){
        return restTemplate.getForObject(baseUrl + "/actuator/health", String.class);
    }
    public DefaultApi api(){
        return api;
    }

}
