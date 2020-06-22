package com.accentrs.iofferbh.viewholder.location;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class LocationViewHolder extends MainViewHolder {

    public TextView tvLocationName;
    public TextView tvLocationAddress;
    public CardView cvLocation;

    public LocationViewHolder(View itemView) {
        super(itemView);

        tvLocationName = itemView.findViewById(R.id.tv_location_name);
        tvLocationAddress = itemView.findViewById(R.id.tv_location_address);
        cvLocation = itemView.findViewById(R.id.card_location_view);

    }
}
