package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vesti.fonis.fonisvesti.adapter.ListViewAdapter;
import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.utils.Util;


/**
 * Created by Dusan on 24.3.2016..
 */
public class NewsActivity extends BaseActivity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Button btnLoadMore;

    private int brojStrane;
    private News[] searchResults;

    // Flag for current page
    int mCurrentPage ;

    private ProgressDialog mProgressDialog;
    private LinearLayout llProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vesti);

        // TODO - handle events when user press back and close the dialog
        mProgressDialog = ProgressDialog.show(this, null, "Učitavanje vesti..", true, true);

        // Fire the downloader
        mCurrentPage = 1;
        downloadNews(new int[]{mCurrentPage, ++mCurrentPage});

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent downloadIntent = new Intent(NewsActivity.this, NewsDownloaderService.class);
                stopService(downloadIntent);
            }
        });

        // Init elements
        mListView = (ListView) findViewById(R.id.list);
        btnLoadMore = new Button(this);
        llProgressbar = (LinearLayout) findViewById(R.id.llProgressBar);
        mAdapter = new ListViewAdapter(this, News.newsList);

        btnLoadMore.setText("Ucitaj još vesti..");
        mListView.setAdapter(mAdapter);
        mListView.addFooterView(btnLoadMore);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                downloadNews(new int[]{++mCurrentPage});
                llProgressbar.setVisibility(View.VISIBLE);
                btnLoadMore.setVisibility(View.GONE);
            }
        });
    }

    private void downloadNews(int[] pages) {
        Intent downloadIntent = new Intent(this, NewsDownloaderService.class);
        downloadIntent.putExtra("pageNumber", pages);
        downloadIntent.putExtra("receiver", new VestiDownloadReceiver(new Handler()));
        startService(downloadIntent);
    }

    @SuppressLint("ParcelCreator")
    private class VestiDownloadReceiver extends ResultReceiver {
        public VestiDownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == NewsDownloaderService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                mProgressDialog.setProgress(progress);
                mProgressDialog.dismiss();
                llProgressbar.setVisibility(View.GONE);
                btnLoadMore.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    // TODO - implement searchNews method

    /**
     * @param query text that is searched for
     * @return search results
     */
    private OnePieceOfNews[] searchNews(String query) {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);


        // TODO - add search dialog if there's no space for searchview
        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(Util.TAG,query);
                mAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Util.TAG,newText);
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
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
