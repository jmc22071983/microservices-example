package com.gateway.service;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class ZuulPreFilter extends ZuulFilter {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
 
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpSession httpSession = context.getRequest().getSession();
       // Session session = repository.getSession(httpSession.getId());
        log.info("SESSION ID: {}", httpSession.getId());
        context.addZuulRequestHeader( "Cookie", "SESSION=" + httpSession.getId());
        return null;
    }
 
    @Override
    public String filterType() {
        return "pre";
    }
 
    @Override
    public int filterOrder() {
        return 0;
    }
}
