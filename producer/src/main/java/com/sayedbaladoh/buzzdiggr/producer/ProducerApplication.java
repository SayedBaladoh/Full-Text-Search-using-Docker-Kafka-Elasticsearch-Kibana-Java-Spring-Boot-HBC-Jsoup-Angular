package com.sayedbaladoh.buzzdiggr.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sayedbaladoh.buzzdiggr.producer.property.TwitterProperties;
import com.sayedbaladoh.buzzdiggr.producer.service.crawler.Spider;
import com.sayedbaladoh.buzzdiggr.producer.service.stream.TwitterStream;

@EnableConfigurationProperties(TwitterProperties.class)
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

	@Autowired
	private TwitterStream twitterStreamingService;

	@Autowired
	private Spider spiderService;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	// Test stream Twitter tweets and web Crawler
	@Override
	public void run(String... strings) throws Exception {
//		logger.info("Running Twitter Streaming ...");
//		twitterStreamingService.stream(10, "twitterapi", "محمد صلاح", "#Sports");
//		logger.info("Running Crawller Streaming ...");
//		spiderService.crawl("https://www.shorouknews.com/", "محمد", 15, 50);
	}
}
