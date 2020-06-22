package com.accentrs.iofferbh.adapter.coupon

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.provider.Settings
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.CouponRedeemActivity
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.interfaces.OnItemClick
import com.accentrs.iofferbh.model.coupon.CouponRedeemResponse
import com.accentrs.iofferbh.retrofit.ApiServices
import com.accentrs.iofferbh.retrofit.ServiceGenerator
import com.accentrs.iofferbh.utils.SharedPreferencesData
import com.accentrs.iofferbh.utils.Utils
import com.accentrs.iofferbh.viewholder.coupon.CouponRedeemViewHolder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_coupon_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CouponRedeemAdapter(val context: Context, val itemList: MutableList<Int>, val coupon_image: String,
                          val s_id: String, val coupon_name: String, val onClick: OnItemClick) : RecyclerView.Adapter<CouponRedeemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponRedeemViewHolder {
        return CouponRedeemViewHolder(LayoutInflater.from(context).inflate(R.layout.row_coupon_redeem_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CouponRedeemViewHolder, position: Int) {
        holder.llCouponRedeem.setOnClickListener { v: View ->
            showDialog("Are you sure you want to use this coupon?", position)
        }

        GlideApp.with(context)
                .load(IMAGE_URL +"coupon/"+ coupon_image).centerCrop()
                .placeholder(R.drawable.iofferbh_placeholder)
                .fitCenter()
                .into(holder.ivCouponRedeemImage)


//        val s_img: String = IMAGE_URL +"coupon/"+ coupon_image
//
//        var requestOptions = RequestOptions()
//        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
//        GlideApp.with(context)
//                .load(s_img).centerCrop()
//                .placeholder(R.drawable.iofferbh_placeholder)
//                .fitCenter()
//                .into(holder.ivCouponRedeemImage)
    }

    override fun getItemCount(): Int {
        return itemList.size - 1
    }


    private fun showDialog(title: String, position: Int) {
        val dialogs = Dialog(context)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.popup_coupon_redeem_view)
        dialogs.show()
        dialogs.setCanceledOnTouchOutside(false)
        dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val body = dialogs.findViewById(R.id.tv_title) as TextView
        body.text = title
        val yesBtn = dialogs.findViewById(R.id.tv_yes) as TextView
        val noBtn = dialogs.findViewById(R.id.tv_no) as TextView
        yesBtn.setOnClickListener {
            if (Utils.isConnectedToInternet(context))
                hitCouponRedeemApi(position)
            else
                (context as CouponRedeemActivity).showToast(context.getString(R.string.error_no_internet_msg))
            dialogs.dismiss()
        }
        noBtn.setOnClickListener {
            dialogs.dismiss()
        }
    }

    private fun hitCouponRedeemApi(position: Int) {

        Utils.showDialog(context, "Loading...", true)
        val apiServices = ServiceGenerator.createService().create(ApiServices::class.java)

        val call = apiServices.getCouponRedeemResponse(uniqueDeviceId(), s_id, coupon_name)
        call.enqueue(object : Callback<CouponRedeemResponse> {
            override fun onResponse(@NonNull call: Call<CouponRedeemResponse>, @NonNull response: Response<CouponRedeemResponse>) {
                Utils.showDialog(context, "Loading...", false)
                if (response.isSuccessful() && response.body()?.status!!) {
                    itemList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemList.size);
                    onClick.onClick(itemList.size - 1)
                }
            }

            override fun onFailure(@NonNull call: Call<CouponRedeemResponse>, @NonNull t: Throwable) {
                Utils.showDialog(context, "Loading...", false)
                Log.e("FAIL_", t.message);
            }
        })
    }

    private fun uniqueDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)
    }

}



