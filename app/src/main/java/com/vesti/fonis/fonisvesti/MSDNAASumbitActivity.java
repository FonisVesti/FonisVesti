package com.vesti.fonis.fonisvesti;

import android.os.Bundle;
import android.view.Window;

public class MSDNAASumbitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();;
        setContentView(R.layout.activity_msdnaasumbit);
    }
}
