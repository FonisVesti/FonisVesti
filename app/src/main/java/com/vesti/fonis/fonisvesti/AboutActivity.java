package com.vesti.fonis.fonisvesti;

import android.os.Bundle;

/**
 * Created by Dusan on 24.3.2016..
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about);
    }

}
