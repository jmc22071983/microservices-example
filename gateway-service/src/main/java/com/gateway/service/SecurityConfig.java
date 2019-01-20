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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
       .withUser("eureka").password("{noop}eurekapass").roles("SYSTEM","USER","ADMIN");
   }
 
   @Override
   protected void configure(HttpSecurity http) throws Exception {
	   http
       .csrf().disable()
       .authorizeRequests()
       .antMatchers("*").hasRole("SYSTEM")
     //  .antMatchers("/session/**").permitAll()
       .antMatchers("/airlines-api/**").permitAll()
       .antMatchers("/eureka/**").hasRole("ADMIN")
       .anyRequest().authenticated()
       .and().formLogin()
       .and().logout();
   }
}
