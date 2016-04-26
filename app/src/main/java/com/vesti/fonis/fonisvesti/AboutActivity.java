package com.vesti.fonis.fonisvesti;

import android.content.Intent;
import android.os.Bundle;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about);
        googleMap=((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        googleMap.getMapAsync(this);
        txtContact=(TextView) findViewById(R.id.textViewAboutUs);

//        txtContact.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent i=new
//            }
//        });
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
