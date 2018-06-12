package com.eureka.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ClientAirlinesService {
	@Autowired
	protected RestTemplate restTemplate;
	protected String serviceUrl;

	public ClientAirlinesService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	// invoke to airlines-service and return a Airlines object
	public Airlines airline(String name) {
		return restTemplate.getForObject(serviceUrl + "/greeting/{name}", Airlines.class, name);
	}
}
