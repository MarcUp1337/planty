package de.pi.plant;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://192.168.178.180", "http://localhost:63342",
            "http://raspberrypi").allowedMethods("GET", "PUT", "POST", "OPTIONS");
    }
}
