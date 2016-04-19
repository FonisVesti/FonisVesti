package com.vesti.fonis.fonisvesti.model;

import android.util.Log;

import com.vesti.fonis.fonisvesti.Slika;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Dusan on 23.2.2016..
 */
public class OnePieceOfNews {
    private int id;
    private String title;
    private GregorianCalendar date;
    private String textHTML;
    private Slika mainImg;
    private LinkedList<Slika> images;
    private String url;
    private String text;

    void parse(){
        Document d= Jsoup.parse(textHTML);
        text =d.text();
    }

    public OnePieceOfNews(int id, String naslov, GregorianCalendar datum, String tekstHTML, String url) {
        this.id = id;
        this.title = naslov;
        this.date = datum;
        this.textHTML = tekstHTML;
        this.url=url;

        images =new LinkedList<>();
        parse();
      //  izvadiSlike();
        if(images.size()>=1)
        mainImg = images.get(0);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getTextHTML() {
        return textHTML;
    }

    public boolean equals(Object o){
        if (!(o instanceof OnePieceOfNews))return false;
        OnePieceOfNews v=(OnePieceOfNews) o;
        if(v.getId()!=id) return false;
        return true;
    }
    private void izvadiSlike(){
        while(textHTML.contains("<img")){
            String slika= textHTML.substring(textHTML.indexOf("<img"), textHTML.indexOf(" />")+3);
            String src=slika.substring(slika.indexOf("src=")+5,slika.indexOf("alt")-2);
            String alt=slika.substring(slika.indexOf("alt=")+5,slika.indexOf("width")-2);
            Slika s=new Slika(src,alt);
            images.add(s);
            Log.d("vesti",slika);
            Log.d("vesti",src);
            textHTML = textHTML.substring(0, textHTML.indexOf("<img"))+ textHTML.substring(textHTML.indexOf(" />") + 3);
        }
    }

    @Override
    public String toString() {
        return title +" "+ text +" "+ date.getTime()+" "+url+";";
    }
    public boolean isSubstring(String text){
        return this.text.contains(text);
    }
}

