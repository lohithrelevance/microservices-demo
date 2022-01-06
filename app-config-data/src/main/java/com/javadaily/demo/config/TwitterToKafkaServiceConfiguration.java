package com.javadaily.demo.config;

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
	
	private Boolean enableMockTweets;
	
	private Long mockSleepMs;
	
	private Integer mockMinTweetLength;
	private Integer mockMaxTweetLength;
	
	public Boolean getEnableMockTweets() {
		return enableMockTweets;
	}

	public void setEnableMockTweets(Boolean enableMockTweets) {
		this.enableMockTweets = enableMockTweets;
	}

	public Long getMockSleepMs() {
		return mockSleepMs;
	}

	public void setMockSleepMs(Long mockSleepMs) {
		this.mockSleepMs = mockSleepMs;
	}

	public Integer getMockMinTweetLength() {
		return mockMinTweetLength;
	}

	public void setMockMinTweetLength(Integer mockMinTweetLength) {
		this.mockMinTweetLength = mockMinTweetLength;
	}

	public Integer getMockMaxTweetLength() {
		return mockMaxTweetLength;
	}

	public void setMockMaxTweetLength(Integer mockMaxTweetLength) {
		this.mockMaxTweetLength = mockMaxTweetLength;
	}

	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
}
