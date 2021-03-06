package com.hotels.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.netflix.appinfo.AmazonInfo;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableEurekaClient //this enables service registration and discovery. In this case, this process registers itself with the discovery-server service using its application name 
@SpringBootApplication
@EnableSwagger2
public class HotelsApplication {
	private static final Logger logger = LoggerFactory.getLogger(HotelsApplication.class);
	@Profile({ "default", "swarm"})
	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class, args);
	}
	
	@Bean
	@Autowired
	@Profile("aws")
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {		
		logger.info("*** Init eurekaInstanceConfigBean ***");
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		return config;
	}
}
