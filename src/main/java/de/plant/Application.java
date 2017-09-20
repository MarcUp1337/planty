package de.plant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"de.plant"})
@PropertySource({"classpath:application.properties"})
public class Application {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore","keystore");
		System.setProperty("javax.net.ssl.keyStorePassword","planty");
		System.setProperty("javax.net.ssl.trustStore","truststore");
		System.setProperty("javax.net.ssl.trustStorePassword","planty");

		SpringApplication.run(Application.class, args);
		System.out.println("I'm running.");
	}
}
