package com.simpletak.takscheduler.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy
@EnableAsync
@SpringBootApplication
public class TakschedulerApplicationProd {


	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(TakschedulerApplicationProd.class);
		application.setAdditionalProfiles("prod");
		application.run(args);

	}

}
