package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL

import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.CouponCategoryActivity
import com.accentrs.iofferbh.activity.CouponStoreActivity
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.coupon.CouponCategoryData
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.coupon.CouponCategoryViewHolder
import com.bumptech.glide.request.target.BitmapImageViewTarget


class CouponCategoryAdapter(val context: Context, val couponCategoryItemList: List<CouponCategoryData>?) : RecyclerView.Adapter<CouponCategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponCategoryViewHolder {
        return CouponCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.row_coupon_category_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CouponCategoryViewHolder, position: Int) {

        holder.tvCouponCategoryTitle.text = couponCategoryItemList?.get(position)?.c_name

        holder.tvCouponCategoryTitle.setOnClickListener { v: View ->

            if (press == 0) {

                if (Utils.isConnectedToInternet(context)) {


                    val intent = Intent(context, CouponStoreActivity::class.java)
                    intent.putExtra("c_id", couponCategoryItemList?.get(position)?.c_id)
                    intent.putExtra("c_name", couponCategoryItemList?.get(position)?.c_name)
                    context.startActivity(intent)
                } else
                    (context as CouponCategoryActivity).showToast(context.getString(R.string.error_no_internet_msg))

            }

            press = 1;
        }



        GlideApp.with(context).asBitmap()
                .fitCenter()
                .load(IMAGE_URL +"category/"+couponCategoryItemList?.get(position)?.c_img)
                .into(object : BitmapImageViewTarget(holder.ivCouponCategoryImage) {
            override fun setResource(resource: Bitmap?) {
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                circularBitmapDrawable.cornerRadius = Utils.dipToPixels(4f, context).toFloat()
                holder.ivCouponCategoryImage.setBackground(circularBitmapDrawable)
            }
        })
    }

    override fun getItemCount(): Int {
        return couponCategoryItemList!!.size
    }
}



