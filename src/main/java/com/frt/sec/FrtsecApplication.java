package com.frt.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FrtsecApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrtsecApplication.class, args);
	}

}
