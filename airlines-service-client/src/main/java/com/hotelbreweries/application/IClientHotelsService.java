package com.hotelbreweries.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "hotels-service")
public interface IClientHotelsService {
	

	@GetMapping("/search-hotel-by-location")
	public String hotelLocation(@RequestParam String location);
}

