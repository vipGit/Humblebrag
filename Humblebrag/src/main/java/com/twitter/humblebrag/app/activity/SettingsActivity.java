package com.twitter.humblebrag.app.activity;


import android.os.Bundle;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.twitter.humblebrag.R;

/**
 * Created by vipulsomani on 1/30/14.
 */
public class SettingsActivity extends SherlockPreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


}
