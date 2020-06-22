package com.accentrs.iofferbh.viewholder.coupon

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class CouponDetailViewHolder(itemView: View) : MainViewHolder(itemView) {

    var ivCouponStoreLogo: ImageView
    var tvCouponStoreTitle: TextView
    var llCouponDetailMenu: LinearLayout
    var tvCouponDetailLocation: TextView
    var tvCouponDetailContact: TextView
    var tvCouponDetailWebSite: TextView
    var checkBox: CheckBox
    var term_tv: TextView



    init {
        ivCouponStoreLogo = itemView.findViewById(R.id.iv_coupon_store_logo)
        tvCouponStoreTitle = itemView.findViewById(R.id.tv_coupon_store_title)
        llCouponDetailMenu = itemView.findViewById(R.id.ll_coupon_detail_menu)
        tvCouponDetailLocation = itemView.findViewById(R.id.tv_coupon_category_title)
        tvCouponDetailContact = itemView.findViewById(R.id.tv_coupon_detail_contact)
        tvCouponDetailWebSite = itemView.findViewById(R.id.tv_coupon_detail_website)
        checkBox = itemView.findViewById(R.id.checkBox)
        term_tv = itemView.findViewById(R.id.term_tv)
    }
}
