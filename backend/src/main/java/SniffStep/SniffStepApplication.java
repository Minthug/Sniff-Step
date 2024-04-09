package SniffStep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SniffStepApplication {

	public static void main(String[] args) {
		SpringApplication.run(SniffStepApplication.class, args);

	}

}
