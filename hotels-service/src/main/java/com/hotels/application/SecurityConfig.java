package com.hotels.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

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
           .antMatchers("/hotels/**").permitAll()
           .antMatchers("/search-hotels-by-location/**").permitAll()
           .antMatchers("/retrive-location-by-hotel-name/**").permitAll()
           .antMatchers("/eureka/**").hasRole("ADMIN")
           .anyRequest().authenticated()
           .and()
       .logout()
           .and()
       .csrf().disable();
   }
}