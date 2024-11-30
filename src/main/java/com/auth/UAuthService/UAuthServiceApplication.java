package com.auth.UAuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UAuthServiceApplication.class, args);
	}

}
