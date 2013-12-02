package com.twitter.humblebrag.app;

import java.util.ArrayList;

import com.twitter.humblebrag.R;
import com.twitter.humblebrag.common.ApplicationConstants;
import com.twitter.humblebrag.data.ReTweet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class StartActivity extends BaseActivity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				findViewById(R.id.button1).setClickable(false);
				getTask = new getHumblebragTask();
				getTask.execute(true);
			}
		});
    }

    
    
    @Override
	protected void setResponse(ArrayList<ReTweet> reTweets, boolean isError) {
    	super.setResponse(reTweets, isError);
    	if(!isError){
    		Intent intent = new Intent(context, HumblebragActivity.class);
    		Bundle data = new Bundle();
			data.putSerializable("reTweets", reTweets);
			intent.putExtra("data", data);
			startActivity(intent);
    	}else{
    		Toast.makeText(context, ApplicationConstants.getInstance(context.getResources()).LOADING_ERROR, Toast.LENGTH_LONG).show();
    	}
		findViewById(R.id.button1).setClickable(true);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	HumblebragActivity.resolution = item.getTitle().toString();
		Toast.makeText(context, "Setting Saved", Toast.LENGTH_LONG).show();
    	return true;
    } 

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }
	
	
    
}
