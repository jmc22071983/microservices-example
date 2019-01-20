package com.gateway.service;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@EnableWebSecurity
@RestController
@Api(value="Airlines Service", tags = {"Airlines API Endpoints"})
@Produces({"application/json"})
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	
	
	@RequestMapping(value="/session", method = RequestMethod.GET)
	public static String allAirlines(HttpSession session) {
		LOGGER.info("SESSION ID...{}",session.getId()) ;
		return session.getId();
	}
	
	@RequestMapping(value="/session-for", method = RequestMethod.GET)
	public static String forward(HttpSession session) {
		LOGGER.info("SESSION ID...{}",session.getId()) ;
		return session.getId();
	}
	
	
}
