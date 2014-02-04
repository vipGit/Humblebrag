package com.twitter.humblebrag.util;

//import com.android.volley.toolbox.StringRequest;
import com.twitter.humblebrag.R;

import android.content.res.Resources;

public class ApplicationConstants {
	
	private static ApplicationConstants constants;
	
	public final String CONSUMER_KEY;
	public final String CONSUMER_SECRET;
    public final String COUNT;
	public final String URL_TOKEN;
	public final String URL_HUMBLEBRAG;
	public final String ENCODING = "UTF-8";
	public final String HEADER_AUTH = "Authorization";
	public final String AUTH_TYPE_BASIC = "Basic ";
	public final String AUTH_TYPE_BEARER = "Bearer ";
	public final String PARAM_GRANT= "grant_type";
	public final String GRANT_VALUE = "client_credentials";
	
	public ApplicationConstants(Resources resources) {
		CONSUMER_KEY = String.format(resources.getString(R.string.consumer_key));
		CONSUMER_SECRET = String.format(resources.getString(R.string.consumer_secret));
		URL_TOKEN = String.format(resources.getString(R.string.url_bearer_token));
		URL_HUMBLEBRAG = String.format(resources.getString(R.string.url_humblebrag_timeline));
        COUNT = resources.getString(R.string.count);
	}

	public static ApplicationConstants getInstance(Resources resources) {
		if (constants == null) {
			constants = new ApplicationConstants(resources);
		}
		return constants;
	}
}
