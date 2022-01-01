package com.javadaily.demo.twitter.to.kafka.service.exception;

public class TwitterToKafkaServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5759790213252105116L;

	
	public TwitterToKafkaServiceException() {
		super();
	}
	
	public TwitterToKafkaServiceException(String message) {
		super(message);
	}
	
	public TwitterToKafkaServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
