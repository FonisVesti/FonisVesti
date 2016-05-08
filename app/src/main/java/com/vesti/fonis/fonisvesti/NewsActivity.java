package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
    private NewsBroadcastReceiver newsBroadcastReceiver;

    // Flag for current page
    static int mCurrentPage;

    private ProgressDialog mProgressDialog;
    private LinearLayout llProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_news);

        mCurrentPage = 2;

        // Init elements
        init();
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.list);
        btnLoadMore = new Button(this);
        llProgressbar = (LinearLayout) findViewById(R.id.llProgressBar);
        newsBroadcastReceiver = new NewsBroadcastReceiver();

        btnLoadMore.setText(R.string.loadmore_btn_txt);
        mAdapter = new ListViewAdapter(NewsActivity.this, News.getNewsList());
        mListView.setAdapter(mAdapter);
        mListView.addFooterView(btnLoadMore);
        setOnClickListeners();

        registerReceiver(newsBroadcastReceiver, new IntentFilter(News.NEWS_DOWNLOADED_INTENT));

    }

    private class NewsBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Util.TAG, "Broadcast received");
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setOnClickListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(NewsActivity.this, NewsViewActivity.class);
                i.putExtra("newsId", mAdapter.getOnePieceOfNewsID(position));
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
        downloadIntent.putExtra("caller", NewsDownloaderService.NEWS_ACTIVITY_CALLER);
        downloadIntent.putExtra("receiver", new NewsDownloadReceiver(new Handler()));
        startService(downloadIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (News.getNewsList().isEmpty()) {

            mProgressDialog = ProgressDialog.show(this, null, "Učitavanje vesti..", true, true);


            downloadNews(new int[]{1, 2});
            mCurrentPage = 2;
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent downloadIntent = new Intent(NewsActivity.this, NewsDownloaderService.class);
                    stopService(downloadIntent);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (newsBroadcastReceiver != null)
            unregisterReceiver(newsBroadcastReceiver);
        super.onDestroy();
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
                if (progress == 0) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setProgress(progress);
                        mProgressDialog.dismiss();
                    }
                    llProgressbar.setVisibility(View.GONE);
                    btnLoadMore.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                }
                if (progress == -1) {
                    if (mProgressDialog != null) {
                        Toast.makeText(getApplicationContext(), "Internet konekcija je ugašena", Toast.LENGTH_SHORT).show();
                        mProgressDialog.setProgress(progress);
                        mProgressDialog.dismiss();
                    }
                }
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
                Log.d(Util.TAG, query);
                mAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(Util.TAG, newText);
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
        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
