# Mancalah API

Java 8 / Spring Boot REST API that runs a gameManager of 6-stone Kalah. The general rules of the gameManager are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah and also below in this document. 


### TODO's
- Implementing and testing varoius pit/stone configurations

### Future Work
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
- Authentication is via Oauth2 using Keycloak as auth server
- MongodDB is used


### Startup guide

Create a `.env` file in the root of the project and populate it with the users and passwords or supply them in the VM as you want:
```
MYSQL_ROOT_PASSWORD=
MYSQL_USER=
MYSQL_PASSWORD=

KEYCLOAK_DB=
KEYCLOAK_USER=
KEYCLOAK_PASSWORD=
KEYCLOAK_SECRET=
```

* Please make sure your firewall allows the build to download the embedded mongodb binaries for tests!
* build the project with gradle `gradle build`
* Start the services with `docker-compose -f docker-compose-services.yml up` (THIS NEEDS TO BE DONE before the app!)
* start the application by either `java -jar build/libs/kalah-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=local`
* check `http://localhost:8080/swagger-ui.html` for available endpoints
* import `src/main/resources/contract.yml` to PostMan
* Get the Oauth token with below details

### Oauth
* Realm: `kalah-api`
* Client ID: `kalah-player`
* password: `password` (This has to be changed at first logon to Keycloak)
* access token url: `http://localhost:8081/auth/realms/kalah-api/protocol/openid-connect/token`

### Notes
* I couldn't figure out why it won't work when it's all in one container, so the app needs to be run outside the container AFTER keycloak has started

