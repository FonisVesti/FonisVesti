package com.vesti.fonis.fonisvesti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MSDNAASumbitSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_msdnaasumbit_success);

        TextView textView = (TextView)findViewById(R.id.tvMessage);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml("Dragi kolega, email koji ste prosledili će vam ujedno biti i <b>username</b> –" +
                "        <b>Password</b> ćete dobiti u potvrdnom e-mail-u da vam je nalog otvoren, " +
                "        i preporučujemo vam da ga odmah promenite. \nZa sva pitanja," +
                "        nedoumice i probleme nam možete pisati na: <a href=\"mailto:msdnaa@fonis.rs\" target=\"_blank\">msdnaa@fonis.rs</a>;\n" +
                "        Na ovom linku možete videti listu softvera koja vam je dostupna preko MSDNAA naloga:\n" +
                "        <a href=\"http://msdnaa.fonis.rs\" target=\"_blank\">MSDNAA Fonis</a>"));
    }
}
