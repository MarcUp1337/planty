package de.pi.plant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"de.pi.plant", "de.plant"})
public class Application {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(Application.class, args);
		System.out.println("I'm running.");
	}
}
