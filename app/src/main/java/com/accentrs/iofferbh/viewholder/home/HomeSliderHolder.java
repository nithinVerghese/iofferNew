package com.accentrs.iofferbh.viewholder.home;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class HomeSliderHolder extends MainViewHolder {

    //public TextView tvCouponOffer;
    public ImageView ivCategory,ivCoupon,ivMyCoupon,ivDelivery;
    public RecyclerView rvHomeSlider;
    public LinearLayout llOfferParent;
    public TextView tvCategoryOffer;

    public HomeSliderHolder(View itemView) {
        super(itemView);
        rvHomeSlider = itemView.findViewById(R.id.rv_home_slider);
        llOfferParent = itemView.findViewById(R.id.ll_offer_parent);
        //tvCategoryOffer = itemView.findViewById(R.id.tv_category_offers);
        //tvCouponOffer = itemView.findViewById(R.id.tv_coupon_offers);
        ivCategory = itemView.findViewById(R.id.iv_category);
        ivCoupon = itemView.findViewById(R.id.iv_coupon);
        ivMyCoupon =itemView.findViewById(R.id.iv_my_coupon);
        ivDelivery =itemView.findViewById(R.id.iv_delivery);
    }
}
