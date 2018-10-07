package com.eureka.config.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigServiceController {

	@RequestMapping("/")
	public String test(Model model) {
		String testA = "Config Service Runnig";
		model.addAttribute("test", testA);
		return "test";
	}
}
