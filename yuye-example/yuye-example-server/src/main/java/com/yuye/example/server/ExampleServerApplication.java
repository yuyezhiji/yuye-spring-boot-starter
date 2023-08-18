package com.yuye.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.yuye"})
public class ExampleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServerApplication.class, args);
	}

}
