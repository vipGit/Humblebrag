package com.twitter.humblebrag.app;

import java.net.URL;
import java.util.ArrayList;
import com.test.humblebrag.R;
import com.twitter.humblebrag.data.ReTweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix.ScaleToFit;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class HumblebragActivity extends BaseActivity implements OnScrollListener{
	private int page;
	private ListView list;
	private ReTweetListAdapter adapter;
	private int width;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_humblebrag);
		context = this;
		Intent intent = getIntent();
		page = 0;
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		list = (ListView)findViewById(R.id.list);
		ArrayList<ReTweet> reTweets = (ArrayList<ReTweet>) intent.getBundleExtra("data").getSerializable("reTweets");
		setResponse(reTweets, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.humblebrag, menu);
		return true;
	}
	
	public class ReTweetListAdapter extends ArrayAdapter<ReTweet> {
		private ArrayList<ReTweet> reTweets = new ArrayList<ReTweet>();
		
		public ReTweetListAdapter(Context context, int textViewResourceId,
				ArrayList<ReTweet> reTweets) {
			super(context, textViewResourceId, reTweets);
			this.reTweets = reTweets;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
            if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.list_layout, null);
            }
           ReTweet tweet = reTweets.get(position);
            if (tweet != null) {
                    TextView retweet = (TextView) v.findViewById(R.id.retweet);
                    ImageView image = (ImageView) v.findViewById(R.id.profileimage);

                    if (retweet != null) {
                            retweet.setText(tweet.getTweet());
                    }
                    if(image != null) {
                            image.setImageBitmap(getBitmap(tweet.getImageUrl()));
                            image.setMinimumHeight(width);                
                    }
            }
            return v;
		}
		private Bitmap getBitmap(String profileImageUrl) {
	         try {
	                 URL url = new URL(profileImageUrl);
	                 return BitmapFactory.decodeStream(url.openConnection() .getInputStream()); 
	         }
	         catch(Exception e) {
	                 return null;
	         }
		
		
		}
	}
	
	@Override
	protected void setResponse(ArrayList<ReTweet> reTweets, boolean isError) {
		super.setResponse(reTweets, isError);
		if(!isError){
			if(adapter == null){
				adapter = new ReTweetListAdapter(context, R.layout.list_layout, reTweets);
			}else{
				Log.i("HB", "Count is " + adapter.getCount());
				adapter.addAll(reTweets);
				Log.i("HB", "Count is " + adapter.getCount());
			}
			list.setAdapter(adapter);
			list.setOnScrollListener(this);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.i("HB", firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);
		if (firstVisibleItem + visibleItemCount > totalItemCount -1 && getTask == null) {
			page++;
			getTask = new getHumblebragTask();
			getTask.execute(page);
		}		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {		
	}

}
