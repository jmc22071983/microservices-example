package com.hotelbreweries.application.controller;

import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbreweries.application.IClientBreweriesService;
import com.hotelbreweries.application.IClientHotelsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Controller For thymeleaf view
@RestController
@Api(value="Airlines client service", tags = {"Airlines Info"})
@Produces({"application/json", "application/xml"})
public class HotelBreweriesController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelBreweriesController.class);
	
	@Autowired
	IClientHotelsService iClientHotelsService;
	
	@Autowired
	IClientBreweriesService iClientBreweriesService;
	
	

	@ApiOperation(value = "Get hotel location", produces="application/json")
	@GetMapping("/hotel-location/{location}")
	public String hotels(@PathVariable ("location") String location) {
		return iClientHotelsService.hotelLocation(location);
	}
	
	@ApiOperation(value = "Get Breweries by an hotel location", produces="application/json")
	@GetMapping("/breweries-location/{location}")
	public String breweries(@PathVariable ("location") String location) {
		return iClientBreweriesService.findBreweries(location);
	}
	
}
