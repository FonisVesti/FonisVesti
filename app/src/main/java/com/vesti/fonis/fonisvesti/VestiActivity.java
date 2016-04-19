package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.vesti.fonis.fonisvesti.adapter.ListViewAdapter;


/**
 * Created by Dusan on 24.3.2016..
 */
public class VestiActivity extends BaseActivity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private Button btnLoadMore;

    private int brojStrane;
    private News[] searchResults;

    // Flag for current page
    int mCurrentPage = 1;

    private ProgressDialog mProgressDialog;
    private LinearLayout llProgressbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vesti);

        // TODO - handle events when user press back and close the dialog
        mProgressDialog = ProgressDialog.show(this, null, "Učitavanje vesti..", true, true);

        // Fire the downloader
        downloadNews(new int[]{mCurrentPage, ++mCurrentPage});

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent downloadIntent = new Intent(VestiActivity.this, VestiDownloaderService.class);
                stopService(downloadIntent);
            }
        });

        // Get the intent, verify the action and get the query
        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
       //     searchResults = searchNews(query);
        }

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
            }
        });
    }



    private void downloadNews(int[] pages) {
        Intent downloadIntent = new Intent(this, VestiDownloaderService.class);
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
            if (resultCode == VestiDownloaderService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                mProgressDialog.setProgress(progress);
                mProgressDialog.dismiss();
                llProgressbar.setVisibility(View.GONE);
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

}
