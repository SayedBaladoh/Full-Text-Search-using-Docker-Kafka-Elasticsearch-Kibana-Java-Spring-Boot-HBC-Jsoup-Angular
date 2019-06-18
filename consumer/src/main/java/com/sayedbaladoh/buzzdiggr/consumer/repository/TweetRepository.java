package com.sayedbaladoh.buzzdiggr.consumer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sayedbaladoh.buzzdiggr.consumer.model.Tweet;

@Repository
public interface TweetRepository extends ElasticsearchRepository<Tweet, String> {

	 Page<Tweet> findByText(String text, Pageable pageable);

	List<Tweet> findByLanguage(String language);

	List<Tweet> findByUserName(String userName);
}
