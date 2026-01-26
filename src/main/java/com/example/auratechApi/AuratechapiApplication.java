package com.example.auratechApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuratechapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuratechapiApplication.class, args);
	}

}
