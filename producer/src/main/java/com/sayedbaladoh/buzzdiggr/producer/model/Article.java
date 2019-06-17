package com.sayedbaladoh.buzzdiggr.producer.model;

public class Article {

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
