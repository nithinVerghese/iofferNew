package com.accentrs.iofferbh.viewholder.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class HomeCategoryViewHolder extends MainViewHolder {

    public RecyclerView rvHomeCategory;
    public TextView tvHomeCategoryName;

    public HomeCategoryViewHolder(View itemView) {
        super(itemView);
        rvHomeCategory = itemView.findViewById(R.id.rv_home_category);
        tvHomeCategoryName = itemView.findViewById(R.id.tv_offer_category);

    }
}
