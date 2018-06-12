package com.eureka.clients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.eureka.clients.controller"})
public class AirlinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlinesApplication.class, args);
	}
}
