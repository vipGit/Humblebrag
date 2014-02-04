package com.twitter.humblebrag.volley.logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Request.Method;
import com.twitter.humblebrag.util.BitmapLru;
import com.twitter.humblebrag.volley.response.ReTweet;
import com.twitter.humblebrag.volley.response.parser.ReTweetParser;
import com.twitter.humblebrag.volley.request.TokenRequest;
import com.twitter.humblebrag.volley.request.TweetRequest;
import com.twitter.humblebrag.util.ApplicationConstants;
import com.twitter.humblebrag.util.TweetListAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipulsomani on 1/27/14.
 */
public class VolleyLogic {
    public static ImageLoader mImageLoader;
    private static RequestQueue mRequestQueue;
    public static String access_token;
    private static TweetJsonListener tweetJsonListener = new TweetJsonListener();;
    private static TweetListAdapter tweetListAdapter;
    private static Activity activity;
    private static ApplicationConstants constants;

    public static void initialise(Context context, TweetListAdapter tweetListAdapter, Activity activity){
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLru(64000));
        constants = ApplicationConstants.getInstance(context.getResources());
        VolleyLogic.tweetListAdapter = tweetListAdapter;
        VolleyLogic.activity = activity;
    }

    public static void callApi(Context context){
        getToken(context, constants.URL_TOKEN, constants.URL_HUMBLEBRAG);
    }

    private static void getToken(final Context context, String token_url, final String tweet_url){
        StringRequest request = new TokenRequest(Method.POST, token_url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject != null) {
                            String tokenType, token;
                        tokenType = jsonObject.getString("token_type");
                        token= jsonObject.getString("access_token");
                        if(tokenType.equals("bearer") && (token!= null)){
                            VolleyLogic.access_token = token;
                            Log.i("HB", access_token);
                            VolleyLogic.getTweets(context, tweet_url);
                        }
                    }

                    }catch(JSONException e){
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }, context);
        mRequestQueue.add(request);
    }

    private static void getTweets(Context context, String tweet_url){
        activity.setProgressBarIndeterminate(true);
        activity.setProgressBarIndeterminateVisibility(true);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("screen_name", "Humblebrag"));
        params.add(new BasicNameValuePair("count", getCount(context)));
        Log.i("HB", "Adding params");
        if (tweetListAdapter.getCount() > 0) {
            String maxId = tweetListAdapter.getItem(tweetListAdapter.getCount() - 1).getId();
            params.add(new BasicNameValuePair("max_id", "" + maxId));
            Log.i("HB", "Max Id " + maxId);
        }
        mRequestQueue.add(new TweetRequest(tweet_url, tweetJsonListener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("HB", "ERROR");
                error.printStackTrace();
            }
        }, params, context));
    }
    private static String getCount(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("count", "50");
    }
    private static class TweetJsonListener implements Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {
            JSONObject jsonObject;
            int count = response != null ? response.length() : 0;
            for (int i = 0; i < count; i++) {
                jsonObject = response.optJSONObject(i);
                if (jsonObject != null) {

                    ReTweet reTweet = ReTweetParser.parseResponse(jsonObject.toString());
                    tweetListAdapter.add(reTweet);
                }
            }
            activity.setProgressBarIndeterminateVisibility(false);
        }
    }
}
