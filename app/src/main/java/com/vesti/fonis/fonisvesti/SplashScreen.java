package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vesti.fonis.fonisvesti.adapter.ListViewAdapter;
import com.vesti.fonis.fonisvesti.model.News;

/**
 * Created by Dusan on 23.4.2016..
 */
public class SplashScreen extends BaseActivity{
    ListViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);
        downloadNews(new int[]{1, 2});
            mAdapter=new ListViewAdapter(this,News.newsList);
       // mAdapter=new ListViewAdapter(this, News.currentList);
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
                if(progress==0) {
                    Intent i=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(i);
                    mAdapter.notifyDataSetChanged();
                    finish();
                }
                if(progress==1){
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

    }
}
