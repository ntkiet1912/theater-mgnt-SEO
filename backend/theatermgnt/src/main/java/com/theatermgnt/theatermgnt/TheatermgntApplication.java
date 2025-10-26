package com.theatermgnt.theatermgnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheatermgntApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheatermgntApplication.class, args);


	}

}
