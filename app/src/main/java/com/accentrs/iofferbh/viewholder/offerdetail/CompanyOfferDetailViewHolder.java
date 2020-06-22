package com.accentrs.iofferbh.viewholder.offerdetail;

import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.viewholder.MainViewHolder;

public class CompanyOfferDetailViewHolder extends MainViewHolder {

    public ImageView ivCompanyOffer;
    public TextView tvOfferValidity;
    public CardView cvOfferDetail;

    public RelativeLayout rlOfferImagePlaceholder;

    public CompanyOfferDetailViewHolder(View itemView) {
        super(itemView);
        ivCompanyOffer = itemView.findViewById(R.id.iv_company_detail_offer);
        tvOfferValidity = itemView.findViewById(R.id.tv_offer_valid);
        cvOfferDetail = itemView.findViewById(R.id.card_offer_view);
        rlOfferImagePlaceholder = itemView.findViewById(R.id.rl_offer_detail_placeholder);
    }
}
