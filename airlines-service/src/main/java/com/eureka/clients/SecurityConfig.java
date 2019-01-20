package com.eureka.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String[] SWAGGER_AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };
	
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication();
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
	http
       .csrf().disable()
       .authorizeRequests()
       .antMatchers(HttpMethod.GET,"/airlines/**","/session-for-air").permitAll()
       .antMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
       .antMatchers("*").hasRole("SYSTEM")
       .anyRequest().authenticated();
   }
   
   
}