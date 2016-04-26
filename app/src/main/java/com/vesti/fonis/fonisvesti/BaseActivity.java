package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vesti.fonis.fonisvesti.utils.Preferences;
import com.vesti.fonis.fonisvesti.utils.Util;

/**
 * Created by Sarma on 4/4/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            // Set the theme for the activity.
            getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);
            Log.e(Util.TAG, "BaseActivity: Theme applied");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(Preferences.isChanged()){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
