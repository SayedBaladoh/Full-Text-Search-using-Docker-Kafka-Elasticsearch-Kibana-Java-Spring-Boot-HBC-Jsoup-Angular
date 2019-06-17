package com.sayedbaladoh.buzzdiggr.producer.service.stream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sayedbaladoh.buzzdiggr.producer.model.Tweet;
import com.sayedbaladoh.buzzdiggr.producer.util.StringUtils;

/**
 * Tweet Handler 
 * 
 * @author SayedBaladoh
 *
 */
@Service
public class TweetHandler {

	/**
	 * Parse Tweet from String to Object
	 * 
	 * @param jsonText
	 * 				- Json as a string
	 * @return
	 * 			- Parsed Tweet object
	 */
	public Tweet parseTweet(String jsonText) {
		
		Tweet tweet = new Tweet();
		try {
			JSONObject jsonObject = new JSONObject(jsonText);

			tweet.setId(jsonObject.getString("id_str"));
			String cleanedtext = StringUtils.cleanText(jsonObject.getString("text"));
			tweet.setText(cleanedtext);

			// "EEE MMM d HH:mm:ss Z yyyy"
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
			try {
				Date date = sdf.parse(jsonObject.getString("created_at"));
				tweet.setDate(date);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}

			tweet.setLanguage(jsonObject.getString("lang"));

			JSONObject user = jsonObject.getJSONObject("user");
			tweet.setUserName(user.getString("name"));

			// this.place = jsonObject.getString("place");
			// System.out.println("Text: " + jsonObject.getString("text"));
			// System.out.println("Created_at: " +
			// jsonObject.getString("created_at"));
			// System.out.println("lang: " + jsonObject.getString("lang"));
			// System.out.println("id_str: " + jsonObject.getString("id_str"));
			// System.out.println("place: " + jsonObject.getString("place"));
			// System.out.println("user name: " + user.getString("name"));
			// System.out.println("user profile_image_url: " +
			// user.getString("profile_image_url"));
			// System.out.println();
			
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		
		return tweet;
	}

}