package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.CouponDetailActivity
import com.accentrs.iofferbh.activity.CouponStoreActivity
import com.accentrs.iofferbh.activity.convertTime
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.coupon.CouponStoreData
import com.accentrs.iofferbh.utils.Constants
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.coupon.CouponStoreViewHolder
import java.io.Serializable
import java.util.*


class CouponStoreAdapter(val context: Context, val couponStoreItemList: List<CouponStoreData>?) : RecyclerView.Adapter<CouponStoreViewHolder>() {

    val obj = com.accentrs.apilibrary.utils.Constants()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponStoreViewHolder {

        return CouponStoreViewHolder(LayoutInflater.from(context).inflate(com.accentrs.iofferbh.R.layout.row_coupon_store_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CouponStoreViewHolder, position: Int) {


        holder.tvCouponStoreTitle.text = couponStoreItemList?.get(position)?.s_name
        val total = couponStoreItemList?.get(position)?.total_coupon

            if (total != null) {
                if(total > 0){

                    holder.available_ccupons.text = "Coupon Left : "+ total
                    holder.available_ccupons.setTextColor(Color.rgb(0x2e,0x7d,0x32))

                }else{

                    holder.available_ccupons.text = "Sold Out Coupons"
                    holder.available_ccupons.setTextColor(Color.rgb(0xc6,0x28,0x28))

                }
            }

             //holder.available_ccupons.text = "Available ccupons : "+ total
            //holder.exp_dt.text = "123456543"


        holder.cvCouponStore.setOnClickListener { v: View ->





            if (press == 0) {

                if (Utils.isConnectedToInternet(context)) {
                    val intent = Intent(context, CouponDetailActivity::class.java)
                    intent.putExtra("s_id", couponStoreItemList?.get(position)?.s_id)
                    intent.putExtra("s_name", couponStoreItemList?.get(position)?.s_name)
                    intent.putExtra("s_img", couponStoreItemList?.get(position)?.s_img)
                    intent.putExtra("s_cont", couponStoreItemList?.get(position)?.s_cont)
                    intent.putExtra("s_web", couponStoreItemList?.get(position)?.s_web)
                    intent.putExtra("s_menu_img", couponStoreItemList?.get(position)?.s_menu_img)
                    intent.putExtra("expiry_date", couponStoreItemList?.get(position)?.expiry_date)
                    intent.putExtra("locations", couponStoreItemList?.get(position)?.locations as Serializable)


                    //(context as Activity).finish()
                    context.startActivity(intent)
                } else
                    (context as CouponStoreActivity).showToast(context.getString(R.string.error_no_internet_msg))

                press = 1;

            }
        }

        val s_img: String = IMAGE_URL +"store/"+couponStoreItemList?.get(position)?.s_img
        GlideApp.with(context)
                .load(s_img)
                .placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder)
                .into(holder.ivCouponStoreLogo)
    }

    override fun getItemCount(): Int {
        return couponStoreItemList!!.size
    }
}



