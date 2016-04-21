package com.vesti.fonis.fonisvesti.model;

import android.graphics.Bitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.GregorianCalendar;

/**
 * Created by Dusan on 23.2.2016..
 */
public class OnePieceOfNews {
    private int id;
    private String title;
    private GregorianCalendar date;
    private String textHTML;
    private Bitmap image;


    public Bitmap getImage() {
        return image;
    }


    private String text;
    public static final String ONE_PIECE_OF_NEWS_URL= "http://fonis.rs/api/get_post/?post_id=";

    public void parse(){
        Document d= Jsoup.parse(textHTML);
        text =d.text();
    }

    public void setTextHTML(String textHTML) {
        this.textHTML = textHTML;
    }

    public OnePieceOfNews(int id, String title, GregorianCalendar date, String textHTML, Bitmap image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.textHTML = textHTML;
        this.image =image;
        parse();

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
        return title + " " + text + " " + date.getTime() + " " + ";";
    }

    public boolean isSubstring(String text) {
        return this.text.contains(text) || this.title.contains(text);
    }

}

