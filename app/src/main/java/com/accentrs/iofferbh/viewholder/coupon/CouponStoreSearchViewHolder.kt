package com.accentrs.iofferbh.viewholder.coupon//CouponStoreSearchViewHolder


import androidx.cardview.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class CouponStoreSearchViewHolder(itemView: View) : MainViewHolder(itemView) {

    var ivCouponStoreLogo: ImageView
    var tvCouponStoreTitle: TextView
    var llCouponStore: LinearLayout
    var cvCouponStore: CardView


    init {
        ivCouponStoreLogo = itemView.findViewById(R.id.iv_coupon_store_logo)
        tvCouponStoreTitle = itemView.findViewById(R.id.tv_coupon_store_title)

        llCouponStore = itemView.findViewById(R.id.ll_coupon_store)
        cvCouponStore = itemView.findViewById(R.id.cv_coupon_store)
    }
}


