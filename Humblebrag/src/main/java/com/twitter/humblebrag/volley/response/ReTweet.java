package com.twitter.humblebrag.volley.response;


public class ReTweet{
	
	private String screen_name;
	private String tweet;
	private String imageUrlLow;
	private String imageUrlMedium;
	private String imageUrlHigh;
	private String id;
	
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getImageUrlLow() {
		return imageUrlLow;
	}
	public void setImageUrlLow(String imageUrlLow) {
		this.imageUrlLow = imageUrlLow;
	}
	public String getImageUrlMedium() {
		return imageUrlMedium;
	}
	public void setImageUrlMedium(String imageUrlMedium) {
		this.imageUrlMedium = imageUrlMedium;
	}
	public String getImageUrlHigh() {
		return imageUrlHigh;
	}
	public void setImageUrlHigh(String imageUrlHigh) {
		this.imageUrlHigh = imageUrlHigh;
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
		reTweet.append(": imageUrlHigh = ").append(imageUrlHigh);
		reTweet.append(": id = ").append(id);

		return reTweet.toString();
	}
	
	
	
}
