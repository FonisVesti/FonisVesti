package com.vesti.fonis.fonisvesti;

import android.media.Image;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Dusan on 23.2.2016..
 */
public class Vest {
    private int id;
    private String naslov;
    private GregorianCalendar datum;
    private String tekstHTML;
    private LinkedList<String> kategorije;
    private Slika glavnaSlika;
    private LinkedList<Slika> slike;
    private String url;
    private String tekst;

    void parsiraj(){
        Document d= Jsoup.parse(tekstHTML);
        tekst=d.text();
    }

    public Vest(int id, String naslov, GregorianCalendar datum, String tekstHTML, LinkedList<String> kategorije,String url) {
        this.id = id;
        this.naslov = naslov;
        this.datum = datum;
        this.tekstHTML = tekstHTML;
        this.url=url;
        this.kategorije = kategorije;
        slike=new LinkedList<>();
        parsiraj();
        izvadiSlike();
        glavnaSlika=slike.get(0);
    }

    public int getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public GregorianCalendar getDatum() {
        return datum;
    }

    public String getTekstHTML() {
        return tekstHTML;
    }

    public LinkedList<String> getKategorije() {
        return kategorije;
    }

    public boolean equals(Object o){
        if (!(o instanceof Vest))return false;
        Vest v=(Vest) o;
        if(v.getId()!=id) return false;
        return true;
    }
    private void izvadiSlike(){
        while(tekstHTML.contains("<img")){
            String slika=tekstHTML.substring(tekstHTML.indexOf("<img"),tekstHTML.indexOf(" />")+3);
            String src=slika.substring(slika.indexOf("src=")+5,slika.indexOf("alt")-2);
            String alt=slika.substring(slika.indexOf("alt=")+5,slika.indexOf("width")-2);
            Slika s=new Slika(src,alt);
            slike.add(s);
            Log.d("vesti",slika);
            Log.d("vesti",src);
            tekstHTML=tekstHTML.substring(0,tekstHTML.indexOf("<img"))+tekstHTML.substring(tekstHTML.indexOf(" />") + 3);
        }
    }

    @Override
    public String toString() {
        String kategorijeS="";
        for (int i=0;i<kategorije.size();i++){
            kategorijeS+=kategorije.get(i)+" ";
        }
        return naslov +" "+tekst+" "+datum.getTime()+" "+url+";"+kategorije;
    }
}

