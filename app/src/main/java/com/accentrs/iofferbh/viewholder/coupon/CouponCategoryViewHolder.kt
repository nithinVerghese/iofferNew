package com.accentrs.iofferbh.viewholder.coupon

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class CouponCategoryViewHolder(itemView: View) : MainViewHolder(itemView) {

    var ivCouponCategoryImage: ImageView
    var tvCouponCategoryTitle: TextView



    init {
        ivCouponCategoryImage = itemView.findViewById(R.id.iv_coupon_category_image)
        tvCouponCategoryTitle = itemView.findViewById(R.id.tv_coupon_category_title)
    }
}
