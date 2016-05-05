package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.vesti.fonis.fonisvesti.adapter.ListViewAdapter;
import com.vesti.fonis.fonisvesti.model.News;

/**
 * Created by Dusan on 23.4.2016..
 */
public class SplashScreen extends BaseActivity {

    private void downloadNews(int[] pages) {
        Intent downloadIntent = new Intent(this, NewsDownloaderService.class);
        downloadIntent.putExtra("pageNumber", pages);
        downloadIntent.putExtra("caller", NewsDownloaderService.SPLASH_SCREEN_CALLER);
        downloadIntent.putExtra("receiver", new NewsDownloadReceiver(new Handler()));
        startService(downloadIntent);
    }

    private Thread splashTread;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);

        // Load first and second page
        downloadNews(new int[]{1, 2});

        final SplashScreen splashScreen = this;
        // thread for displaying the SplashScreen
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2500);
                    }

                } catch (InterruptedException e) {
                } finally {
                    finish();

                    Intent i = new Intent();
                    i.setClass(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }
            }
        };

        splashTread.start();
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
                if(progress==-1){
                        Toast.makeText(getApplicationContext(), "Internet konekcija je ugašena", Toast.LENGTH_SHORT).show();

                }

            }
        }

    }
}

