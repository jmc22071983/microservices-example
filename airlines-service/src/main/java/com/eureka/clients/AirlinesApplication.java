package com.eureka.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient //this enables service registration and discovery. In this case, this process registers itself with the discovery-server service using its application name 
@SpringBootApplication
public class AirlinesApplication {
	public static void main(String[] args) {
		SpringApplication.run(AirlinesApplication.class, args);
	}
}
