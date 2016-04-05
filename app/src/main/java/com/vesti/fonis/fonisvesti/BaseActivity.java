package com.vesti.fonis.fonisvesti;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.vesti.fonis.fonisvesti.utils.Preferences;
import com.vesti.fonis.fonisvesti.utils.Util;

/**
 * Created by Sarma on 4/4/2016.
 */
public class BaseActivity extends ActionBarActivity {

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


}
