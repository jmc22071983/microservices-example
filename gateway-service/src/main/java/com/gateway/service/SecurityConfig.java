package com.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("SYSTEM")
		.and()
		.withUser("user").password("{noop}password").roles("USER", "ADMIN", "READER", "WRITER");
	}
	

	
   @Override
   protected void configure(HttpSecurity http) throws Exception {
	   http.authorizeRequests().antMatchers("/actuator/health").permitAll();
	   http.authorizeRequests().antMatchers("/airlines-api/").permitAll();
	   http.authorizeRequests().antMatchers("/airports-api/").permitAll();
	   http.authorizeRequests().antMatchers("/hotels-api/").permitAll();
	   http.authorizeRequests().antMatchers("/breweries-api/").permitAll();
	   http.authorizeRequests().antMatchers("/hotel-breweries-api/").permitAll();
	   http
       .csrf().disable()
       .authorizeRequests()
       .anyRequest().authenticated()
       .and().formLogin()
       .and().logout();

   }
}
