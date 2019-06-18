package com.sayedbaladoh.buzzdiggr.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sayedbaladoh.buzzdiggr.producer.service.crawler.Spider;
import com.sayedbaladoh.buzzdiggr.producer.service.stream.TwitterStream;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Initialize and instantiate Streaming and Crawling API
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@CrossOrigin(origins = "${frontend.url}")
@RequestMapping("/api/stream")
@Api(value = "Stream", description = "Initialize and instantiate Streaming and Crowling API")
public class StreamController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StreamController.class);

	@Autowired
	private TwitterStream twitterStreamingService;

	@Autowired
	private Spider spiderService;

	/**
	 * stream
	 * 
	 * Start retrieve real-time Tweets
	 * 
	 * @param count
	 *            - The total number of tweets to stream. Default (100).
	 * @param q
	 *            - List of search keywords
	 * 
	 * @return
	 */
	@ApiOperation(value = "Start retrieve real-time Tweets")
	@GetMapping(value = "/twitter", params = { "q", "count" })
	public ResponseEntity<?> stream(@RequestParam(name = "count") int numberOfTweets,
			@RequestParam(name = "q") String[] keywords) {
		try {
			twitterStreamingService.stream(numberOfTweets, keywords);
		} catch (InterruptedException e) {
			// e.printStackTrace();
			LOGGER.error(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * stream
	 * 
	 * Start retrieve real-time Tweets with default total number of tweets (100)
	 * 
	 * @param q
	 *            - List of search keywords
	 * 
	 * @return
	 */
	@ApiOperation(value = "Start retrieve real-time Tweets with default total number of tweets  (100)")
	@GetMapping(value = "/twitter", params = { "q" })
	public ResponseEntity<?> stream(@RequestParam(name = "q") String[] keywords) {
		try {
			twitterStreamingService.stream(keywords);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * crawl
	 * 
	 * launch web crawler
	 * 
	 * @param url
	 *            - The starting point of the crawler
	 * @param q
	 *            - The keyword or string that you are searching for
	 * @param count
	 *            - The limit for retrieving pages
	 * @param maxPagesToSearch
	 *            - The maximum pages to search
	 */
	@ApiOperation(value = "launch web crawler")
	@GetMapping(value = "/crawler", params = { "url", "q", "count", "maxPagesToSearch" })
	public ResponseEntity<?> crawl(@RequestParam(name = "url") String url, @RequestParam(name = "q") String keyword,
			@RequestParam(name = "count") int pagesLimit,
			@RequestParam(name = "maxPagesToSearch") int maxPagesToSearch) {
		spiderService.crawl(url, keyword, pagesLimit, maxPagesToSearch);
		return ResponseEntity.ok().build();
	}

	/**
	 * crawl
	 * 
	 * launch web crawler
	 * 
	 * @param url
	 *            - The starting point of the crawler
	 * @param q
	 *            - The keyword or string that you are searching for
	 * @param count
	 *            - The limit for retrieving pages
	 */
	@ApiOperation(value = "launch web crawler")
	@GetMapping(value = "/crawler", params = { "url", "q", "count" })
	public ResponseEntity<?> crawl(@RequestParam(name = "url") String url, @RequestParam(name = "q") String keyword,
			@RequestParam(name = "count") int pagesLimit) {
		spiderService.crawl(url, keyword, pagesLimit);
		return ResponseEntity.ok().build();
	}

	/**
	 * crawl
	 * 
	 * launch web crawler
	 * 
	 * @param url
	 *            - The starting point of the crawler
	 * @param q
	 *            - The keyword or string that you are searching for
	 * @param maxPagesToSearch
	 *            - The maximum pages to search
	 */
	@ApiOperation(value = "launch web crawler")
	@GetMapping(value = "/crawler", params = { "url", "q", "maxPagesToSearch" })
	public ResponseEntity<?> crawlByMax(@RequestParam(name = "url") String url,
			@RequestParam(name = "q") String keyword, @RequestParam(name = "maxPagesToSearch") int maxPagesToSearch) {
		spiderService.crawlByMax(url, keyword, maxPagesToSearch);
		return ResponseEntity.ok().build();
	}

	/**
	 * crawl
	 * 
	 * launch web crawler
	 * 
	 * @param url
	 *            - The starting point of the crawler
	 * @param q
	 *            - The keyword or string that you are searching for
	 */
	@ApiOperation(value = "launch web crawler")
	@GetMapping(value = "/crawler", params = { "url", "q" })
	public ResponseEntity<?> crawl(@RequestParam(name = "url") String url, @RequestParam(name = "q") String keyword) {
		spiderService.crawl(url, keyword);
		return ResponseEntity.ok().build();
	}
}
