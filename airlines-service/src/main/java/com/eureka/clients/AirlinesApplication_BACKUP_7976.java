package com.eureka.clients;

<<<<<<< HEAD
=======
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> 185c522da665f8b1b9ddc596fd88deab35eba7d9
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
=======
>>>>>>> 185c522da665f8b1b9ddc596fd88deab35eba7d9
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
<<<<<<< HEAD
import org.springframework.context.annotation.ComponentScan;
=======
>>>>>>> 185c522da665f8b1b9ddc596fd88deab35eba7d9
import org.springframework.context.annotation.Profile;

import com.netflix.appinfo.AmazonInfo;

@EnableEurekaClient //this enables service registration and discovery. In this case, this process registers itself with the discovery-server service using its application name 
@SpringBootApplication
public class AirlinesApplication {
	private static final Logger logger = LoggerFactory.getLogger(AirlinesApplication.class);
	@Profile("default")
	public static void main(String[] args) {
		SpringApplication.run(AirlinesApplication.class, args);
	}
	
	@Bean
	@Autowired
	@Profile("aws")
<<<<<<< HEAD
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {
=======
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {		
		logger.info("*** Init eurekaInstanceConfigBean ***");
>>>>>>> 185c522da665f8b1b9ddc596fd88deab35eba7d9
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
<<<<<<< HEAD
		config.setDataCenterInfo(info);
		return config;
}
=======
		return config;
	}
>>>>>>> 185c522da665f8b1b9ddc596fd88deab35eba7d9
}
