package com.accentrs.iofferbh.viewholder.offerdetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;


public class CompanyDetailHeaderViewHolder extends MainViewHolder {

    public ImageView ivCompanyBanner;
    public ImageView ivCompany;

    public LinearLayout llCompanyCall;
    public LinearLayout llCompanyLocation;
    public LinearLayout llCompanyWebsite;
    public LinearLayout llCompanyWishlist;

    public TextView tvOfferDescription;
    public TextView tvOfferDescriptionAr;
    public TextView tvOfferValidity;

    public ImageView ivBookmark;

    public ImageView ivCompanyLogo;

    public CompanyDetailHeaderViewHolder(View itemView) {
        super(itemView);
        ivCompanyBanner = itemView.findViewById(R.id.ic_company_banner);
        ivCompany = itemView.findViewById(R.id.iv_company_image);
        llCompanyCall = itemView.findViewById(R.id.ll_company_call);
        llCompanyLocation = itemView.findViewById(R.id.ll_company_location);
        llCompanyWebsite = itemView.findViewById(R.id.ll_company_website);
        llCompanyWishlist = itemView.findViewById(R.id.ll_offer_wishlist);
        ivBookmark = itemView.findViewById(R.id.iv_bookmark);
        tvOfferDescription = itemView.findViewById(R.id.tv_offer_description);
        tvOfferDescriptionAr = itemView.findViewById(R.id.tv_offer_description_ar);
        tvOfferValidity = itemView.findViewById(R.id.tv_offer_validity);

        ivCompanyLogo = itemView.findViewById(R.id.iv_company_logo);

    }
}
