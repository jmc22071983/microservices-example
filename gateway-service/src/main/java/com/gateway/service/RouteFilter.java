package com.gateway.service;
/*
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class RouteFilter extends ZuulFilter{
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
        HttpSession httpSession = context.getRequest().getSession();
       // Session session = repository.getSession(httpSession.getId());
        log.info("SESSION ID ROUTEFILTER: {}", httpSession.getId());
        context.addZuulRequestHeader( "Cookie", "SESSION=" + httpSession.getId());
        return null;

		
	}
}
*/
public class RouteFilter{
	
}