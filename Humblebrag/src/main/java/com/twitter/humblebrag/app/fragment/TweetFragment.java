package com.twitter.humblebrag.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.toolbox.NetworkImageView;
import com.twitter.humblebrag.R;
import com.twitter.humblebrag.util.ImageSaver;
import com.twitter.humblebrag.volley.logic.VolleyLogic;
import com.twitter.humblebrag.volley.response.ReTweet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vipulsomani on 1/29/14.
 */
public class TweetFragment extends SherlockFragment {
    public static String KEY_POSITION = "key_position";
    public static ArrayList<ReTweet> tweetsAll = new ArrayList<ReTweet>();
    private int width;
    public static int currentPosition;

    public static TweetFragment newInstance(int position){
        TweetFragment tweetFragment = new TweetFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        tweetFragment.setArguments(args);
        return  tweetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_tweet, container, false);
        createTweetPage(result, getArguments().getInt(KEY_POSITION, 0));
        DisplayMetrics dm = new DisplayMetrics();
        getSherlockActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        return(result);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_image){
            try {
                String location = ImageSaver.saveImage(tweetsAll.get(currentPosition).getScreen_name(), getImageUrl(getSherlockActivity(), currentPosition), getSherlockActivity());
                Toast.makeText(getSherlockActivity().getApplicationContext(), "Image saved at locations " + location, Toast.LENGTH_LONG);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getSherlockActivity().getApplicationContext(), "Image Error: Could Not Save", Toast.LENGTH_LONG);
                return false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
   private  String getImageUrl(Context context, int position){
       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
       if(preferences.getBoolean("isHigh", false)){
           Log.i("HB", "True High");
           return tweetsAll.get(position).getImageUrlHigh();
       }
       Log.i("HB", "True False");
       return tweetsAll.get(position).getImageUrlMedium();

   }
   private View createTweetPage(View result, int position){
       if(tweetsAll == null){
           Log.i("HB","tweets null");
           return result;
       }
       Log.i("HB","creating tweet pager");
       ((TextView) result.findViewById(R.id.tweet_user)).setText("@" + tweetsAll.get(position).getScreen_name());
       ((TextView)result.findViewById(R.id.tweet_text)).setText(tweetsAll.get(position).getTweet());
       ((NetworkImageView)result.findViewById(R.id.image_volley)).setImageUrl(getImageUrl(getSherlockActivity(), position), VolleyLogic.mImageLoader);
       ((NetworkImageView) result.findViewById(R.id.image_volley)).setMaxHeight(width);
       ((NetworkImageView) result.findViewById(R.id.image_volley)).setMinimumHeight(width);
       return result;
   }
}
