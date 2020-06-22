package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.model.coupon.CouponDetailData
import com.accentrs.iofferbh.viewholder.coupon.CouponDetailViewHolder

class CouponDetailAdapter(val context: Context, val couponDetailItemList: List<CouponDetailData>?) : RecyclerView.Adapter<CouponDetailViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponDetailViewHolder {
        return CouponDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.row_coupon_detail_layout, parent, false))


    }

    override fun onBindViewHolder(holder: CouponDetailViewHolder, position: Int) {

        /*holder.tvCouponStoreTitle.text = couponDetailItemList?.get(position)?.coupon_name

        *//*holder.tvCouponStoreTitle.setOnClickListener { v: View ->
            val intent = Intent(context, CouponStoreActivity::class.java)
            intent.putExtra("s_id", couponDetailItemList?.get(position)?.s_id)
            context.startActivity(intent)
        }*//*

        GlideApp.with(context).asBitmap().load(IMAGE_URL + couponDetailItemList?.get(position)?.s_img).into(object : BitmapImageViewTarget(holder.ivCouponStoreLogo) {
            override fun setResource(resource: Bitmap?) {
                val circularBitmapDrawable = create(context.resources, resource)
                circularBitmapDrawable.cornerRadius = Utils.dipToPixels(4f, context).toFloat()
                //holder.ivCouponStoreLogo.setImageDrawable(circularBitmapDrawable)
                holder.ivCouponStoreLogo.setBackground(circularBitmapDrawable)
            }
        })*/
    }

    override fun getItemCount(): Int {
        return couponDetailItemList!!.size
    }
}



