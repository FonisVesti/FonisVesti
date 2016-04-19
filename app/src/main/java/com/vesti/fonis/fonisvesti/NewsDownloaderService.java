package com.vesti.fonis.fonisvesti;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

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
    private HttpURLConnection konekcija = null;
    private String tekst = null;
    private BufferedReader in = null;
    private URL url;
    private String pageNumber;
    private ResultReceiver receiver;

    public NewsDownloaderService() {
        super("NewsDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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
            String tekstJSON = procitajVesti(url);
            napraviVesti(tekstJSON);
            News.demoNews();
        }
        for (int i = 0; i < News.newsList.size(); i++) {
            Log.d(Util.TAG, "Vest " + i + ":" + News.newsList.get(i).toString());
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }

    private String procitajVesti(URL url) {
        try {
            konekcija = (HttpURLConnection) url.openConnection();
            konekcija.connect();
            in = new BufferedReader(new InputStreamReader(konekcija.getInputStream()));
            tekst = "";
            boolean procitano = false;
            while (!procitano) {
                String red = in.readLine();
                procitano = (red == null) ? true : false;
                tekst += red;
            }
            return tekst;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (konekcija != null)
                konekcija.disconnect();
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tekst;
    }

    private void napraviVesti(String tekstJSON) {
        try {
            if (tekstJSON == null) return;
            JSONObject sadrzajStrane = new JSONObject(tekstJSON);
            JSONArray vesti = sadrzajStrane.getJSONArray("posts");
            for (int i = 0; i < vesti.length(); i++) {
                JSONObject vest = vesti.getJSONObject(i);
                int id = vest.getInt("id");
                String naslov = vest.getString("title");
                String tekstHTML = vest.getString("content");
                GregorianCalendar datum = napraviDatum(vest.getString("date"));
                String url = vest.getString("url");
                LinkedList<String> kategorije = izvuciKategorije(vest.getJSONArray("categories"));
                OnePieceOfNews v = new OnePieceOfNews(id, naslov, datum, tekstHTML, url);

                // TODO - redefine equals method
                if (!News.newsList.contains(v))
                    News.newsList.add(v);
            }
            mDownloadedPages++;
            Log.d(Util.TAG,"napraviVesti: page downloaded: "+mDownloadedPages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private GregorianCalendar napraviDatum(String datumS) {
        int godina = Integer.parseInt(datumS.substring(0, 4));
        int mesec = Integer.parseInt(datumS.substring(5, 7)) - 1;
        int dan = Integer.parseInt(datumS.substring(8, 10));
        int sat = Integer.parseInt(datumS.substring(11, 13));
        int minut = Integer.parseInt(datumS.substring(14, 16));
        int sekund = Integer.parseInt(datumS.substring(17, 19));
        return new GregorianCalendar(godina, mesec, dan, sat, minut, sekund);
    }

    private LinkedList<String> izvuciKategorije(JSONArray kategorijeJSON) {
        LinkedList<String> kategorije = new LinkedList<>();
        for (int j = 0; j < kategorijeJSON.length(); j++) {
            try {
                JSONObject kategorijaJSON = kategorijeJSON.getJSONObject(j);
                String kategorija = kategorijaJSON.getString("title");
                kategorije.add(kategorija);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return kategorije;
    }

    @Override
    public void onDestroy() {
        Log.d(Util.TAG, "NewsDownloaderService is stopped.");
        super.onDestroy();

    }
}