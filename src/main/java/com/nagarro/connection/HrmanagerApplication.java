package com.nagarro.connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HrmanagerApplication {

	public static void main(String[] args) {
		System.out.println("I am here");
		SpringApplication.run(HrmanagerApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
