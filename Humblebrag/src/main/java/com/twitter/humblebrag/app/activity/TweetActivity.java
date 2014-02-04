package com.twitter.humblebrag.app.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.twitter.humblebrag.R;
import com.twitter.humblebrag.app.fragment.TweetFragment;
import com.twitter.humblebrag.util.TweetPagerAdapter;


/**
 * Created by vipulsomani on 1/29/14.
 */
public class TweetActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_pager);
        int position = getIntent().getIntExtra(TweetFragment.KEY_POSITION, 0);
        ViewPager viewPager = (ViewPager)findViewById(R.id.tweet_view_pager);
        viewPager.setAdapter(new TweetPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
               TweetFragment.currentPosition = i;
                Log.i("HB", "Current " + TweetFragment.currentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
