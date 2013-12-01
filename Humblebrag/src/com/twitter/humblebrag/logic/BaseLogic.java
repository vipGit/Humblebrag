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
import com.twitter.humblebrag.data.ReTweet;
import com.twitter.humblebrag.data.parser.ReTweetParser;
import com.twitter.humblegrag.common.ApplicationConstants;

import android.content.Context;
import android.util.Log;

public class BaseLogic {
	
	public static ArrayList<ReTweet> getHumblebragResponse(Context context, int page) throws ClientProtocolException, IOException{
		String encodedKeys = encodeKeys(ApplicationConstants.getInstance(context.getResources()).CONSUMER_KEY, ApplicationConstants.getInstance(context.getResources()).CONSUMER_SECRET);
		Log.i("HB", "encoded keys obtained " + encodedKeys);
		String bearerToken = requestBearerToken(ApplicationConstants.getInstance(context.getResources()).URL_TOKEN, encodedKeys);
		Log.i("HB", "Token obtained" + bearerToken);
		if(bearerToken == null) {
			return null;
		}
		ArrayList<ReTweet> reTweets = fetchTimelineData(String.format(ApplicationConstants.getInstance(context.getResources()).URL_HUMBLEBRAG, page), bearerToken);
		return reTweets;
	}
	
	// Encodes the consumer key and secret to create the basic authorization key
	private static String encodeKeys(String consumerKey, String consumerSecret) {
	
	    try {	
	        String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
	        String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
	        String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
	        Log.i("HB", "encode " + fullKey);
		    return new String(Base64.encodeBase64(fullKey.getBytes()));  
	    } catch (UnsupportedEncodingException e) {
	        return new String(e.getMessage());
	    }
	}
	
	// Constructs the request for requesting a bearer token and returns that token as a string
	private static String requestBearerToken(String endPointUrl, String encodedKeys) throws IOException {
		
		HttpPost post = new HttpPost(endPointUrl);
		post.setHeader("Authorization", "Basic " + encodedKeys);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "client_credentials"));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpClient hc = new DefaultHttpClient();
		HttpResponse rp = hc.execute(post);
		Log.i("HB", "Response "+ rp.getStatusLine().getReasonPhrase());
		//Log.i("HB", "Response "+ EntityUtils.toString(rp.getEntity()));
		Log.i("HB", "Response "+ rp.getStatusLine().getStatusCode());
		String result = null;
		if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(rp.getEntity());
            Log.i("HB", "Result "+ result);   
            JSONObject obj = (JSONObject)JSONValue.parse(result);
    		Log.i("HB", obj.toString());	
    		if (obj != null) {
    			String tokenType;
    			tokenType = (String)obj.get("token_type");
				String token = (String)obj.get("access_token");
				return ((tokenType.equals("bearer")) && (token != null)) ? token : "";            
    		}
		}
		return null;
	}
	
	// Fetches the first tweet from a given user's timeline
	private static ArrayList<ReTweet> fetchTimelineData(String endPointUrl, String bearerToken) throws ClientProtocolException, IOException {
		
			HttpGet get = new HttpGet(endPointUrl);
			
			get.setHeader("Authorization", "Bearer " + bearerToken);
			HttpClient hc = new DefaultHttpClient();
			HttpResponse rp = hc.execute(get);
			Log.i("HB", "Request "+ endPointUrl);
			//Log.i("HB", "Response "+ EntityUtils.toString(rp.getEntity()));
			Log.i("HB", "Response "+ rp.getStatusLine().getStatusCode());
			// Parse the JSON response into a JSON mapped object to fetch fields from.
			JSONArray obj = (JSONArray)JSONValue.parse(EntityUtils.toString(rp.getEntity()));
			//Log.i("HB", obj.toJSONString());
			ArrayList<ReTweet> reTweets = new ArrayList<ReTweet>();
			if (obj != null) {
				@SuppressWarnings("unchecked")
				Iterator<JSONObject> iterator = obj.iterator();
				while(iterator.hasNext()){
					ReTweet reTweet = ReTweetParser.parseResponse(iterator.next());
					reTweets.add(reTweet);
					Log.i("HB", reTweet.getTweet());
				}
				return reTweets;
			}
			return null;		
	}
	
}
