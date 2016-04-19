package com.vesti.fonis.fonisvesti.model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Dusan on 19.3.2016..
 */
public abstract class News {
    public static List<OnePieceOfNews> newsList =new ArrayList<>();
    public static final String NEWS_URL = "http://fonis.rs/api/get_posts/?page=";

    public static void downloadNews(int pageNumber){
        try {
            new JSONNews().execute(new URL(News.NEWS_URL+ pageNumber));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void demoNews(){
        for(int i = 0;i<10;i++) {
            OnePieceOfNews v = new OnePieceOfNews(i,"Lorem ipsum.",new GregorianCalendar(2016,4,2),"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.","demo");
            newsList.add(v);
        }
    }
    public static ArrayList<OnePieceOfNews> searchNews(String text){
        // removes all extra space between words

        text=text.replaceAll("\\s+", " ");

        ArrayList<OnePieceOfNews> list=new ArrayList<>();
        for (int i=0;i< newsList.size();i++){
            if(newsList.get(i).isSubstring(text))
                list.add(newsList.get(i));
        }
        return list;
    }
    private static class JSONNews extends AsyncTask<URL,String, Void>{
        HttpURLConnection connection =null;
        String text =null;
        BufferedReader in=null;
        @Override
        protected Void doInBackground(URL... params) {
            String tekstJSON= readNews(params[0]);
            makeNews(tekstJSON);
            for (OnePieceOfNews v: newsList
                    ) {
                Log.d("duka",v.toString());
            }
            return null;
        }
        private String readNews(URL url){
            try {

                connection =(HttpURLConnection) url.openConnection();
                connection.connect();
                in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                text ="";
                boolean end=false;
                while(!end){
                    String line=in.readLine();
                    end=(line==null)?true:false;
                    text +=line;
                }
                return text;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection !=null)
                    connection.disconnect();
                try {
                    if(in!=null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return text;
        }
        private void makeNews(String textJSON){
            try {
                if(textJSON==null)return;
                JSONArray newsListJSON=new JSONObject(textJSON).getJSONArray("posts");
                for(int i=0;i<newsListJSON.length();i++){
                    JSONObject OnePieceOfNewsJSON=newsListJSON.getJSONObject(i);
                    int id=OnePieceOfNewsJSON.getInt("id");
                    String title=OnePieceOfNewsJSON.getString("title");
                    String textHTML=OnePieceOfNewsJSON.getString("content");
                    GregorianCalendar date=createDate(OnePieceOfNewsJSON.getString("date"));
                    String url=OnePieceOfNewsJSON.getString("url");
                    OnePieceOfNews v=new OnePieceOfNews(id,title,date,textHTML,url);
                    newsList.add(v);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private GregorianCalendar createDate(String datumS){
            int year=Integer.parseInt(datumS.substring(0, 4));
            int month=Integer.parseInt(datumS.substring(5, 7))-1;
            int day=Integer.parseInt(datumS.substring(8, 10));
            int hour=Integer.parseInt(datumS.substring(11,13));
            int minute=Integer.parseInt(datumS.substring(14,16));
            int second=Integer.parseInt(datumS.substring(17,19));
            return new GregorianCalendar(year,month,day,hour,minute,second);
        }

    }

}