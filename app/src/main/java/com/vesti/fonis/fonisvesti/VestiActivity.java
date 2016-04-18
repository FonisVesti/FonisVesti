package com.vesti.fonis.fonisvesti;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;

/**
 * Created by Dusan on 24.3.2016..
 */
public class VestiActivity extends BaseActivity{
    private int brojStrane;
    private String[] searchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vesti);
        brojStrane=0;

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchResults = searchNews(query);
        }

        // TODO - create custom adapter
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.activity_list_item,searchResults);
       //setListAdapter(arrayAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        brojStrane=1;
        Vesti.svuciVesti(brojStrane);
    }

    // TODO - implement searchNews method
    /**
     * @param query text that is searched for
     * @return search results
     */
    private String[] searchNews(String query) {
        return null;
    }

}
