package com.example.AuthMicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroservicesApplication.class, args);
	}

}
