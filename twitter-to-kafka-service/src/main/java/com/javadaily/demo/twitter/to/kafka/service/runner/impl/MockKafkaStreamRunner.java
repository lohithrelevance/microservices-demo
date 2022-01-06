package com.javadaily.demo.twitter.to.kafka.service.runner.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.javadaily.demo.config.TwitterToKafkaServiceConfiguration;
import com.javadaily.demo.twitter.to.kafka.service.exception.TwitterToKafkaServiceException;
import com.javadaily.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.javadaily.demo.twitter.to.kafka.service.runner.StreamRunner;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "true" )
public class MockKafkaStreamRunner implements StreamRunner {

	private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration;

	private final TwitterKafkaStatusListener kafkaStatusListener;

	private static final Random RANDOM = new Random();

	private static final String[] WORDS = new String[] { "Lorem", "ipsum", "dolor", "sit", "amet", "consectetuer",
			"adipiscing", "elit", "Maecenas", "porttitor", "congue", "massa", "Fusce", "posuere", "magna", "sed",
			"pulvinar", "ultricies", "purus", "lectus", "malesuada", "libero" };

	private static final String tweetAsRawJson = "{" + "\"created_at\":\"{0}\"," + "\"id\":\"{1}\","
			+ "\"text\":\"{2}\"," + "\"user\":{\"id\":\"{3}\"}" + "}";

	private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

	public MockKafkaStreamRunner(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration,
			TwitterKafkaStatusListener kafkaStatusListener) {
		this.twitterToKafkaServiceConfiguration = twitterToKafkaServiceConfiguration;
		this.kafkaStatusListener = kafkaStatusListener;
	}

	@Override
	public void start() throws TwitterException {

		String[] keywords = twitterToKafkaServiceConfiguration.getTwitterKeywords().toArray(new String[0]);
		int minTweetLength = twitterToKafkaServiceConfiguration.getMockMinTweetLength();
		int maxTweetLenght = twitterToKafkaServiceConfiguration.getMockMaxTweetLength();
		long sleepTimeMs = twitterToKafkaServiceConfiguration.getMockSleepMs();
		LOG.info("Starting mock filtering twitter streams for keywords", Arrays.toString(keywords));

		simulateTwitterStream(keywords, minTweetLength, maxTweetLenght, sleepTimeMs);

	}

	private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLenght, long sleepTimeMs) {

		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				while (true) {
					String formattedTweetAsRawJson = getformattedTweet(keywords, minTweetLength, maxTweetLenght);
					Status status = TwitterObjectFactory.createStatus(formattedTweetAsRawJson);
					kafkaStatusListener.onStatus(status);
					sleep(sleepTimeMs);
				}
			} catch (TwitterException e) {
				LOG.error("Error creating Twitter status {}  ",e);
			}
		});
	}

	private void sleep(long sleepTimeMs) {

		try {
			Thread.sleep(sleepTimeMs);
		} catch (InterruptedException e) {
			throw new TwitterToKafkaServiceException("Error while sleeping for waiting new status to create!!");
		}
	}

	private String getformattedTweet(String[] keywords, int minTweetLength, int maxTweetLenght) {

		String[] params = new String[] {
				ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH) ),
				String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
				getRandomTweetContent(keywords, minTweetLength, maxTweetLenght),
				String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)) };

		return formatTweetAsJSONWithParams(params);
	}

	private String formatTweetAsJSONWithParams(String[] params) {
		String tweet = tweetAsRawJson;

		for (int i = 0; i < params.length; i++) {
			 tweet = tweet.replace("{" + i + "}", params[i]);
		}

		return tweet;
	}

	private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLenght) {

		StringBuilder tweet = new StringBuilder();
		int tweetlength = RANDOM.nextInt(maxTweetLenght - minTweetLength + 1) + minTweetLength;

		return constructRandomTweet(keywords, tweet, tweetlength);
	}

	private String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetlength) {
		for (int i = 0; i < tweetlength; i++) {
			tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
			if (i == tweetlength / 2) {
				tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
			}
		}

		return tweet.toString().trim();
	}

}
