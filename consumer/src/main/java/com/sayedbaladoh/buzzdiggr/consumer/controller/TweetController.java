package com.sayedbaladoh.buzzdiggr.consumer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sayedbaladoh.buzzdiggr.consumer.model.Tweet;
import com.sayedbaladoh.buzzdiggr.consumer.service.TweetService;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

	private final Logger LOG = LoggerFactory.getLogger(TweetController.class);

	@Autowired
	private TweetService tweetService;

	// @PostMapping()
	// public Tweet addNewTweet(@RequestBody Tweet tweet) {
	// LOG.info("Adding tweet : {}", tweet);
	// tweetService.add(tweet);
	// LOG.info("Added tweet : {}", tweet);
	// return tweet;
	// }

	@GetMapping("/all")
	public List<Tweet> getAllTweet() {
		return tweetService.getAll();
	}

	@GetMapping()
	public Page<Tweet> getAllTweet(Pageable page) {
		return tweetService.getAll(page);
	}

	@GetMapping(params = { "q" })
	public List<Tweet> search(@RequestParam(name = "q") String keyword) {
		return tweetService.getByText(keyword);
	}

	@GetMapping(params = { "userName" })
	public List<Tweet> searchByUserName(@RequestParam(name = "userName") String userName) {
		return tweetService.getByUserName(userName);
	}

	@GetMapping(params = { "language" })
	public List<Tweet> searchByLanguage(@RequestParam(name = "language") String language) {
		return tweetService.getByLanguage(language);
	}

}
