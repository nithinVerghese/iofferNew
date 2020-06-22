package com.accentrs.iofferbh.viewholder.delivery

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.viewholder.MainViewHolder

class DeliveryCategoryViewHolder(itemView: View) : MainViewHolder(itemView) {

    var ivDeliveryCategoryImage: ImageView
    var tvDeliveryCategoryTitle: TextView



    init {
        ivDeliveryCategoryImage = itemView.findViewById(R.id.iv_delivery_category_image)
        tvDeliveryCategoryTitle = itemView.findViewById(R.id.tv_delivery_category_title)
    }
}
