package com.simpletak.takscheduler.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TakschedulerApplicationProd {


	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(TakschedulerApplicationProd.class);
		application.setAdditionalProfiles("prod");
		application.run(args);

	}

}
