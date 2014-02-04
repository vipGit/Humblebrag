package com.twitter.humblebrag.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.twitter.humblebrag.app.fragment.TweetFragment;


/**
 * Created by vipulsomani on 1/29/14.
 */
public class TweetPagerAdapter extends FragmentPagerAdapter {
    public TweetPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return TweetFragment.tweetsAll.size();
    }

    @Override
    public Fragment getItem(int position) {
        //TODO
        return (TweetFragment.newInstance(position));
    }
}
