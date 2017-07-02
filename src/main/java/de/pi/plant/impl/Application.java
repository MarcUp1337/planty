package de.pi.plant.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(Application.class, args);
		System.out.println("I'm running.");
	}
}
