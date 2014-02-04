package com.twitter.humblebrag.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.twitter.humblebrag.app.activity.TweetActivity;
import com.twitter.humblebrag.util.TweetListAdapter;
import com.twitter.humblebrag.volley.logic.VolleyLogic;

/**
 * Created by vipulsomani on 1/29/14.
 */
public class HumblebragListFragment extends SherlockListFragment implements AbsListView.OnScrollListener{
    private TweetListAdapter tweetListAdapter;
    private boolean isScrollStateIdle = true;
    private int visibleItemCountThreshold= 5;
    private boolean isLoading = true;
    private  int previousTotal = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tweetListAdapter = new TweetListAdapter(getSherlockActivity());
        VolleyLogic.initialise(getSherlockActivity(), tweetListAdapter, getSherlockActivity());
        VolleyLogic.callApi(getSherlockActivity());
        setListAdapter(tweetListAdapter);
        getListView().setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(!isScrollStateIdle){
            return;
        }
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!isLoading && (firstVisibleItem + visibleItemCountThreshold) > (totalItemCount - visibleItemCount)) {
            getTweets();
            isLoading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
            isScrollStateIdle = false;
        }else{
            isScrollStateIdle = true;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        for(int i=0; i < tweetListAdapter.getCount(); i++){
            TweetFragment.tweetsAll.add(tweetListAdapter.getItem(i));
        }
        Intent tweetPagerIntent = new Intent(getSherlockActivity(), TweetActivity.class);
        tweetPagerIntent.putExtra(TweetFragment.KEY_POSITION, position);
        startActivity(tweetPagerIntent);
    }

    private void getTweets(){
        VolleyLogic.callApi(getSherlockActivity());
    }
}
