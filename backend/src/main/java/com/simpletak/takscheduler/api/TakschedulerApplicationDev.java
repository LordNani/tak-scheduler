package com.simpletak.takscheduler.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy
@EnableAsync
@SpringBootApplication
public class TakschedulerApplicationDev {
	public static void main(String[] args) {
		SpringApplication application =
				new SpringApplication(TakschedulerApplicationDev.class);
		application.setAdditionalProfiles("dev");
		application.run(args);
	}
}
