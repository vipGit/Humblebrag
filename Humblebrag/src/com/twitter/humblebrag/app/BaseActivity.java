package com.twitter.humblebrag.app;

import java.io.IOException;
import java.util.ArrayList;

import com.twitter.humblebrag.common.ApplicationConstants;
import com.twitter.humblebrag.data.ReTweet;
import com.twitter.humblebrag.logic.BaseLogic;

import android.os.AsyncTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;

public class BaseActivity extends Activity implements OnCancelListener{
	private ProgressDialog progressDialog;
	protected AsyncTask<Boolean, Void, ArrayList<ReTweet>> getTask;
	protected Context context;
	
	protected void setResponse(ArrayList<ReTweet> reTweets, boolean isError){
		
	}

	 protected class getHumblebragTask extends AsyncTask<Boolean, Void, ArrayList<ReTweet>>{
		 
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(context);
				progressDialog = ProgressDialog.show(context, ApplicationConstants.getInstance(context.getResources()).LOADING_TITLE, ApplicationConstants.getInstance(context.getResources()).LOADING_MESSAGE, true, false);
				progressDialog.setCancelable(true);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setOnCancelListener(BaseActivity.this);
				super.onPreExecute();
			}

			@Override
			protected ArrayList<ReTweet> doInBackground(Boolean... params) {
				try {
					//Log.i("HB", "Executing task");
					ArrayList<ReTweet> reTweets = BaseLogic.getHumblebragResponse(context, params[0]);
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

	@Override
	public void onCancel(DialogInterface dialog) {
		getTask.cancel(true);
		getTask = null;
		setResponse(null, true);
		
	}
	 
}
