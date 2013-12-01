package com.twitter.humblebrag.app;

import java.io.IOException;
import java.util.ArrayList;

import com.twitter.humblebrag.data.ReTweet;
import com.twitter.humblebrag.logic.BaseLogic;
import com.twitter.humblegrag.common.ApplicationConstants;

import android.os.AsyncTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class BaseActivity extends Activity {
	private ProgressDialog progressDialog;
	protected AsyncTask<Void, Void, ArrayList<ReTweet>> getTask;
	protected Context context;
	
	protected void setResponse(ArrayList<ReTweet> reTweets, boolean isError){
		
	}

	 protected class getHumblebragTask extends AsyncTask<Void, Void, ArrayList<ReTweet>>{
		 
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(context);
				progressDialog = ProgressDialog.show(context, ApplicationConstants.getInstance(context.getResources()).LOADING_TITLE, ApplicationConstants.getInstance(context.getResources()).LOADING_MESSAGE, true, true);
				super.onPreExecute();
			}

			@Override
			protected ArrayList<ReTweet> doInBackground(Void... params) {
				try {
					Log.i("HB", "Executing task");
					ArrayList<ReTweet> reTweets = BaseLogic.getHumblebragResponse(context);
					return reTweets;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(ArrayList<ReTweet> reTweets) {
				progressDialog.dismiss();
				getTask = null;
				if(reTweets == null){
					setResponse(reTweets, true);
				}else{
					setResponse(reTweets, false);
				}
				super.onPostExecute(reTweets);
			}
	    	
	    }

}
