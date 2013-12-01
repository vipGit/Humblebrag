package com.twitter.humblebrag.data;

import java.io.Serializable;

public class ReTweet implements Serializable{
	
	private static final long serialVersionUID = -1099833709381936191L;
	private String screen_name;
	private String tweet;
	private String imageUrl;
	private String id;
	
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		StringBuilder reTweet = new StringBuilder();
		reTweet.append("Screen_name = ").append(screen_name);
		reTweet.append(": Tweet =").append(tweet);
		reTweet.append(": imageUrl = ").append(imageUrl);
		reTweet.append(": id = ").append(id);

		return reTweet.toString();
	}
	
	
	
}
