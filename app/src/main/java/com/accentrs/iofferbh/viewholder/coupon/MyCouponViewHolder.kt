package com.accentrs.iofferbh.viewholder.coupon

import androidx.cardview.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class MyCouponViewHolder(itemView: View) : MainViewHolder(itemView) {

    var tvMyCouponLeft: TextView
    var ivMyCouponLogo: ImageView
    var tvMyCouponTitle: TextView
    var llMyCoupon: LinearLayout
    var cvMyCoupon: CardView
    var tv_my_coupon_exp: TextView

    init {
        tvMyCouponLeft = itemView.findViewById(R.id.tv_my_coupon_left)
        ivMyCouponLogo = itemView.findViewById(R.id.iv_my_coupon_logo)
        tvMyCouponTitle = itemView.findViewById(R.id.tv_my_coupon_title)
        llMyCoupon = itemView.findViewById(R.id.ll_my_coupon)
        cvMyCoupon = itemView.findViewById(R.id.cv_my_coupon)
        tv_my_coupon_exp = itemView.findViewById(R.id.tv_my_coupon_exp)

    }
}
