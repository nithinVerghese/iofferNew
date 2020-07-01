package com.accentrs.iofferbh.adapter.delivery

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL_DE
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.*
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.coupon.CouponStoreData
import com.accentrs.iofferbh.model.delivery.Store
import com.accentrs.iofferbh.utils.Constants
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.coupon.CouponStoreViewHolder
import com.accentrs.iofferbh.viewholder.delivery.DeliveryStoreViewHolder
import java.io.Serializable
import java.util.*


class DeliveryStoreAdapter(val context: Context, val couponStoreItemList: List<Store>?) : RecyclerView.Adapter<DeliveryStoreViewHolder>() {

    val obj = com.accentrs.apilibrary.utils.Constants()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryStoreViewHolder {

        return DeliveryStoreViewHolder(LayoutInflater.from(context).inflate(com.accentrs.iofferbh.R.layout.row_delivery_store_layout, parent, false))
    }

    override fun onBindViewHolder(holder: DeliveryStoreViewHolder, position: Int) {


        holder.tvDeliveryStoreTitle.text = couponStoreItemList?.get(position)?.getNameEn()




             //holder.available_ccupons.text = "Available ccupons : "+ total
            //holder.exp_dt.text = "123456543"


        holder.cvDeliveryStore.setOnClickListener { v: View ->


            if (press == 0) {

                if (Utils.isConnectedToInternet(context)) {
                    val intent = Intent(context, DeliveryStoreInfoActivity::class.java)
                    intent.putExtra("s_id", couponStoreItemList?.get(position)?.getId().toString())
                    intent.putExtra("s_name", couponStoreItemList?.get(position)?.getShopName())
                    intent.putExtra("s_img", couponStoreItemList?.get(position)?.getLogo())
//                    intent.putExtra("s_web", couponStoreItemList?.get(position)?.s_web)
//                    intent.putExtra("s_menu_img", couponStoreItemList?.get(position)?.s_menu_img)
//                    intent.putExtra("expiry_date", couponStoreItemList?.get(position)?.expiry_date)
//                    intent.putExtra("locations", couponStoreItemList?.get(position)?.locations as Serializable)
//
//
//                    //(context as Activity).finish()
                    context.startActivity(intent)
                } else
                    (context as DeliveryStoreActivity).showToast(context.getString(R.string.error_no_internet_msg))

                press = 1;

            }
        }

        val s_img: String = IMAGE_URL_DE + couponStoreItemList?.get(position)?.getLogo()
        GlideApp.with(context)
                .load(s_img)
                .placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder)
                .into(holder.ivDeliverytSoreLogo)
    }

    override fun getItemCount(): Int {
        return couponStoreItemList!!.size
    }
}



