package com.sayedbaladoh.buzzdiggr.consumer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sayedbaladoh.buzzdiggr.consumer.model.Article;


@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

	Page<Article> findByText(String text, Pageable pageable);

	List<Article> findByTitle(String title);
	
}
