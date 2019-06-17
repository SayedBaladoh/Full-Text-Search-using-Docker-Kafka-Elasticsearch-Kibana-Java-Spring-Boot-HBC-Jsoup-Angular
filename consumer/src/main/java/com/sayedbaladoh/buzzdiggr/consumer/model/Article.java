package com.sayedbaladoh.buzzdiggr.consumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "crawler", type = "article")
public class Article {

	@Id
    private String id;
	private String url;
	private String title;
	private String text;
	
	public Article(){
		
	}
	
	public Article(String url, String title, String text) {
		super();
		this.url = url;
		this.title = title;
		this.text = text;
	}
	
	public Article(String id, String url, String title, String text) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Article [url=" + url + ", title=" + title + ", text=" + text + "]";
	}

}
