package com.eureka.clients.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eureka.clients.Airlines;
import com.eureka.clients.ClientAirlinesService;

public class AirlinesServiceClientController {

	protected ClientAirlinesService helloWorldService;

	public AirlinesServiceClientController(ClientAirlinesService helloWorldService) {
		super();
		this.helloWorldService = helloWorldService;
	}

	@RequestMapping("/test")
	public String goHome() {
		return "hola";
	}

	@RequestMapping("/greeting/{name}")
	public String greeting(Model model, @PathVariable("name") String name) {
		Airlines airlines = helloWorldService.airline(name);
		model.addAttribute("greeting", airlines.getContent());
		return "greeting";
	}

}
