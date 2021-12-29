package com.javadaily.demo.twitter.to.kafka.service.runner.impl;

import java.util.Arrays;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.javadaily.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfiguration;
import com.javadaily.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.javadaily.demo.twitter.to.kafka.service.runner.StreamRunner;

import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
public class TwitterKafkaStreamRunner implements StreamRunner {

	private final static Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class); 
	
	private final TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration;

	private final TwitterKafkaStatusListener kafkaStatusListener;
	
	private TwitterStream twitterStream;

	public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfiguration twitterToKafkaServiceConfiguration,
			TwitterKafkaStatusListener kafkaStatusListener) {
		this.twitterToKafkaServiceConfiguration = twitterToKafkaServiceConfiguration;
		this.kafkaStatusListener = kafkaStatusListener;
	}

	@Override
	public void start() throws TwitterException {

		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(kafkaStatusListener);
		addFilter();
	}
	
	@PreDestroy
	public void shutdown() {
		if(twitterStream != null) {
			LOG.info("Closing twitter stream !!!");
			twitterStream.shutdown();
		}
	}

	private void addFilter() {
		String[] keywords = twitterToKafkaServiceConfiguration.getTwitterKeywords().toArray(new String[0]);
		FilterQuery filterQuery = new FilterQuery(keywords);
		twitterStream.filter(filterQuery);
		LOG.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
	}

}
