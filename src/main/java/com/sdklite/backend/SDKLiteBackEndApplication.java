package com.sdklite.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SDKLiteBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SDKLiteBackEndApplication.class, args);
	}

}
