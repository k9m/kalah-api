package org.k9m.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@EnableMongoRepositories(basePackages = "org.k9m.kalah.persistence")
public class KalahApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahApiApplication.class, args);
	}

}
