package com.vesti.fonis.fonisvesti;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static RelativeLayout mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickIbtnVestiListener();
        onClickIbtnONamaListener();
        onClickIbtnProjektiListener();
        onClickIbtnMSDNAAListener();
//        News.downloadNews(1);
//        ArrayList<OnePieceOfNews> proba= News.searchNews("Philip");
//        Log.d("vesti",proba.size()+"");
//        for (int i=0;i<proba.size();i++){
//            Log.d("vesti",proba.get(i).toString());
//        }
        //int brojVesti=News.svuciBrojVesti();
        //Log.d("vesti", brojVesti + "");
    }

    public void onClickIbtnVestiListener() {
        mButton = (RelativeLayout) findViewById(R.id.rlVesti);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.NewsActivity");
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
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.AboutActivity");
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
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.ProjectsActivity");
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
