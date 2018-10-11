package com.eureka.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.eureka.clients.controller.AirlinesServiceClientController;
import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner ...
public class AirlinesServiceClientApplication {
	public static final String SERVICE_URL = "http://AIRLINES-SERVICE";
	private static final Logger logger = LoggerFactory.getLogger(AirlinesServiceClientApplication.class);
	@Profile("default")
	public static void main(String[] args) {
		SpringApplication.run(AirlinesServiceClientApplication.class, args);
	}
	
	@Bean
	@Autowired
	@Profile("aws")
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {		
		logger.info("*** Init eurekaInstanceConfigBean ***");
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		return config;
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
