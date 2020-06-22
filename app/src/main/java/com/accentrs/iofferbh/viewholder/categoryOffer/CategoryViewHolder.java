package com.accentrs.iofferbh.viewholder.categoryOffer;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class CategoryViewHolder extends MainViewHolder {

    public RecyclerView rvCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        rvCategory = itemView.findViewById(R.id.rv_category_offer);
    }
}
