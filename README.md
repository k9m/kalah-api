# Bank Account Manager

Java 8 / Spring Boot microservice which will allow a user to administer a customer’s bank account.

- ~~request the current balance~~
- ~~capture a withdrawal~~
- ~~capture a deposit~~
- debit/credit interest amount

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
