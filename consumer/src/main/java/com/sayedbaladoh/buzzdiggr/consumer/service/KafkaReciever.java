package com.sayedbaladoh.buzzdiggr.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sayedbaladoh.buzzdiggr.consumer.model.Article;
import com.sayedbaladoh.buzzdiggr.consumer.model.Tweet;

@Service
public class KafkaReciever {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReciever.class);

	@Autowired
	TweetService tweetService;

	@Autowired
	ArticleService articleService;

	@KafkaListener(topics = "${kafka.topic.string.name}", groupId = "${kafka.consumer.group.string.id}")
	public void consume(String message) {
		LOGGER.info("Recieved data='{}' from kafka", message);
	}

	@KafkaListener(topics = "${kafka.topic.json.tweets}", groupId = "${kafka.consumer.group.json.id}", containerFactory = "tweetKafkaListenerFactory")
	public void consumeJson(Tweet tweet) {
		tweetService.add(tweet);
		LOGGER.info("Recieved data='{}' from kafka", tweet);
	}

	@KafkaListener(topics = "${kafka.topic.json.articles}", groupId = "${kafka.consumer.group.json.id}", containerFactory = "articleKafkaListenerFactory")
	public void consumeJson(Article article) {
		articleService.add(article);
		LOGGER.info("Recieved data='{}' from kafka", article);
	}
}
