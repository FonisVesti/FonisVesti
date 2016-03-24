package com.vesti.fonis.fonisvesti;

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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dusan on 19.3.2016..
 */
public abstract class Vesti {
    public static List<Vest> vestiLista=new ArrayList<>();
    public static void svuciVesti(){
        try {
            new JSON().execute(new URL("https://epos-dulerad94.c9users.io/api/get_posts/?v=8cee5050eeb7"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static class JSON extends AsyncTask<URL,String, Void>{
        HttpURLConnection konekcija=null;
        String tekst=null;
        BufferedReader in=null;
        @Override
        protected Void doInBackground(URL... params) {
            String tekstJSON=procitajVesti(params[0]);
            napraviVesti(tekstJSON);
            for (Vest v:vestiLista
                    ) {
                Log.d("duka",v.toString());
            }
            return null;
        }
        private String procitajVesti(URL url){
            try {

                konekcija=(HttpURLConnection) url.openConnection();
                konekcija.connect();
                in=new BufferedReader(new InputStreamReader(konekcija.getInputStream()));
                tekst="";
                boolean procitano=false;
                while(!procitano){
                    String red=in.readLine();
                    procitano=(red==null)?true:false;
                    tekst+=red;
                }
                return tekst;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(konekcija!=null)
                    konekcija.disconnect();
                try {
                    if(in!=null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tekst;
        }
        private void napraviVesti(String tekstJSON){
            try {
                if(tekstJSON==null)return;
                JSONArray vesti=new JSONObject(tekstJSON).getJSONArray("posts");
                for(int i=0;i<vesti.length();i++){
                    JSONObject vest=vesti.getJSONObject(i);
                    int id=vest.getInt("id");
                    String naslov=vest.getString("title");
                    String tekstHTML=vest.getString("content");
                    GregorianCalendar datum=napraviDatum(vest.getString("date"));
                    String url=vest.getString("url");
                    LinkedList<String> kategorije=izvuciKategorije(vest.getJSONArray("categories"));
                    Vest v=new Vest(id,naslov,datum,tekstHTML,kategorije,url);
                    vestiLista.add(v);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private GregorianCalendar napraviDatum(String datumS){
            int godina=Integer.parseInt(datumS.substring(0, 4));
            int mesec=Integer.parseInt(datumS.substring(5, 7))-1;
            int dan=Integer.parseInt(datumS.substring(8, 10));
            int sat=Integer.parseInt(datumS.substring(11,13));
            int minut=Integer.parseInt(datumS.substring(14,16));
            int sekund=Integer.parseInt(datumS.substring(17,19));
            return new GregorianCalendar(godina,mesec,dan,sat,minut,sekund);
        }
        private LinkedList<String> izvuciKategorije(JSONArray kategorijeJSON){
            LinkedList<String> kategorije=new LinkedList<>();
            for(int j=0;j<kategorijeJSON.length();j++){
                try {
                    JSONObject kategorijaJSON=kategorijeJSON.getJSONObject(j);
                    String kategorija=kategorijaJSON.getString("title");
                    kategorije.add(kategorija);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return kategorije;
        }
    }

}