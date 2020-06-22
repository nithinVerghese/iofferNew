package com.accentrs.iofferbh.viewholder.home;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class HomeSliderViewHolder extends MainViewHolder {

    public ImageView ivHomeLatestSlider;
    public ImageView ivHomeCompanySlider;
    public CardView cardLatestSlider;

    public HomeSliderViewHolder(View itemView) {
        super(itemView);
        ivHomeLatestSlider = itemView.findViewById(R.id.iv_latest_offer);
        ivHomeCompanySlider = itemView.findViewById(R.id.iv_company_image);
        cardLatestSlider =itemView.findViewById(R.id.card_video_view);
    }
}
