package com.yuye.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.yuye"})
@EnableFeignClients
public class ExampleClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleClientApplication.class, args);
	}

}
