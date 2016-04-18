package com.vesti.fonis.fonisvesti;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

    private static RelativeLayout mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickIbtnVestiListener();
        onClickIbtnONamaListener();
        onClickIbtnProjektiListener();
        onClickIbtnMSDNAAListener();
        //Vesti.svuciVesti(1);
        //int brojVesti=Vesti.svuciBrojVesti();
        //Log.d("vesti", brojVesti + "");
    }

    public void onClickIbtnVestiListener() {
        mButton = (RelativeLayout) findViewById(R.id.rlVesti);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.VestiActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnONamaListener() {
        mButton = (RelativeLayout) findViewById(R.id.rlOnama);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.ONamaActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnProjektiListener() {
        mButton = (RelativeLayout) findViewById(R.id.rlProjekti);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.ProjektiActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnMSDNAAListener() {
        mButton = (RelativeLayout) findViewById(R.id.rlMsdnaa);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.MSDNAAActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    /*
    public void linkovanje (Button button, final String sajt) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(sajt);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        // TODO - add search dialog if there's no space for searchview
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }



    }
}
