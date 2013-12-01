package com.twitter.humblegrag.common;

import com.test.humblebrag.R;

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
	
	public ApplicationConstants(Resources resources) {
		LOADING_MESSAGE = String.format(resources.getString(R.string.loading_message));
		LOADING_TITLE = String.format(resources.getString(R.string.loading_title));
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
