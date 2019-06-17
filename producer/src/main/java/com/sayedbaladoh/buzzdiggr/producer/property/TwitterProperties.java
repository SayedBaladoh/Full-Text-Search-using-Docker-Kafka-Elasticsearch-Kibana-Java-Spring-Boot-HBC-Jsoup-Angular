package com.sayedbaladoh.buzzdiggr.producer.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Twitter Properties specific to Twitter Social properties.
 *
 * <p>
 * Properties are configured in the application.properties file.
 * Bind all the file Twitter properties  to a POJO class
 * </p>
 * 
 *  @author SayedBaladoh
 */
@ConfigurationProperties(prefix = "social.twitter.auth", ignoreUnknownFields = false)
public class TwitterProperties {

	private String apiKey;
    private String apiSecretKey;
    private String accessToken;
    private String accessTokenSecret;
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getApiSecretKey() {
		return apiSecretKey;
	}
	public void setApiSecretKey(String apiSecretKey) {
		this.apiSecretKey = apiSecretKey;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

}
