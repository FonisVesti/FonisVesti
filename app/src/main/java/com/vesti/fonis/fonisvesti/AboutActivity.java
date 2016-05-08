package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Dusan on 24.3.2016..
 */
public class AboutActivity extends BaseActivity implements OnMapReadyCallback{
    private MapFragment googleMap;
    private TextView txtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){}

        setContentView(R.layout.activity_about);
        googleMap=((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        googleMap.getMapAsync(this);
        txtContact=(TextView) findViewById(R.id.textViewMail);

        txtContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //send mail to office@fonis.rs
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                String[] to={"office@fonis.rs"};
                i.putExtra(Intent.EXTRA_EMAIL, to);
                i.setType("message/rfc822");
                Intent chooser=Intent.createChooser(i,"Izaberi aplikaciju:");
                startActivity(chooser);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng FON=new LatLng(44.772764, 20.475129);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(FON).title("FONIS"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(FON));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(FON, 14.0f));

    }
}
