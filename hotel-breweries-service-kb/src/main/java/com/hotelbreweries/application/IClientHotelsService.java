package com.hotelbreweries.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "hotels-service")
public interface IClientHotelsService {
	

	@GetMapping("/hotels/search-hotels-by-location")
	public String retrieveHotelsLocation(@RequestParam String location);
	
	@GetMapping("/hotels/retrieve-location-by-hotel-name")
	public String hotelLocation(@RequestParam String hotelName);
}

