package com.javadaily.demo.twitter.to.kafka.service.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class TwitterToKafkaServiceConfiguration {

	public List<String> getTwitterKeywords() {
		return twitterKeywords;
	}

	public void setTwitterKeywords(List<String> twitterKeywords) {
		this.twitterKeywords = twitterKeywords;
	}

	private List<String> twitterKeywords;
	
	private String welcomeMessage;

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
}
