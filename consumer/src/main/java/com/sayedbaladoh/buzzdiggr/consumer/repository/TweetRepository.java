package com.sayedbaladoh.buzzdiggr.consumer.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sayedbaladoh.buzzdiggr.consumer.model.Tweet;

@Repository
public interface TweetRepository extends ElasticsearchRepository<Tweet, String> {

	List<Tweet> findByText(String text);

	List<Tweet> findByLanguage(String language);

	List<Tweet> findByUserName(String userName);
}
