package com.sayedbaladoh.buzzdiggr.producer.model;

import java.util.Date;

public class Tweet {

	private String id;
	private String text;
	private Date date;
	private String language;
	private String userName;

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
