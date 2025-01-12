package com.amalvadkar.lms;

import org.springframework.boot.SpringApplication;

public class TestLmsServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
