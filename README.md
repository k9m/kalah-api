# Bank Account Manager

Java 8 / Spring Boot REST API that runs a game of 6-stone Kalah. The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah and also below in this document. 

###


### Future Work
- Implementing capture opponent's stones game style
- Using persistence mechanism to keep states of games
---

### Project outline

- The project was built in a contract-first manner
- The public API and models are defined in `resources/contract.yml` 
- The API interface and models are generated
- Swagger UI is accessible from `http://localhost:8080/swagger-ui.html`
- A set of canned accounts are loaded at startup from `resources/data.sql`
- jacoco coverage reports can be found at `build/jacoco-reports`
- Integration tests use the Cucumber framework


### Startup guide

* build the project with gradle `gradle build`
* start the application by either `java -jar build/libs/example-account-0.0.1-SNAPSHOT.jar` or `docker-compose up`
* check `http://localhost:8080/swagger-ui.html` for available endpoints
