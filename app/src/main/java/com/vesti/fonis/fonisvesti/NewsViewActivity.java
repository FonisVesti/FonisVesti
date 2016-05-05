package com.vesti.fonis.fonisvesti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.utils.Util;

import java.text.SimpleDateFormat;

/**
 * Created by Sarma on 4/19/2016.
 */
public class NewsViewActivity extends BaseActivity {
    private int mNewsId;
    private ImageView imNewsImage;
    private TextView tvNewsTitle, tvNewsDate, tvNewsText;
    private ProgressDialog mProgressDialog;
    private OnePieceOfNews news;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_news_view);

        if (getIntent().hasExtra("newsId")) {
            mNewsId = getIntent().getExtras().getInt("newsId");
        }

        imNewsImage = (ImageView) findViewById(R.id.imNewsImage);
        tvNewsDate = (TextView) findViewById(R.id.tvNewsDate);
        tvNewsText = (TextView) findViewById(R.id.tvNewsText);
        tvNewsTitle = (TextView) findViewById(R.id.tvNewsTitle);
        mImageLoader = Util.getImageLoader(this);

        //  news = News.currentList.get(mNewsId);
        news = News.findOnePieceOfNewsByID(mNewsId);
        tvNewsDate.setText(new SimpleDateFormat("dd.MM.yyyy.").format(news.getDate().getTime()).toString());
        tvNewsTitle.setText(news.getTitle());


        tvNewsText.setMovementMethod(LinkMovementMethod.getInstance());
        mImageLoader.displayImage(news.getImageUrl(), imNewsImage);

        String text = news.getText();
        if (text.toLowerCase().endsWith("read more")) {
            mProgressDialog = ProgressDialog.show(this, null, "Učitavanje vesti..", true, true);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent downloadIntent = new Intent(NewsViewActivity.this, NewsDownloaderService.class);
                    stopService(downloadIntent);
                }
            });
            Intent downloadIntent = new Intent(this, NewsDownloaderService.class);
            downloadIntent.putExtra("onePieceOfNewsId", news.getId());
            downloadIntent.putExtra("receiver", new OnePieceOfNewsDownloadReceiver(new Handler()));
            downloadIntent.putExtra("caller", NewsDownloaderService.NEWS_VIEW_ACTIVITY_CALLER);
            startService(downloadIntent);
        }
        tvNewsText.setText(Html.fromHtml(news.getTextHTML()));

    }

    @SuppressLint("ParcelCreator")
    private class OnePieceOfNewsDownloadReceiver extends ResultReceiver {
        public OnePieceOfNewsDownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == NewsDownloaderService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                if (progress == 0) {
                    mProgressDialog.setProgress(progress);
                    mProgressDialog.dismiss();
                    tvNewsText.setText(Html.fromHtml(news.getTextHTML()));
                } else if (progress == -1) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Internet konekcija je ugašena", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
