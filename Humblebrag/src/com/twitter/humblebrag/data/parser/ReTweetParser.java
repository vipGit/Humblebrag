package com.twitter.humblebrag.data.parser;

import org.json.simple.JSONObject;

import android.util.Log;

import com.twitter.humblebrag.data.ReTweet;

public class ReTweetParser {
	private static String TWEET = "text";
	private static String IMAGE = "profile_image_url_https";
	private static String SCREEN_NAME = "screen_name";
	private static String USER = "user";
	private static String RETWEET_STATUS = "retweeted_status";
	private static String ID = "id_str";

	public static ReTweet parseResponse(JSONObject jsonObject){
		//Log.i("HB", jsonObject.toJSONString());
		ReTweet reTweet = new ReTweet();
		if(jsonObject.get(RETWEET_STATUS) != null){
			jsonObject = (JSONObject) jsonObject.get(RETWEET_STATUS);
		}
		if(jsonObject.get(TWEET) != null){
			reTweet.setTweet(jsonObject.get(TWEET).toString());
		}
		if(jsonObject.get(ID) != null){
			reTweet.setId(jsonObject.get(ID).toString());
		}
		if(jsonObject.get(USER) != null){
			if(((JSONObject)jsonObject.get(USER)).get(SCREEN_NAME) != null){
				reTweet.setScreen_name(((JSONObject)jsonObject.get("user")).get(SCREEN_NAME).toString());
			}
		}
		if(jsonObject.get(USER) != null){
			if(((JSONObject)jsonObject.get(USER)).get(IMAGE) != null){
				reTweet.setImageUrlLow(((JSONObject)jsonObject.get(USER)).get(IMAGE).toString());
				reTweet.setImageUrlMedium(reTweet.getImageUrlLow().replace("normal", "bigger"));
				reTweet.setImageUrlHigh(reTweet.getImageUrlLow().replace("_normal", ""));
			}
		}
		return reTweet;
	} 
 }
