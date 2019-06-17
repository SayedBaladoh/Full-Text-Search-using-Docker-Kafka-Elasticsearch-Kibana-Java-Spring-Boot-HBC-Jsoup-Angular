package com.sayedbaladoh.buzzdiggr.consumer.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "stream", type = "tweet")
public class Tweet {

	@Id
	private String id;
	private String text;
	private Date date;
	private String language;
	private String userName;
	
	public Tweet(){
		
	}

	public Tweet(String id, String text, Date date, String language, String userName) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
		this.language = language;
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", text=" + text + ", date=" + date + ", language=" + language + ", userName="
				+ userName + "]";
	}
	
}
