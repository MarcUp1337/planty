package de.plant.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/plant/**").allowedOrigins("http://192.168.178.180", "http://localhost:63342",
            "http://raspberrypi", "http://localhost:8080").allowedMethods("GET", "PUT", "POST", "OPTIONS");
    }
}
