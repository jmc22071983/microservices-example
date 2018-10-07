package com.eureka.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import com.netflix.appinfo.AmazonInfo;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

	@Value("${server.port:8761}")
	private int port;

	@Profile("default")
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

	@Bean
	@Autowired
	@Profile("aws")
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {
		/*
		 * logger.info("*** Init eurekaInstanceConfigBean ***");
		 * EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		 * AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		 * config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		 * config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		 * logger.info("*** LOCAL HOSTNAME:" +
		 * info.get(AmazonInfo.MetaDataKey.localHostname)); logger.info("*** LOCAL IP:"
		 * + info.get(AmazonInfo.MetaDataKey.localIpv4));
		 * logger.info("*** PUBLIC HOSTNAME:" +
		 * info.get(AmazonInfo.MetaDataKey.publicHostname));
		 * logger.info("*** PUBLIC IP:" + info.get(AmazonInfo.MetaDataKey.publicIpv4));
		 * logger.info("*** availabilityZone:" +
		 * info.get(AmazonInfo.MetaDataKey.availabilityZone));
		 * config.setNonSecurePort(port); config.setDataCenterInfo(info); return config;
		 */
		logger.info("*** Init eurekaInstanceConfigBean ***");
		final EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils) {
			@Scheduled(initialDelay = 30000L, fixedRate = 30000L)
			public void refreshInfo() {
				AmazonInfo newInfo = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
				if (!this.getDataCenterInfo().equals(newInfo)) {
					logger.info("*** ! this.getDataCenterInfo().equals(newInfo) ***");
					((AmazonInfo) this.getDataCenterInfo()).setMetadata(newInfo.getMetadata());
					logger.info("*** Setting newInfo properties ***");
					this.setHostname(newInfo.get(AmazonInfo.MetaDataKey.publicHostname));
					this.setIpAddress(newInfo.get(AmazonInfo.MetaDataKey.publicIpv4));
					this.setDataCenterInfo(newInfo);
					this.setNonSecurePort(port);
					logger.info("*** LOCAL HOSTNAME:" + newInfo.get(AmazonInfo.MetaDataKey.localHostname));
					logger.info("*** PUBLIC IP:" + newInfo.get(AmazonInfo.MetaDataKey.publicIpv4));
					logger.info("*** PUBLIC IP:" + newInfo.get(AmazonInfo.MetaDataKey.publicIpv4));
				}
			}
		};
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		logger.info("*** Setting Info properties ***");
		instance.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		instance.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		instance.setDataCenterInfo(info);
		instance.setNonSecurePort(port);
		logger.info("*** LOCAL HOSTNAME:" + info.get(AmazonInfo.MetaDataKey.localHostname));
		logger.info("*** PUBLIC IP:" + info.get(AmazonInfo.MetaDataKey.publicIpv4));
		logger.info("*** PUBLIC IP:" + info.get(AmazonInfo.MetaDataKey.publicIpv4));

		return instance;
	}
	
/*	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
		  EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
		  AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		  b.setDataCenterInfo(info);
		  return b;
	}*/
}
