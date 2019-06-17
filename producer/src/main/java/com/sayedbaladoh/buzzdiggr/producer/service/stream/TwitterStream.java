package com.sayedbaladoh.buzzdiggr.producer.service.stream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sayedbaladoh.buzzdiggr.producer.model.Tweet;
import com.sayedbaladoh.buzzdiggr.producer.property.TwitterProperties;
import com.sayedbaladoh.buzzdiggr.producer.service.kafka.Sender;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

/**
 * Twitter Streaming Service
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class TwitterStream {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterStream.class);

	@Autowired
	TwitterProperties twitterProperties;

	@Autowired
	TweetHandler tweetHandler;

	@Autowired
	private Sender sender;

	@Value("${kafka.topic.json.tweet}")
	private String tweetTopicName;
	
	@Value("${social.twitter.count}")
	private int count;

	/**
	 * Stream tweets from twitter with default (100) total number of tweets to
	 * stream
	 * @param topics
	 *            - List of topics to search
	 * @throws InterruptedException
	 */
	public void stream(String... topics) throws InterruptedException {
		this.stream(this.count, topics);
	}

	/**
	 * Stream tweets from twitter
	 * 
	 * @param numberOfTweets
	 *            - Number of tweets
	 * @param topics
	 *            - List of topics to search
	 * @throws InterruptedException
	 */
	public void stream(int numberOfTweets, String... topics) throws InterruptedException {

		// Creating Twitter Stream
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

		// Add some track terms
		endpoint.trackTerms(Lists.newArrayList(topics));

		Authentication auth = new OAuth1(twitterProperties.getApiKey(), twitterProperties.getApiSecretKey(),
				twitterProperties.getAccessToken(), twitterProperties.getAccessTokenSecret());

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth)
				.processor(new StringDelimitedProcessor(queue)).build();

		// Establish a connection
		client.connect();

		// Parse and process tweets
		for (int msgRead = 0; msgRead < numberOfTweets; msgRead++) {
			String msg = queue.take();

//			LOGGER.info("Message: {} ", msg);

			// Parse and Clean Msg
			if (msg != null) {
				Tweet tweet = tweetHandler.parseTweet(msg);
//				LOGGER.info("Text: {} ", tweet.getText());
				LOGGER.info("Tweet: {} ", tweet);

				// Send tweet to Kafka
				sender.send(tweetTopicName, tweet);
			}

		}

		client.stop();

		LOGGER.info("'{}' messages processed!\n", client.getStatsTracker().getNumMessages());

	}

}
