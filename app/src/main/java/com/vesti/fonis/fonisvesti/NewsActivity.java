package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vesti.fonis.fonisvesti.adapter.ListViewAdapter;
import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.utils.Util;


/**
 * Created by Dusan on 24.3.2016..
 */
public class NewsActivity extends BaseActivity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Button btnLoadMore;


    // Flag for current page
    int mCurrentPage ;

    private ProgressDialog mProgressDialog;
    private LinearLayout llProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vesti);
        if(News.newsList.isEmpty()) {
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
        }
        // Init elements
        mListView = (ListView) findViewById(R.id.list);
        btnLoadMore = new Button(this);
        llProgressbar = (LinearLayout) findViewById(R.id.llProgressBar);
        mAdapter = new ListViewAdapter(this, News.newsList);

        btnLoadMore.setText(R.string.loadmore_btn_txt);
        mListView.setAdapter(mAdapter);
        mListView.addFooterView(btnLoadMore);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(NewsActivity.this, NewsViewActivity.class);
                i.putExtra("newsPosition",position);
                Log.d(Util.TAG,Integer.toString(position));
                startActivity(i);
            }
        });

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
        downloadIntent.putExtra("caller",NewsDownloaderService.NEWS_ACTIVITY_CALLER);
        downloadIntent.putExtra("receiver", new NewsDownloadReceiver(new Handler()));
        startService(downloadIntent);
    }

    @SuppressLint("ParcelCreator")
    private class NewsDownloadReceiver extends ResultReceiver {
        public NewsDownloadReceiver(Handler handler) {
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
                //TODO - set background color for list view

                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);

        // TODO - add search dialog if there's no space for searchview
        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        //SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
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
