package com.accentrs.iofferbh.adapter.location;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.model.companydetail.LocationsItem;
import com.accentrs.iofferbh.viewholder.location.LocationViewHolder;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private Context mContext;
    private ArrayList<LocationsItem> locationsItemArrayList;
    private View view;

    public LocationAdapter(Context mContext, ArrayList<LocationsItem> locationsItemArrayList) {
        this.mContext = mContext;
        this.locationsItemArrayList = locationsItemArrayList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location_layout,parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocationViewHolder holder, int position) {
            holder.tvLocationAddress.setText(locationsItemArrayList.get(position).getAddress());
            holder.tvLocationName.setText(locationsItemArrayList.get(position).getNameEn());
            holder.cvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callMapIntent(locationsItemArrayList.get(holder.getAdapterPosition()));
                }
            });
    }

    @Override
    public int getItemCount() {
        if (locationsItemArrayList != null)
            return locationsItemArrayList.size();
        else
            return 0;
    }


    private void callMapIntent(LocationsItem locationsItem){

        String locationLat = locationsItem.getLat();
        String locationLng = locationsItem.getLng();

        String strUri = "http://maps.google.com/maps?q=loc:" + locationLat + "," + locationLng + " (" + locationsItem.getNameEn()+" " + locationsItem.getAddress() + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setPackage("com.google.android.apps.maps");
        mContext.startActivity(intent);
    }








}
