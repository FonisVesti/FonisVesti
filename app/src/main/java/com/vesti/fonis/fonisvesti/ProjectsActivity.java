package com.vesti.fonis.fonisvesti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.vesti.fonis.fonisvesti.adapter.ProjectsListAdapter;
import com.vesti.fonis.fonisvesti.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dusan on 24.3.2016..
 */
public class ProjectsActivity extends BaseActivity {

    private ExpandableListView mListView;
    private ProjectsListAdapter mAdapter;
    private List<Project> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_projects);

        // Add projects here
        list.add(new Project("","",null));
        list.add(new Project("Start!", "Udruženje studenata informatike FONIS već četvrtu godinu zaredom uspešno organizuje projekat Start. Cilj projekta i motiv organizatora da svake godine unapređuju Start je uočena potreba i želja studenata informacionih tehnologija da svoje učenje i razvoj prošire van granica amfiteatara fakulteta. Svesni smo neverovatne dinamičnosti i neprekidnog napretka oblasti informacionih tehnologija i sve veće potrebe tržišta za visokokvalifikovanim kadrovima. Start upravo predstavlja priliku entuzijastima u oblasti programiranja da prošire svoje znanje u najatraktivnijim tehnologijama, oprobaju se u praktičnim problemima i bolje se pripreme za buduće zaposlenje ili preduzetnički poduhvat. Zahvaljujući šansama koje pruža studentima iz godine u godinu, Start je projekat koji je izuzetno posećen i pozitivno ocenjen. Pozivamo sve studente da učestvuju u projektu i ove godine i pokažu svoju proaktivnost, znanje i kvalitet!", createImage(R.drawable.fonis)));
        list.add(new Project("Studenti studentima", "“Studenti Studentima” je tradicionalni projekat Udruženja koji podrazumeva organizovanje i održavanje niza interaktivnih kurseva koje drže studenti FON-a i drugih fakulteta na različite teme iz oblasti informacionih sistema i tehnologija. Tokom niza godina izvođenja projekta, studentima FON-a omogućeno je da učenjem od svojih kolega dobiju znanja o upotrebi raznih softverskih paketa, operativnih sistema i modernih tehnologija. Cilj projekta „Studenti studentima“ je održavanje interaktivnih kurseva iz oblasti primenjenih tehnologija, kao i iz gradiva smera Informacioni sistemi (programski jezici, informatička pismenost, softverski paketi za web dizajn, grafičku obradu slike i zvuka).\n" +
                "\n" +
                "U okviru projekta „Studenti studentima“, u saradnji sa fakultetom, studentima i kompanijama, FONIS je od svog osnivanja organizovao niz uspešnih kurseva, među kojima su Linux, PHP, Dreamweaver, Corel Draw, Photoshop, Flash, 3ds max i mnogi drugi, koje je pohađalo na stotine zadovoljnih studenata. FONIS će i u budućnosti nastaviti da pruža svojim članovima usavršavanje organizovanjem različitih kvalitetnih kurseva u zavisnosti od interesovanja. Pored toga, FONIS će redovno ispitivati interesovanje studenata za određenim kursevima i na taj način planirati buduće aktivnosti.\n" +
                "\n" +
                "Do sada su održani kursevi na čak nekoliko desetina tema. Java, Java advanced , JAVA II – napredno programiranje, Flash, Flash Advanced, C# , ECDL , Photoshop CS4, Linux, Osnovni HTML 1, Napredni HTML, MySQL, PHP, Joomla, Flash CS5, Photoshop CS5, HTML i CSS, Mercurial, Haskell, WordPress, Haskell – Funkcionalno programiranje, phpBB Forum, Osnove LINQ-a, Excel-advanced, ADOBE PREMIER PRO , PHP I MySQL, Symfony, Osnovi jezika C++ i algoritama, Google AdWords – Search Engine Marketing, Uvod u C# – Windows forms application, Algoritmi – dizajn i analiza, Symfony framework, Node.js, Ember.js, C, razvoj igrica u C#-u, Osnove Android platforme.", createImage(R.drawable.fonis)));

        list.add(new Project("Safety Code Hakaton", "Safety Code hakaton je pokrenut kao incijativa Krovne organizacije mladih Srbije  i biće organizovan u saradnji sa Udruženjem studenata informatike FONIS i finansiran od strane Ministarstva omladine i sporta.\n" +
                "\n" +
                "Cilj samog takmičenja jeste izrada aplikacije koja će pružiti pomoć bezbednosti mladih Srbije. Kao oblasti od najvećeg bezbednosnog značaja prepoznate su: vanredne situacije, ekološka bezbednost, bezbednost u saobraćaju, bezbednost na internetu, nasilje i zdravlje, stoga će se teme aplikacija ticati upravo ovih oblasti. Ideja je da se učesnici prijave u timovima, kako bi se došlo do najboljeg i najkvalitetnijeg mogućeg rešenja problema za 24 sata. Pravo učešća imaju sve osobe od 15-30 godina.", createImage(R.drawable.fonis)));



        mListView = (ExpandableListView) findViewById(R.id.list);
        mAdapter = new ProjectsListAdapter(this, list);
        mListView.setAdapter(mAdapter);
    }

    private Bitmap createImage(int resId) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        Bitmap b = BitmapFactory.decodeResource(ProjectsActivity.this.getResources(), resId, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int targetW = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, ProjectsActivity.this.getResources().getDisplayMetrics());
        int targetH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, ProjectsActivity.this.getResources().getDisplayMetrics());

        // Determine how much to scale down the image
        int scaleFactor = 1;
        while ((photoW / scaleFactor) > targetW
                && (photoH / scaleFactor) > targetH) {
            scaleFactor *= 2;
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeResource(ProjectsActivity.this.getResources(), resId, bmOptions);
    }
}
