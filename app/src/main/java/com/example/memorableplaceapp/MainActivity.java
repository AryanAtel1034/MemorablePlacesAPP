package com.example.memorableplaceapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageButton imgMapIntentBtn;
    RecyclerView recyclerview;
    LinearLayout recyclerPlaceMarker;
    TextView temp;
    private String Name;
    private ArrayList<LatLng> latLang= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(MainActivity.this,"Click on dialog button to add places",Toast.LENGTH_SHORT).show();
        ArrayList<recyclerView_struct>addressInfo =new ArrayList<>();

        imgMapIntentBtn=findViewById(R.id.imgMapIntentBtn);
        temp=findViewById(R.id.temp);
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));


        ActivityResultLauncher<Intent> activityResultLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        Intent data = result.getData();
                        int Result = result.getResultCode();


                        if (Result==RESULT_OK){
                             Name = data.getStringExtra("add");
                             latLang.add(data.getParcelableExtra("latLang"));
                            addressInfo.add(new recyclerView_struct(Name));
                            recyclerView_Adapter adapter= new recyclerView_Adapter(getApplicationContext() , addressInfo ,latLang);
                            recyclerview.setAdapter(adapter);



//                            Log.d("add",Name);



                        }

                    }
                }
        );


        imgMapIntentBtn.setOnClickListener(new View.OnClickListener() {
            Intent intent= new Intent(MainActivity.this, MapsActivity.class);
            @Override


            public void onClick(View v) {

                new Timer().schedule(
                        new TimerTask(){

                            @Override
                            public void run(){

                                temp.setText("ADDED PLACES");
                            }

                        }, 1000);


                activityResultLauncher.launch(intent);

            }
        });






    }
}