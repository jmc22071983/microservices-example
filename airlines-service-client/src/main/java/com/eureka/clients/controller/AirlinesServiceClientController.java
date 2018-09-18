package com.eureka.clients.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.eureka.clients.ClientAirlinesService;

//@Controller For thymeleaf view
@RestController
public class AirlinesServiceClientController {

	protected ClientAirlinesService airlineTest;

	public AirlinesServiceClientController(ClientAirlinesService airlineTest) {
		super();
		this.airlineTest = airlineTest;
	}

	@RequestMapping("/test")
	public String test(Model model) {
		String testA = "Hola Javi";
		model.addAttribute("test", testA);
		return "test";
	}

	@RequestMapping(value="/airlines/{name}", method = RequestMethod.GET)
	public String airlines(@PathVariable("name") String name) {
		return airlineTest.airlines(name);
	}

}
