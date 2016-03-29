package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {

    private static ImageButton ibtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickIbtnVestiListener();
        onClickIbtnONamaListener();
        onClickIbtnProjektiListener();
        onClickIbtnMSDNAAListener();
        //Vesti.svuciVesti(1);
        //int brojVesti=Vesti.svuciBrojVesti();
        //Log.d("vesti", brojVesti + "");
    }

    public void onClickIbtnVestiListener() {
        ibtn = (ImageButton) findViewById(R.id.ibtnVesti);
        ibtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.VestiActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnONamaListener() {
        ibtn = (ImageButton) findViewById(R.id.ibtnONama);
        ibtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.ONamaActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnProjektiListener() {
        ibtn = (ImageButton) findViewById(R.id.ibtnProjekti);
        ibtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.ProjektiActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickIbtnMSDNAAListener() {
        ibtn = (ImageButton) findViewById(R.id.ibtnMSDNAA);
        ibtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.MSDNAAActivity");
                        startActivity(intent);
                    }
                }
        );
    }

    /*
    public void linkovanje (Button button, final String sajt) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(sajt);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    */
}
