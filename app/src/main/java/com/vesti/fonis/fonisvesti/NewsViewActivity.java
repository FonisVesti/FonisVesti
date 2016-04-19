package com.vesti.fonis.fonisvesti;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;

import java.text.SimpleDateFormat;

/**
 * Created by Sarma on 4/19/2016.
 */
public class NewsViewActivity extends BaseActivity {
    private int mNewsPosition;
    private ImageView imNewsImage;
    private TextView tvNewsTitle, tvNewsDate, tvNewsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        if(getIntent().hasExtra("newsPosition")){
            mNewsPosition = getIntent().getExtras().getInt("newsPosition");
        }

        imNewsImage = (ImageView)findViewById(R.id.imNewsImage);
        tvNewsDate = (TextView)findViewById(R.id.tvNewsDate);
        tvNewsText = (TextView)findViewById(R.id.tvNewsText);
        tvNewsTitle = (TextView)findViewById(R.id.tvNewsTitle);

        OnePieceOfNews news = News.newsList.get(mNewsPosition);
        tvNewsDate.setText(new SimpleDateFormat("dd.MM.yyyy.").format(news.getDate().getTime()).toString());
        tvNewsTitle.setText(news.getTitle());
        tvNewsText.setText(news.getTextHTML());

    }
}
