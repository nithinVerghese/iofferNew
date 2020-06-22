package com.accentrs.iofferbh.viewholder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class HomeCompanyOfferHolder extends MainViewHolder {

    public RecyclerView rvHomeCompanyOffer;
    public RecyclerView rvHomeCompanyOfferList;
    public TextView btnGotoTop;


    public HomeCompanyOfferHolder(View itemView) {
        super(itemView);
        rvHomeCompanyOffer = itemView.findViewById(R.id.rv_home_company_offer);
        rvHomeCompanyOfferList = itemView.findViewById(R.id.rv_home_company_offer_list);
        btnGotoTop = itemView.findViewById(R.id.btn_goto_top);
        btnGotoTop.setVisibility(View.GONE);


    }
}
