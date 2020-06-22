package com.accentrs.iofferbh.adapter.delivery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL_DE
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.CouponCategoryActivity
import com.accentrs.iofferbh.activity.CouponStoreActivity
import com.accentrs.iofferbh.activity.DeliveryStoreActivity
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.delivery.CategoryResponse
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.delivery.DeliveryCategoryViewHolder
import com.bumptech.glide.request.target.BitmapImageViewTarget


class DeliveryCategoryAdapter(val context: Context, val couponCategoryItemList: List<CategoryResponse>?) : RecyclerView.Adapter<DeliveryCategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryCategoryViewHolder {
        return DeliveryCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.row_delivory_category_layout, parent, false))
    }

    override fun onBindViewHolder(holder: DeliveryCategoryViewHolder, position: Int) {

        holder.tvDeliveryCategoryTitle.text = couponCategoryItemList?.get(position)?.getNameEn()

        holder.tvDeliveryCategoryTitle.setOnClickListener { v: View ->

            if (press == 0) {

                if (Utils.isConnectedToInternet(context)) {
                    val intent = Intent(context, DeliveryStoreActivity::class.java)
                    intent.putExtra("id", couponCategoryItemList?.get(position)?.getId().toString())
                    intent.putExtra("c_name", couponCategoryItemList?.get(position)?.getNameEn())
                    context.startActivity(intent)
                    //Toast.makeText(context, "id: " + couponCategoryItemList?.get(position)?.getId(), Toast.LENGTH_SHORT).show()
                } else
                    (context as CouponCategoryActivity).showToast(context.getString(R.string.error_no_internet_msg))

            }

            press = 1;
        }



        GlideApp.with(context).asBitmap()
                .fitCenter()
                .load(IMAGE_URL_DE + couponCategoryItemList?.get(position)?.getLogo())
                .into(object : BitmapImageViewTarget(holder.ivDeliveryCategoryImage) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.cornerRadius = Utils.dipToPixels(4f, context).toFloat()
                        holder.ivDeliveryCategoryImage.setBackground(circularBitmapDrawable)
                    }
                })
    }

    override fun getItemCount(): Int {
        return couponCategoryItemList!!.size
    }
}



