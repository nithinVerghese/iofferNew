package com.accentrs.iofferbh.viewholder.offerdetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class CompanyOfferImageListViewHolder extends MainViewHolder {



    public RecyclerView rvOfferImage;
    public CompanyOfferImageListViewHolder(View itemView) {
        super(itemView);
        rvOfferImage = itemView.findViewById(R.id.rv_offer_image);

    }
}
