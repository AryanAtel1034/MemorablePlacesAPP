package com.example.memorableplaceapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class recyclerView_Adapter extends RecyclerView.Adapter<recyclerView_Adapter.ViewHolder> {

    Context context;
    ArrayList<recyclerView_struct> addressInfo;
    ArrayList<LatLng> latLang;


    recyclerView_Adapter(Context context , ArrayList<recyclerView_struct> addressInfo, ArrayList<LatLng> latLang){

        this.context=context;
        this.addressInfo=addressInfo;
        this.latLang=latLang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_view ,parent,false);
        ViewHolder viewHolder= new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtAddres.setText(addressInfo.get(position).add);
        holder.recyclerPlaceMarker.setOnClickListener(new View.OnClickListener() {
            Intent intent= new Intent(context,MapsActivity.class);

            @Override
            public void onClick(View v) {

                intent.putExtra("location", latLang.get(position));

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return addressInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddres;
        LinearLayout recyclerPlaceMarker;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAddres=itemView.findViewById(R.id.txtAddres);
            recyclerPlaceMarker=itemView.findViewById(R.id.recyclerPlaceMarker);


        }
    }
}
