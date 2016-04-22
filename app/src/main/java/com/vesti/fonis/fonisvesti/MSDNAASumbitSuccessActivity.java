package com.vesti.fonis.fonisvesti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MSDNAASumbitSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_msdnaasumbit_success);
    }
}
