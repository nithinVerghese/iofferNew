package com.accentrs.iofferbh.viewholder.delivery

import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class DeliveryStoreViewHolder(itemView: View) : MainViewHolder(itemView) {

    var tvDeliveryStoreTitle: TextView
    var ivDeliverytSoreLogo: ImageView
    var llDeliveryStore: LinearLayout
    var cvDeliveryStore: CardView


    init {
        ivDeliverytSoreLogo = itemView.findViewById(R.id.iv_delivery_store_logo)
        tvDeliveryStoreTitle = itemView.findViewById(R.id.tv_delivery_store_title)
        llDeliveryStore = itemView.findViewById(R.id.ll_delivery_store)
        cvDeliveryStore = itemView.findViewById(R.id.cv_delivery_store)
    }
}
