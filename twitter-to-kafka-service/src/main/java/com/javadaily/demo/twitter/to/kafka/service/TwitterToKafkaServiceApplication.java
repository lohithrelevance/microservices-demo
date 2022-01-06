package com.javadaily.demo.twitter.to.kafka.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.javadaily.demo.config.TwitterToKafkaServiceConfiguration;
import com.javadaily.demo.twitter.to.kafka.service.runner.StreamRunner;

@SpringBootApplication
@ComponentScan(basePackages = "com.javadaily.demo")
public class TwitterToKafkaServiceApplication implements CommandLineRunner{
	
	private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
	
	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfigData;
	
	private final StreamRunner streamRunner;
	
	public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfigData,
			StreamRunner streamRunner) {
		this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
		this.streamRunner = streamRunner;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		LOG.info("App starts...");
		LOG.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[] {})));
		LOG.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
		streamRunner.start();
	}

	
}
