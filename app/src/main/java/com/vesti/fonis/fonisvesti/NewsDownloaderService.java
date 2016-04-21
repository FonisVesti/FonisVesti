package com.vesti.fonis.fonisvesti;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.vesti.fonis.fonisvesti.model.News;
import com.vesti.fonis.fonisvesti.model.OnePieceOfNews;
import com.vesti.fonis.fonisvesti.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Sarma on 4/18/2016.
 */
public class NewsDownloaderService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    private static int mDownloadedPages = 0;
    public static final int NEWS_ACTIVITY_CALLER=0;
    public static final int NEWS_VIEW_ACTIVITY_CALLER=1;
    public static final int IMAGE_CALLER=2;
    private HttpURLConnection connection = null;
    private String text = null;
    private BufferedReader in = null;
    private URL url;

    public NewsDownloaderService() {
        super("NewsDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getExtras().getInt("caller")){
            case NEWS_ACTIVITY_CALLER:{
                downloadNews(intent);
                break;
            }
            case NEWS_VIEW_ACTIVITY_CALLER:{
                downloadOnePieceOfNews(intent);
                break;
            }
            case IMAGE_CALLER:{
               // downloadImage("");
                break;
            }
        }

    }

    private void downloadOnePieceOfNews(Intent intent){
        int id=intent.getExtras().getInt("onePieceOfNewsId");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        try {
            url=new URL(OnePieceOfNews.ONE_PIECE_OF_NEWS_URL+id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String textJSON=downloadJSON(url);
        setText(id,textJSON);

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }
    private void downloadNews(Intent intent){
        int[] pageNumber = intent.getExtras().getIntArray("pageNumber");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");


        for (int i = 0; i < pageNumber.length; i++) {
            // TODO - add error feedback to user
            try {
                url = new URL(News.NEWS_URL + pageNumber[i]);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            //Downloading news and making news objects
            // TODO - list of news is now only in memory.. cache it later

            String textJSON = downloadJSON(url);
            makeTheNews(textJSON);

            //       News.demoNews(this);
        }
        for (int i = 0; i < News.newsList.size(); i++) {
            Log.d(Util.TAG, "Vest " + i + ":" + News.newsList.get(i).toString());
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }
    private String downloadJSON(URL url) {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            text = "";
            boolean procitano = false;
            while (!procitano) {
                String red = in.readLine();
                procitano = (red == null) ? true : false;
                text += red;
            }
            return text;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    private void makeTheNews(String tekstJSON) {
        try {
            if (tekstJSON == null) return;
            JSONObject sadrzajStrane = new JSONObject(tekstJSON);
            JSONArray vesti = sadrzajStrane.getJSONArray("posts");
            for (int i = 0; i < vesti.length(); i++) {
                JSONObject vest = vesti.getJSONObject(i);
                int id = vest.getInt("id");
                String title = vest.getString("title");
                String textHTML = vest.getString("content");
                GregorianCalendar date = createDate(vest.getString("date"));
                Bitmap image=null;
                if(vest.has("thumbnail_images")) {
                    JSONObject thumbnailJSON = vest.getJSONObject("thumbnail_images");
                    String imageURL = thumbnailJSON.getJSONObject("thumbnail").getString("url");
                    if (imageURL != null) {
                        image = downloadImage(imageURL);
                    }
                }
                OnePieceOfNews v = new OnePieceOfNews(id, title, date, textHTML,image);
                if (!News.newsList.contains(v))
                    News.newsList.add(v);
            }
            mDownloadedPages++;
            Log.d(Util.TAG,"makeTheNews: page downloaded: "+mDownloadedPages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Bitmap downloadImage(String URL){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        return imageLoader.loadImageSync(URL);

    }

    private void setText(int id, String textJSON){
        try {
            JSONObject postJSON=new JSONObject(textJSON).getJSONObject("post");
            OnePieceOfNews post=News.findOnePieceOfNewsByID(id);
            post.setTextHTML(postJSON.getString("content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private GregorianCalendar createDate(String datumS) {
        int godina = Integer.parseInt(datumS.substring(0, 4));
        int mesec = Integer.parseInt(datumS.substring(5, 7)) - 1;
        int dan = Integer.parseInt(datumS.substring(8, 10));
        int sat = Integer.parseInt(datumS.substring(11, 13));
        int minut = Integer.parseInt(datumS.substring(14, 16));
        int sekund = Integer.parseInt(datumS.substring(17, 19));
        return new GregorianCalendar(godina, mesec, dan, sat, minut, sekund);
    }



    @Override
    public void onDestroy() {
        Log.d(Util.TAG, "NewsDownloaderService is stopped.");
        super.onDestroy();

    }
}