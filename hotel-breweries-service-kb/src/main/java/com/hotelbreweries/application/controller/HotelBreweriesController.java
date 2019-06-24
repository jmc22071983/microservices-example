package com.hotelbreweries.application.controller;

import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbreweries.application.IClientBreweriesService;
import com.hotelbreweries.application.IClientHotelsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Controller For thymeleaf view
@RestController
@Api(value="Find Breweries near the  hotel", tags = {"Breweries API Endpoints"})
@Produces({"application/json"})
public class HotelBreweriesController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelBreweriesController.class);
	
	@Autowired
	IClientHotelsService iClientHotelsService;
	
	@Autowired
	IClientBreweriesService iClientBreweriesService;
	
	

	@ApiOperation(value = "Get hotel location - Return city", produces="application/json")
	@GetMapping("/hotel-breweries/hotels/location/{location}")
	public String hotels(@PathVariable ("location") String location) {
		return iClientHotelsService.retrieveHotelsLocation(location);
	}
	
	@ApiOperation(value = "Get Breweries by an hotel location", produces="application/json")
	@GetMapping("/hotel-breweries/breweries/location/{location}")
	public String breweriesByLocation(@PathVariable ("location") String location) {
		return iClientBreweriesService.findBreweries(location);
	}
	
	@ApiOperation(value = "Breweries near the city", produces="application/json")
	@GetMapping("/hotel-breweries/breweries")
	public String breweries(@RequestParam String hotelName) {	
		String city = iClientHotelsService.hotelLocation(hotelName);
		return iClientBreweriesService.findBreweries(city);
	}
	
}
