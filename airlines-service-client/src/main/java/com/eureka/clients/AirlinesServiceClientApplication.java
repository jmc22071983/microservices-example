package com.eureka.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.eureka.clients.controller.AirlinesServiceClientController;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner ...
public class AirlinesServiceClientApplication {
	public static final String SERVICE_URL = "http://AIRLINES-SERVICE";

	public static void main(String[] args) {
		SpringApplication.run(AirlinesServiceClientApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// The service encapsulates the interaction with the micro-service.
	@Bean
	public ClientAirlinesService helloWorldService() {
		return new ClientAirlinesService(SERVICE_URL);
	}

	// Create the controller, passing it the ClientGreetingService to use.
	@Bean
	public AirlinesServiceClientController helloWorldController() {
		return new AirlinesServiceClientController(helloWorldService());
	}

}
