package com.vesti.fonis.fonisvesti;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Dusan on 24.3.2016..
 */
public class VestiActivity extends ActionBarActivity{
    int brojStrane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vesti);
        brojStrane=0;

    }

    @Override
    protected void onStart() {
        super.onStart();
        brojStrane=1;
        Vesti.svuciVesti(brojStrane);
    }

}
