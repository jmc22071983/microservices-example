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
	   http
       .csrf().disable()
       .authorizeRequests()
       .antMatchers("*").hasRole("SYSTEM")
       .antMatchers("/airlines-api/**").permitAll()
       .antMatchers("/airports-api/**").permitAll()
       .antMatchers("/breweries-api/**").permitAll()
       .antMatchers("/hotels-api/**").permitAll()
       .antMatchers("/hotel-breweries-api/**").permitAll()
       .antMatchers("/session/**").permitAll()
       .antMatchers("/session-for/**").permitAll()
       .anyRequest().authenticated()
       .and().formLogin()
       .and().logout();
     /*  .and()
       .csrf()
       .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());;*/
   }
}
