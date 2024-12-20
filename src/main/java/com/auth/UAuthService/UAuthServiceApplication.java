package com.auth.UAuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.library.models")
public class UAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UAuthServiceApplication.class, args);
	}

}
