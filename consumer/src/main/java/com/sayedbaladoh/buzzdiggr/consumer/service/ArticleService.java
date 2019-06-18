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

import com.sayedbaladoh.buzzdiggr.consumer.model.Article;
import com.sayedbaladoh.buzzdiggr.consumer.repository.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleService.class);

	@Value("${elasticsearch.index.crawler}")
	private String indexName;

	@Value("${elasticsearch.type.article}")
	private String articleType;

	public void add(Article article) {
		if (article.getId() == null || article.getId().equals(""))
			article.setId(UUID.randomUUID().toString());
		articleRepository.save(article);
	}

	public void add(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	// public void addBulk(List<Article> articles) {
	// try {
	// if (!template.indexExists(indexName)) {
	// template.createIndex(indexName);
	// }
	// ObjectMapper mapper = new ObjectMapper();
	// List<IndexQuery> queries = new ArrayList<>();
	// for (Article article : articles) {
	// IndexQuery indexQuery = new IndexQuery();
	// indexQuery.setSource(mapper.writeValueAsString(article));
	// indexQuery.setIndexName(indexName);
	// indexQuery.setType(articleType);
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
		articleRepository.deleteAll();
	}

	public List<Article> getAll() {
		List<Article> articlesList = new ArrayList<>();
		Iterable<Article> articleses = articleRepository.findAll();
		articleses.forEach(articlesList::add);
		return articlesList;
	}

	public Page<Article> getAll(Pageable page) {
		return articleRepository.findAll(page);
	}

	// search term all fields
	public List<Article> get(String query) {
		List<Article> articlesList = new ArrayList<>();
		Iterable<Article> articleses = articleRepository.search(queryStringQuery(query));
		articleses.forEach(articlesList::add);
		return articlesList;
	}

	public Page<Article> getByText(String text, Pageable pageable) {
		return articleRepository.findByText(text, pageable);
	}

	public List<Article> getByTitle(String title) {
		return articleRepository.findByTitle(title);
	}

}
