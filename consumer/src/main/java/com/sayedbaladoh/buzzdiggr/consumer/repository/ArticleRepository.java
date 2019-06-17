package com.sayedbaladoh.buzzdiggr.consumer.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sayedbaladoh.buzzdiggr.consumer.model.Article;


@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

	List<Article> findByText(String text);

	List<Article> findByTitle(String title);
	
}
