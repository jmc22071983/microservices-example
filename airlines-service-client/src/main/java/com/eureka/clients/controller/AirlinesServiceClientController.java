package com.eureka.clients.controller;

import java.util.List;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.eureka.clients.IClientAirlinesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Controller For thymeleaf view
@RestController
@Api(value="Airlines client service", tags = {"Airlines Info"})
@Produces({"application/json", "application/xml"})
public class AirlinesServiceClientController {
	/*
	@Autowired
	private DiscoveryClient discoveryClient;
	
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
	
	@RequestMapping(value="/allAirlines", method = RequestMethod.GET)
	public String allAirlines() {
		return airlineTest.allAirlines();
	}
	
	
	
	
	@RequestMapping(value="/service-instances/{applicationName}", method = RequestMethod.GET)
	public List<ServiceInstance> serviceInstancesByApplicationName( @PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
	
	@RequestMapping(value="/showAllServiceIds",  method = RequestMethod.GET)
	public String showAllServiceIds() {
		List<String> serviceIds = this.discoveryClient.getServices();
        if (serviceIds == null || serviceIds.isEmpty()) {
            return "No services found!";
        }
        String html = "<h3>Service Ids:</h3>";
        for (String serviceId : serviceIds) {
            html += "<br><a href='showService?serviceId=" + serviceId + "'>" + serviceId + "</a>";
        }
        return html;
	}
	
    @RequestMapping(value = "/showService", method = RequestMethod.GET)
    public String showFirstService(@RequestParam(defaultValue = "") String serviceId) {
 
        // (Need!!) eureka.client.fetchRegistry=true
        List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceId);
 
        if (instances == null || instances.isEmpty()) {
            return "No instances for service: " + serviceId;
        }
        String html = "<h2>Instances for Service Id: " + serviceId + "</h2>";
 
        for (ServiceInstance serviceInstance : instances) {
            html += "<h3>Instance: " + serviceInstance.getUri() + "</h3>";
            html += "Host: " + serviceInstance.getHost() + "<br>";
            html += "Port: " + serviceInstance.getPort() + "<br>";
        }
 
        return html;
    }
*/
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesServiceClientController.class);
	
	@Autowired
	IClientAirlinesService clientAirlinesService;
	
	
	@ApiOperation(value = "Get all airlines details", produces="application/json")
	@GetMapping("/print-all-airlines")
	public String allAirlines() {
		LOGGER.info("printing all airlines");
		return clientAirlinesService.allAirlines();
	}
	
	@GetMapping("/airline-datail/{name}")
	public String airline(@PathVariable ("name") String name) {
		LOGGER.info("getting  airline detail");
		return clientAirlinesService.airlines(name);
	}
	
}
