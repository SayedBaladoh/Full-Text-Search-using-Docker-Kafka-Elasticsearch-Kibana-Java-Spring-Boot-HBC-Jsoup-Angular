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

import com.sayedbaladoh.buzzdiggr.consumer.model.Article;
import com.sayedbaladoh.buzzdiggr.consumer.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

	private final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;

	// @PostMapping()
	// public Article addNewArticle(@RequestBody Article article) {
	// LOG.info("Adding article : {}", article);
	// articleService.add(article);
	// LOG.info("Added article : {}", article);
	// return article;
	// }

	@GetMapping("/all")
	public List<Article> getAllArticle() {
		return articleService.getAll();
	}

	@GetMapping()
	public Page<Article> getAllArticle(Pageable page) {
		return articleService.getAll(page);
	}

	@GetMapping(params = { "q" })
	public List<Article> search(@RequestParam(name = "q") String keyword) {
		return articleService.getByText(keyword);
	}

	@GetMapping(params = { "title" })
	public List<Article> searchByUserName(@RequestParam(name = "title") String title) {
		return articleService.getByTitle(title);
	}

}
