package com.accentrs.iofferbh.viewholder.company;

import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class CompanyOfferViewHolder extends MainViewHolder {

    public TextView ivCompanyName;
    public ImageView ivCompanyLogo;
    public ImageView ivCompanyOffer;
    public ImageView ivShareOffer;
    public ImageView ivWishlistOffer;
    public TextView  tvOfferViews;
    public TextView tvOfferValidity;
    public TextView tvOfferDescription;
    public TextView tvOfferValidityDays;

    public RelativeLayout rlOfferImagePlaceHolder;

    public CardView cardOfferView;

    public RelativeLayout rlUserViewedOffer;

    public RelativeLayout rlOfferPlaceHolder;
    public RelativeLayout rlOfferView;

    public ImageView ivOfferListWishlist;
    public ImageView ivOfferListShare;


    public CompanyOfferViewHolder(View itemView) {
        super(itemView);
        ivCompanyName = itemView.findViewById(R.id.tv_compnay_name);
        ivCompanyLogo = itemView.findViewById(R.id.iv_company);
        ivCompanyOffer = itemView.findViewById(R.id.iv_company_offer);
        ivShareOffer = itemView.findViewById(R.id.iv_offer_share);
        ivWishlistOffer = itemView.findViewById(R.id.iv_offer_wislist);
        tvOfferViews = itemView.findViewById(R.id.tv_company_offer_views);
        tvOfferValidity = itemView.findViewById(R.id.tv_offer_validity);
        tvOfferDescription = itemView.findViewById(R.id.tv_company_offer_desc);
        tvOfferValidityDays = itemView.findViewById(R.id.tv_offer_validity_days_left);
        cardOfferView = itemView.findViewById(R.id.card_offer_view);
        rlOfferView = itemView.findViewById(R.id.rl_offer_view);
        rlUserViewedOffer = itemView.findViewById(R.id.rl_user_viewed_offer);

        rlOfferImagePlaceHolder = itemView.findViewById(R.id.rl_company_offer_image_placeholder);

        rlOfferPlaceHolder = itemView.findViewById(R.id.rl_offer_placeholder);

        ivOfferListWishlist = itemView.findViewById(R.id.iv_offer_wishlist);
        ivOfferListShare = itemView.findViewById(R.id.iv_offer_list_share);



    }
}
