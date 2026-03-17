package com.example.HospitalMicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HospitalMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalMicroservicesApplication.class, args);
	}

}
