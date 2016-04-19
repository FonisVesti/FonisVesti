package com.vesti.fonis.fonisvesti.model;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.vesti.fonis.fonisvesti.NewsDownloaderService;

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
    private Image mainImg;
    private LinkedList<Image> images;
    private String url;
    private String text;
    public static final String ONE_PIECE_OF_NEWS_URL= "http://fonis.rs/api/get_post/?post_id=";

    public void parse(){
        Document d= Jsoup.parse(textHTML);
        text =d.text();
    }

    public void setTextHTML(String textHTML) {
        this.textHTML = textHTML;
    }

    public OnePieceOfNews(int id, String naslov, GregorianCalendar datum, String tekstHTML, String url) {
        this.id = id;
        this.title = naslov;
        this.date = datum;
        this.textHTML = tekstHTML;
        this.url = url;

        images = new LinkedList<>();
        parse();
        //  izvadiSlike();
        if (images.size() >= 1)
            mainImg = images.get(0);
    }

    public String getText() {
        return text;
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


    public boolean equals(Object o) {
        if (!(o instanceof OnePieceOfNews)) return false;
        OnePieceOfNews v = (OnePieceOfNews) o;
        if (v.getId() != id) return false;
        return true;
    }

    @Override
    public String toString() {
        return title + " " + text + " " + date.getTime() + " " + url + ";";
    }

    public boolean isSubstring(String text) {
        return this.text.contains(text) || this.title.contains(text);
    }
}

