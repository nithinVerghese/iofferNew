package com.accentrs.iofferbh.viewholder.coupon

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class CouponRedeemViewHolder(itemView: View) : MainViewHolder(itemView) {

    var ivCouponRedeemImage: ImageView
    var llCouponRedeem: LinearLayout

    init {
        ivCouponRedeemImage = itemView.findViewById(R.id.iv_coupon_redeem_image)
        llCouponRedeem = itemView.findViewById(R.id.ll_coupon_redeem)
    }
}
