package com.gateway.service;
/*
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ZuulPosFilter extends ZuulFilter {
	
	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1000;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
    	HttpServletResponse servletResponse = context.getResponse();
    	String setCookie = servletResponse.getHeader(HttpHeaders.SET_COOKIE);
    	
    	String auth = servletResponse.getHeader(HttpHeaders.AUTHORIZATION);
 	
		return null;
	}
}
*/
public class ZuulPosFilter{
	
}