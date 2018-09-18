package com.eureka.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientAirlinesService {
	@Autowired
	protected RestTemplate restTemplate;
	protected String serviceUrl;

	public ClientAirlinesService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	// invoke to airlines-service and return a Airlines object
	public String airlines(String name) {
		return restTemplate.getForObject(serviceUrl + "/airlines/{name}", String.class, name);
	}
	
	// invoke to test and return a String object
	public String allAirlines() {
		return restTemplate.getForObject(serviceUrl + "/allAirlines", String.class);
	}
		
	// invoke to test and return a String object
	public String test() {
		return restTemplate.getForObject(serviceUrl + "/test", String.class);
	}
}
