package com.twitter.humblebrag.app.activity;


import com.twitter.humblebrag.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class StartActivity extends BaseActivity {
    private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
    }
	
	public void showOther(View v){
        startActivity(new Intent(context, HumblebragActivity.class));
    }
    
}
