package org.k9m.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "org.k9m.kalah.persistence")
public class KalahApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahApiApplication.class, args);
	}

}
