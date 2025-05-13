package com.sw.output;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OutputApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutputApplication.class, args);
	}

}
