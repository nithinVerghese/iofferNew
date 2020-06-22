package com.accentrs.iofferbh.viewholder.home;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class HomeCompanyViewHolder extends MainViewHolder {

    public RecyclerView rvHomeCompany;

    public HomeCompanyViewHolder(View itemView) {
        super(itemView);
        rvHomeCompany = itemView.findViewById(R.id.rv_home_company);

    }
}
