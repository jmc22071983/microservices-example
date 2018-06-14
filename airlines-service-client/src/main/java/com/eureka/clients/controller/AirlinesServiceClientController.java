package com.eureka.clients.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.clients.Airlines;
import com.eureka.clients.ClientAirlinesService;

@RestController
public class AirlinesServiceClientController {

	protected ClientAirlinesService helloWorldService;

	public AirlinesServiceClientController(ClientAirlinesService helloWorldService) {
		super();
		this.helloWorldService = helloWorldService;
	}

	@RequestMapping("/test")
	public String test() {
		String testA = helloWorldService.test();
		return testA;
	}

	@RequestMapping("/airlines/{name}")
	public String airlines(Model model, @PathVariable("name") String name) {
		Airlines airlines = helloWorldService.airlines(name);
		model.addAttribute("airlines", airlines.getContent());
		return "airlines";
	}

}
