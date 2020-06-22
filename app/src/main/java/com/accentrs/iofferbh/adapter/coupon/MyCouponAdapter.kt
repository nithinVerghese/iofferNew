package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.iofferbh.activity.CouponRedeemActivity
import com.accentrs.iofferbh.activity.MyCouponActivity
import com.accentrs.iofferbh.activity.convertTime
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.coupon.MyCouponData
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.coupon.MyCouponViewHolder
import android.R
import android.annotation.SuppressLint
import com.accentrs.iofferbh.activity.CouponRedeemActivityexp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MyCouponAdapter(val context: Context, val myCouponItemList: List<MyCouponData>?) : RecyclerView.Adapter<MyCouponViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCouponViewHolder {
        return MyCouponViewHolder(LayoutInflater.from(context).inflate(com.accentrs.iofferbh.R.layout.row_my_coupon_layout, parent, false))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MyCouponViewHolder, position: Int) {
        holder.tvMyCouponLeft.text = myCouponItemList?.get(position)?.no_of_coupons + " left"
        holder.tvMyCouponTitle.text = myCouponItemList?.get(position)?.s_name

        var date: String? = myCouponItemList?.get(position)?.expiry_date


        holder.tv_my_coupon_exp.text = "Valid till : "+ convertTime().formatDate(date)

//        Toast.makeText(context,date,Toast.LENGTH_SHORT).show()

        holder.cvMyCoupon.setOnClickListener { v: View ->

            val sysdate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val date1 = SimpleDateFormat("yyyy-MM-dd").parse(sysdate)
            val date2 = SimpleDateFormat("yyyy-MM-dd").parse(date)

            if(date2 < date1) {//exp
                if (Utils.isConnectedToInternet(context)) {
                    val intent = Intent(context, CouponRedeemActivityexp::class.java)
                    intent.putExtra("no_of_coupons", myCouponItemList?.get(position)?.no_of_coupons)
                    intent.putExtra("coupon_image", myCouponItemList?.get(position)?.coupon_image)
                    intent.putExtra("s_id", myCouponItemList?.get(position)?.s_id)
                    intent.putExtra("s_name", myCouponItemList?.get(position)?.s_name)
                    intent.putExtra("coupon_name", myCouponItemList?.get(position)?.coupon_name)
                    context.startActivity(intent)

                } else//non
                    (context as MyCouponActivity).showToast(context.getString(com.accentrs.iofferbh.R.string.error_no_internet_msg))

            }else{
                if (Utils.isConnectedToInternet(context)) {
                    val intent = Intent(context, CouponRedeemActivity::class.java)
                    intent.putExtra("no_of_coupons", myCouponItemList?.get(position)?.no_of_coupons)
                    intent.putExtra("coupon_image", myCouponItemList?.get(position)?.coupon_image)
                    intent.putExtra("s_id", myCouponItemList?.get(position)?.s_id)
                    intent.putExtra("s_name", myCouponItemList?.get(position)?.s_name)
                    intent.putExtra("coupon_name", myCouponItemList?.get(position)?.coupon_name)
                    context.startActivity(intent)

                } else
                    (context as MyCouponActivity).showToast(context.getString(com.accentrs.iofferbh.R.string.error_no_internet_msg))
            }


        }

        val s_img: String = IMAGE_URL +"store/"+ myCouponItemList?.get(position)?.s_img



        GlideApp.with(context)
                .load(s_img)
                .placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder)
                .fitCenter()
                .into(holder.ivMyCouponLogo)

    }

    override fun getItemCount(): Int {
        return myCouponItemList!!.size
    }
}



