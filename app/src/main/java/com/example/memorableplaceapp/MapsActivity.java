package com.example.memorableplaceapp;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.memorableplaceapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ActivityMapsBinding binding;
    ImageButton imgMapTypeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imgMapTypeBtn=findViewById(R.id.imgMapTypeBtn);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng indiaLatLang = new LatLng(20.5937,78.9629);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indiaLatLang));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indiaLatLang,4f)); // this line is to give close view of the mapFragment


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {



                Intent intent= new Intent();

                mMap.addMarker(new MarkerOptions().position(latLng).title("Placed here"));

                Geocoder geocoder= new Geocoder(MapsActivity.this);

                try {
                    ArrayList<Address> addr= (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    String city = addr.get(0).getLocality();
                    String postalCode = addr.get(0).getPostalCode();
                    intent.putExtra("add",addr.get(0).getAddressLine(0));
                    intent.putExtra("latLang",latLng);

                    mMap.addMarker(new MarkerOptions().position(latLng).title(postalCode).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                    setResult(RESULT_OK, intent);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



                new Timer().schedule(
                        new TimerTask(){

                            @Override
                            public void run(){

                                finish();
                            }

                        }, 500);




            }

        });

        Intent intent = getIntent();
        if(intent != null){
            LatLng locationMarked= intent.getParcelableExtra("location");

            if (locationMarked!=null){
                mMap.addMarker(new MarkerOptions().position(locationMarked).title("Placed here"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMarked,16f));

            }
            else {
                // Not written as nothing is needed here

            }


        }
        else{
            Toast.makeText(MapsActivity.this, "Intent is null", Toast.LENGTH_SHORT).show();
        }




    }

    public void mapTypes(View view) {

        Dialog dialog= new Dialog(MapsActivity.this);
        dialog.setContentView(R.layout.dialog_box_view);


        LinearLayout defaultBtn=  dialog.findViewById(R.id.defaultBtn);
        LinearLayout satelliteBtn= dialog.findViewById(R.id.satelliteBtn);
        LinearLayout terrainBtn= dialog.findViewById(R.id.terrainBtn);

        defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                dialog.dismiss();




            }
        });
        satelliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                dialog.dismiss();




            }
        });
        terrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                dialog.dismiss();




            }
        });



        dialog.show();



    }
}