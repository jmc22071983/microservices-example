package com.gateway.service;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityInitializer() {
	        super(SecurityConfig.class, SessionConfig.class);
	}
}
