package com.vesti.fonis.fonisvesti.model;

import android.graphics.Bitmap;

/**
 * Created by Dusan on 27.4.2016..
 */
public class Project {
    private String name;
    private String text;
    private Bitmap img;
    public Project(String name, String text, Bitmap img){
        this.name=name;
        this.text=text;
        this.img=img;
    }
    public String getName() {
        return name;
    }



    public String getText() {
        return text;
    }



    public Bitmap getImg() {
        return img;
    }
}
