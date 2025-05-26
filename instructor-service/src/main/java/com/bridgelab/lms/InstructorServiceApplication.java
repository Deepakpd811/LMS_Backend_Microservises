package com.bridgelab.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InstructorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstructorServiceApplication.class, args);
	}

}
