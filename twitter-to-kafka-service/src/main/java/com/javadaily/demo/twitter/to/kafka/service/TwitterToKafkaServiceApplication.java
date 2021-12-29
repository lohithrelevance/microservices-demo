package com.javadaily.demo.twitter.to.kafka.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javadaily.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfiguration;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner{
	
	private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
	
	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfigData;
	
	public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfigData) {
		this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		LOG.info("App starts...");
		LOG.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[] {})));
		LOG.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
	}

	
}
