package com.sayedbaladoh.buzzdiggr.producer.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sayedbaladoh.buzzdiggr.producer.model.Article;
import com.sayedbaladoh.buzzdiggr.producer.model.Tweet;

/**
 * Kakfa Sender Service
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Tweet> tweetKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Article> articleKafkaTemplate;

	// Send String data
	public void send(String topic, String data) {

		LOGGER.info("sending data='{}' to topic='{}'", data, topic);
		kafkaTemplate.send(topic, data);
	}

	// Send Tweet object
	public void send(String topic, Tweet tweet) {

		LOGGER.info("sending data='{}' to kafka topic='{}'", tweet, topic);
		tweetKafkaTemplate.send(topic, tweet);

	}

	// Send Article object
	public void send(String topic, Article article) {

		LOGGER.info("sending data='{}' to kafka topic='{}'", article, topic);
		articleKafkaTemplate.send(topic, article);

	}

}
