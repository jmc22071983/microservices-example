package com.eureka.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "airlines-service")
public interface IClientAirlinesService {
	
	@GetMapping("/allAirlines")
	public String allAirlines();
	
	@GetMapping("/airlines/{name}")
	public String airlines(@PathVariable("name") String name);
}

