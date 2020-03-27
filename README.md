# Mancalah API

Java 8 / Spring Boot REST API that runs a game of 6-stone Kalah. The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah and also below in this document. 


### TODO's
- Implementing and testing Game Over
- Implementing and testing winner once game over
- Implementing and testing varoius pit/stone configurations

### Future Work
- Using persistence mechanism to keep states of games
- Testing and implementing game over feature
- getting status without making a move
- moves audit trail
- player hall of fame
- frontend

---

### Project outline

- The project was built in a contract-first manner, the public API 
and models are defined in `resources/contract.yml` 
- The API interface and models are generated
- Swagger UI is accessible from `http://localhost:8080/swagger-ui.html`
- jacoco coverage reports can be found at `build/jacoco-reports`
- Integration tests use the Cucumber framework, feature files are in `src/test/resources`


### Startup guide

* build the project with gradle `gradle build`
* Running without "prod" profile will enable deleting all games by per test/reset endpoint
* start the application by either `java -jar build/libs/kalah-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod` or `docker-compose up`
* check `http://localhost:8080/swagger-ui.html` for available endpoints
* import `src/main/resources/contract.yml` to PostMan
