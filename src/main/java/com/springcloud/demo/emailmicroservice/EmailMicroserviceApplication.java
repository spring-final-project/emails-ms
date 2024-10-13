package com.springcloud.demo.emailmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmailMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailMicroserviceApplication.class, args);
	}
}
