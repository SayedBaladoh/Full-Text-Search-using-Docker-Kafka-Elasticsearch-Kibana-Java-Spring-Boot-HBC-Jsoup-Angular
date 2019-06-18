package com.sayedbaladoh.buzzdiggr.consumer.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sayedbaladoh.buzzdiggr.consumer.model.Tweet;
import com.sayedbaladoh.buzzdiggr.consumer.repository.TweetRepository;

@Service
public class TweetService {

	@Autowired
	TweetRepository tweetRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

	@Value("${elasticsearch.index.stream}")
	private String indexName;

	@Value("${elasticsearch.type.tweet}")
	private String tweetType;

	public void add(Tweet tweet) {
		if (tweet.getId() == null || tweet.getId().equals(""))
			tweet.setId(UUID.randomUUID().toString());
		tweetRepository.save(tweet);
	}

	public void add(List<Tweet> tweets) {
		tweetRepository.saveAll(tweets);
	}

	// public void addBulk(List<Tweet> tweets) {
	// try {
	// if (!template.indexExists(indexName)) {
	// template.createIndex(indexName);
	// }
	// ObjectMapper mapper = new ObjectMapper();
	// List<IndexQuery> queries = new ArrayList<>();
	// for (Tweet tweet : tweets) {
	// IndexQuery indexQuery = new IndexQuery();
	// indexQuery.setSource(mapper.writeValueAsString(tweet));
	// indexQuery.setIndexName(indexName);
	// indexQuery.setType(tweetType);
	// queries.add(indexQuery);
	// }
	// if (queries.size() > 0) {
	// template.bulkIndex(queries);
	// }
	//
	// template.refresh(indexName);
	//
	// LOGGER.info("BulkIndex completed: {}");
	// } catch (Exception e) {
	// LOGGER.error("Error bulk index", e);
	// }
	// }

	public void removeAll() {
		tweetRepository.deleteAll();
	}
	
	public List<Tweet> getAll() {
		List<Tweet> tweetsList = new ArrayList<>();
		Iterable<Tweet> tweetses = tweetRepository.findAll();
		tweetses.forEach(tweetsList::add);
		return tweetsList;
	}

	public Page<Tweet> getAll(Pageable page) {
		return tweetRepository.findAll(page);
	}

	// search term all fields
	public List<Tweet> get(String query) {
		List<Tweet> tweetsList = new ArrayList<>();
		Iterable<Tweet> tweetses = tweetRepository.search(queryStringQuery(query));
		tweetses.forEach(tweetsList::add);
		return tweetsList;
	}

	public  Page<Tweet> getByText(String text, Pageable pageable) {
		return tweetRepository.findByText(text, pageable);
	}

	public List<Tweet> getByLanguage(String language) {
		return tweetRepository.findByLanguage(language);
	}

	public List<Tweet> getByUserName(String userName) {
		return tweetRepository.findByUserName(userName);
	}
	
//	 @Autowired
//	    private ElasticsearchTemplate elasticsearchTemplate;
//
//	    public List<Tweet> findAllByText(String text) {
//	        MatchQueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("name", name);
//
//	        //You can query as many indices as you want
//	        IndicesQueryBuilder builder = QueryBuilders.indicesQuery(queryBuilder, "stream", "crawler");
//
//	        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
//
//	        return elasticsearchTemplate.query(searchQuery, response -> {
//	            SearchHits hits = response.getHits();
//	            List<Tweet> result = new ArrayList<>();
//	            Arrays.stream(hits.getHits()).forEach(h -> {
//	                Map<String, Object> source = h.getSource();
//	                //get only id just for test
//	                Tweet tweet = new Tweet()
//	                        .setId(String.valueOf(source.getOrDefault("id", null)));
//	                result.add(tweet);
//	            });
//	            return result;
//	        });
//	    }
}
