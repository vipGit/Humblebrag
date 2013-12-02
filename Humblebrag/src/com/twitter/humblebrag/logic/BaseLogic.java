package com.twitter.humblebrag.logic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.twitter.humblebrag.common.ApplicationConstants;
import com.twitter.humblebrag.data.ReTweet;
import com.twitter.humblebrag.data.parser.ReTweetParser;

import android.content.Context;
import android.util.Log;

public class BaseLogic {
	private static long max_id = 0;
	private static ApplicationConstants constants;
	
	public static ArrayList<ReTweet> getHumblebragResponse(Context context, boolean isStart) throws ClientProtocolException, IOException{
		constants = ApplicationConstants.getInstance(context.getResources());
		String encodedKeys = encodeKeys(constants.CONSUMER_KEY, constants.CONSUMER_SECRET);
		//Log.i("HB", "encoded keys obtained " + encodedKeys);
		String bearerToken = requestBearerToken(constants.URL_TOKEN, encodedKeys);
		//Log.i("HB", "Token obtained" + bearerToken);
		if(bearerToken == null) {
			return null;
		}
		ArrayList<ReTweet> reTweets = null;
		if(isStart){	
			reTweets = fetchTimelineData(constants.URL_HUMBLEBRAG, bearerToken);
		}else{
			reTweets = fetchTimelineData(String.format((constants.URL_HUMBLEBRAG_PAGING), max_id), bearerToken);
		}
		return reTweets;
	}
	
	// Encodes the consumer key and secret to create the basic authorization key
	private static String encodeKeys(String consumerKey, String consumerSecret) {
	
	    try {	
	        String encodedConsumerKey = URLEncoder.encode(consumerKey, constants.ENCODING);
	        String encodedConsumerSecret = URLEncoder.encode(consumerSecret, constants.ENCODING);
	        String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
	       // Log.i("HB", "encode " + fullKey);
		    return new String(Base64.encodeBase64(fullKey.getBytes()));  
	    } catch (UnsupportedEncodingException e) {
	        return new String(e.getMessage());
	    }
	}
	
	// Request for bearer token to authenticate the application
	private static String requestBearerToken(String urlToken, String encodedKeys) throws IOException {
		
		HttpPost post = new HttpPost(urlToken);
		post.setHeader(constants.HEADER_AUTH, constants.AUTH_TYPE_BASIC + encodedKeys);
		post.setHeader(constants.HEADER_CONTENT, constants.CONTENT_TYPE_VALUE); 
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair(constants.PARAM_GRANT, constants.GRANT_VALUE));
		post.setEntity(new UrlEncodedFormEntity(params, constants.ENCODING));
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		//Log.i("HB", "Response "+ response.getStatusLine().getReasonPhrase());
		//Log.i("HB", "Response "+ EntityUtils.toString(rp.getEntity()));
		//Log.i("HB", "Response "+ response.getStatusLine().getStatusCode());
		String result = null;
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = (JSONObject)JSONValue.parse(result);
            return parseTokenResponse(jsonObject);
    		
		}
		return null;
	}
	
	private static String parseTokenResponse(JSONObject jsonObject){
		String token = null;
		if (jsonObject != null) {
			String tokenType;
			tokenType = (String)jsonObject.get("token_type");
			token = (String)jsonObject.get("access_token");
			if(tokenType.equals("bearer") && (token != null)){
				return token;
			}
		}
		return token;
	}
	
	// Request for  retweets from Humblebrag's timeline
	private static ArrayList<ReTweet> fetchTimelineData(String urlTimeline, String bearerToken) throws ClientProtocolException, IOException {
		
			HttpGet get = new HttpGet(urlTimeline);
			get.setHeader(constants.HEADER_AUTH, constants.AUTH_TYPE_BEARER + bearerToken);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(get);
			//Log.i("HB", "Request "+ urlTimeline);
			//Log.i("HB", "Response "+ response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// Parse the JSON response into a JSON mapped object to fetch fields from.
				JSONArray jsonArray = (JSONArray)JSONValue.parse(EntityUtils.toString(response.getEntity()));
				//Log.i("HB", jsonArray.toJSONString());
				ArrayList<ReTweet> reTweets = new ArrayList<ReTweet>();
				if (jsonArray != null) {
					@SuppressWarnings("unchecked")
					Iterator<JSONObject> iterator = jsonArray.iterator();
					while(iterator.hasNext()){
						ReTweet reTweet = ReTweetParser.parseResponse(iterator.next());
						reTweets.add(reTweet);
						//Log.i("HB", reTweet.getTweet());
						//Log.i("HB", reTweet.getId());
						max_id = Long.valueOf(reTweet.getId()) - 1;
					}
					//Log.i("HB", "max_id is " + max_id);
					return reTweets;
				}
			}
			
			return null;		
	}
	
}
