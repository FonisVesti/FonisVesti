package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vesti.fonis.fonisvesti.model.News;

/**
 * Created by Dusan on 24.3.2016..
 */
public class MSDNAAActivity extends BaseActivity{
    private Button btnRegAcc, btnRenewAcc;
    private TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_msdnaa);

        btnRegAcc = (Button)findViewById(R.id.btnRegAcc);
        btnRenewAcc = (Button)findViewById(R.id.btnRenewAcc);
        tvIntro = (TextView)findViewById(R.id.tvIntro);

        btnRenewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MSDNAAActivity.this, MSDNAASumbitActivity.class);
                i.putExtra("action","renew");
                startActivity(i);
                finish();
            }
        });

        btnRegAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MSDNAAActivity.this, MSDNAASumbitActivity.class);
                i.putExtra("action","reg");
                startActivity(i);
                finish();
            }
        });
        tvIntro.setMovementMethod(LinkMovementMethod.getInstance());
        tvIntro.setText(Html.fromHtml("Registruj svoj nalog za Microsoft razvojne alate ili obnovi postojeći nalog.\n" +
                "Ako ne uspete da pošaljete svoje podatke, možete nam pisati na <u><a href=\"mailto:msdnaa@fonis.rs\" target=\"_blank\">msdnaa@fonis.rs</a></u>"));
    }
}
