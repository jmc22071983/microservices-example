package com.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication().withUser("eureka")
         .password("{noop}eurekapass").roles("SYSTEM");
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http
       .authorizeRequests()
           .antMatchers("/airlines-api/**").permitAll()
           .antMatchers("/airports-api/**").permitAll()
           .antMatchers("/hotels-api/**").permitAll()
           .antMatchers("/breweries-api/**").permitAll()
           .antMatchers("/hotel-breweries-api/**").permitAll() 
           .antMatchers("/eureka/**").hasRole("ADMIN")
           .anyRequest().authenticated()
           .and()
       .logout()
           .and()
       .csrf().disable();
   }
}