package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends ActionBarActivity {
    private static ImageButton ibtnVesti;
    /**
     * ATTENTION: This was auto-generated to implement the AponClickIbtnVestiListenerp Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  //  private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
   //     client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        onClickIbtnVestiListener();
        onClickIbtnONamaListener();
        onClickIbtnProjektiListener();
        onClickIbtnMSDNAAListener();
////        Button button1 = (Button) findViewById(R.id.button1);
////        Button button2 = (Button) findViewById(R.id.button2);
////        Button button3 = (Button) findViewById(R.id.button3);
////        Button button4 = (Button) findViewById(R.id.button4);
////        Button button5 = (Button) findViewById(R.id.button5);
//        linkovanje(button1, "http://www.fonis.rs");
//        linkovanje(button2, "https://www.facebook.com/fonis.rs");
//        linkovanje(button3, "https://twitter.com/fonis_fon");
//        linkovanje(button4, "https://www.linkedin.com/company/fonis");
//        linkovanje(button5, "https://www.youtube.com/user/FonisFON");
    }

    public void linkovanje (Button button, final String sajt) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(sajt);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickIbtnVestiListener() {
        ibtnVesti = (ImageButton) findViewById(R.id.ibtnVesti);
        ibtnVesti.setOnClickListener(
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
        ibtnVesti = (ImageButton) findViewById(R.id.ibtnONama);
        ibtnVesti.setOnClickListener(
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
        ibtnVesti = (ImageButton) findViewById(R.id.ibtnProjekti);
        ibtnVesti.setOnClickListener(
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
        ibtnVesti = (ImageButton) findViewById(R.id.ibtnMSDNAA);
        ibtnVesti.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.vesti.fonis.fonisvesti.MSDNAAActivity");
                        startActivity(intent);
                    }
                }
        );
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.vesti.fonis.fonisvesti/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.vesti.fonis.fonisvesti/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
}
