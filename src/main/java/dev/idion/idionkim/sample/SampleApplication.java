package dev.idion.idionkim.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SampleApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
