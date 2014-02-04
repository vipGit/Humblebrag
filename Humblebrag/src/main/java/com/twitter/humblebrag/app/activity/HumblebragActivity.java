package com.twitter.humblebrag.app.activity;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.twitter.humblebrag.R;
import com.twitter.humblebrag.app.fragment.HumblebragListFragment;
import com.twitter.humblebrag.volley.logic.VolleyLogic;
import com.twitter.humblebrag.util.TweetListAdapter;

/**
 * Created by vipulsomani on 1/28/14.
 */
public class HumblebragActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new HumblebragListFragment()).commit();
        }
    }

}
