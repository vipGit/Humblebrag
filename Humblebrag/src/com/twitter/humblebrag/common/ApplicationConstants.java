package com.twitter.humblebrag.common;

import com.twitter.humblebrag.R;

import android.content.res.Resources;

public class ApplicationConstants {
	
	private static ApplicationConstants constants;
	
	public final String LOADING_MESSAGE;
	public final String LOADING_TITLE;
	public final String CONSUMER_KEY;
	public final String CONSUMER_SECRET;
	public final String URL_TOKEN;
	public final String URL_HUMBLEBRAG;
	public final String URL_HUMBLEBRAG_PAGING;
	public final String LOADING_ERROR;
	public final String ENCODING = "UTF-8";
	public final String HEADER_AUTH = "Authorization";
	public final String HEADER_CONTENT = "Content-Type";
	public final String AUTH_TYPE_BASIC = "Basic ";
	public final String AUTH_TYPE_BEARER = "Bearer ";
	public final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded;charset=UTF-8";
	public final String PARAM_GRANT= "grant_type";
	public final String GRANT_VALUE = "client_credentials";
	
	public ApplicationConstants(Resources resources) {
		LOADING_MESSAGE = String.format(resources.getString(R.string.loading_message));
		LOADING_TITLE = String.format(resources.getString(R.string.loading_title));
		LOADING_ERROR = resources.getString(R.string.loading_error);
		CONSUMER_KEY = String.format(resources.getString(R.string.consumer_key));
		CONSUMER_SECRET = String.format(resources.getString(R.string.consumer_secret));
		URL_TOKEN = String.format(resources.getString(R.string.url_bearer_token));
		URL_HUMBLEBRAG = String.format(resources.getString(R.string.url_humblebrag_timeline));
		URL_HUMBLEBRAG_PAGING = resources.getString(R.string.url_humblebrag_timeline_paging);
	}

	public static ApplicationConstants getInstance(Resources resources) {
		if (constants == null) {
			constants = new ApplicationConstants(resources);
		}
		return constants;
	}
}
