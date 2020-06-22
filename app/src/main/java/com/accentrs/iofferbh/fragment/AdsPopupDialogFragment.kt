package com.accentrs.iofferbh.fragment


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.accentrs.apilibrary.utils.Constants
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.CompanyOfferActivity
import com.accentrs.iofferbh.activity.CouponDetailActivity
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.interfaces.AdsPopupListener
import com.accentrs.iofferbh.model.adspopup.AdsPopupResponse
import com.accentrs.iofferbh.model.adspopup.CouponPopupResponse
import com.accentrs.iofferbh.service.AdsPopupService
import com.accentrs.iofferbh.utils.SharedPreferencesData
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_ads_popup_dialog.view.*
import java.io.Serializable


class AdsPopupDialogFragment : DialogFragment(), View.OnClickListener {

    private var rootView: View? = null
    private var type: Int = 0  //0- Offer. 1- coupon
    private var adsPopupResponse: AdsPopupResponse? = null
    private var couponPopupResponse: CouponPopupResponse? = null

    private var adsPopupListener: AdsPopupListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_ads_popup_dialog, container, false)
            dialog.requestWindowFeature(STYLE_NO_TITLE)
            dialog.setCancelable(false)
            initializeResources()
        }
        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        adsPopupListener = context as AdsPopupListener?
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        adsPopupListener!!.showSpinningWheelSpinner()
    }

    private fun initializeResources() {

        val adPopupData = SharedPreferencesData(activity).adsPopupData

        Log.d("ads popup data", adPopupData + " ")

        if (adPopupData != null && !TextUtils.isEmpty(adPopupData))
        {
            couponPopupResponse = Gson().fromJson(adPopupData, CouponPopupResponse::class.java)
            adsPopupResponse = Gson().fromJson(adPopupData, AdsPopupResponse::class.java)

            if (adsPopupResponse!!.data != null || couponPopupResponse!!.data != null) {

                //SharedPreferencesData(activity).adsPopupStatus = false
                if(adsPopupResponse!!.data!!.companyId != null){
                    //Offer
                    type = 0
                    adsPopupResponse = Gson().fromJson(adPopupData, AdsPopupResponse::class.java)

                }else{
                    //Coupon
                    type = 1
                    couponPopupResponse = Gson().fromJson(adPopupData, CouponPopupResponse::class.java)
                 //
                }



                val target = object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        rootView!!.iv_ad_popup.setImageBitmap(resource)
                    }

                }
                if(type == 0) {
                    GlideApp.with(rootView!!.context)
                            .asBitmap()
                            .load(Constants.BASE_URL +adsPopupResponse!!.data!!.adsImageUrl) //Constants.BASE_URL +adsPopupResponse!!.data!!.adsImageUrl
                            .apply(RequestOptions().placeholder(R.drawable.iofferbh_placeholder))
                            .into(target)

                    GlideApp.with(rootView!!.context)
                            .load(Constants.BASE_URL + adsPopupResponse!!.data!!.company!!.logo)
                            .fitCenter()
                            .apply(RequestOptions.circleCropTransform())
                            .into(rootView!!.iv_ads_popup_company)

                    rootView!!.tv_company_offer_desc.text = adsPopupResponse!!.data!!.company!!.nameEn
                    //rootView!!.tv_company_website.text = adsPopupResponse!!.data!!.company!!.website
                }else{
                    GlideApp.with(rootView!!.context)
                            .asBitmap()
                            .load(Constants.BASE_URL +couponPopupResponse!!.data!!.coupons_image_url)
                            .apply(RequestOptions().placeholder(R.drawable.iofferbh_placeholder))
                            .into(target)

                    GlideApp.with(rootView!!.context)
                            .load(Constants.BASE_URL + "storage/coupon_module/store/"+ couponPopupResponse!!.data!!.store!!.s_img)//Constants.BASE_URL + "storage/coupon_module/store/"+ couponPopupResponse!!.data!!.store!!.s_img
                            .fitCenter()
                            .apply(RequestOptions.circleCropTransform())
                            .into(rootView!!.iv_ads_popup_company)

                    rootView!!.tv_company_offer_desc.text = couponPopupResponse!!.data!!.store!!.s_name
                    //rootView!!.tv_company_website.text = couponPopupResponse!!.data!!.store!!.s_web
                }

                rootView!!.btn_ad_popup.setOnClickListener(this)
                rootView!!.iv_ad_popup_close.setOnClickListener(this)

                SharedPreferencesData(activity).adsPopupStatus = false
            }
        }else{
            // Dismissed Popup
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_ad_popup -> {

                if(type == 0){
                     AdsPopupService.startAdsPopupService(activity, adsPopupResponse!!.data!!.id!!)
                    val intentCompanyOffer: Intent = Intent(activity, CompanyOfferActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt(com.accentrs.iofferbh.utils.Constants.COMPANY_ID_KEY, adsPopupResponse!!.data!!.companyId!!)
                    intentCompanyOffer.putExtras(bundle)
                    startActivity(intentCompanyOffer)
                    dismiss()
                }else{
                     AdsPopupService.startAdsPopupService(activity, 1)
                    // Code to go to coupon details
//                    couponPopupResponse!!.data!!.store

                    val intent = Intent(context, CouponDetailActivity::class.java)
                    intent.putExtra("s_id", couponPopupResponse!!.data!!.store!!.s_id)
                    intent.putExtra("s_name", couponPopupResponse!!.data!!.store!!.s_name)
                    intent.putExtra("s_img", couponPopupResponse!!.data!!.store!!.s_img)
                    intent.putExtra("s_cont", couponPopupResponse!!.data!!.store!!.s_cont)
                    intent.putExtra("s_web", couponPopupResponse!!.data!!.store!!.s_web)
                    intent.putExtra("s_menu_img", couponPopupResponse!!.data!!.store!!.s_menu_img)
                    intent.putExtra("expiry_date", couponPopupResponse!!.data!!.store!!.expiry_date)
                    intent.putExtra("locations", couponPopupResponse!!.data!!.store!!.locations as Serializable)


                    //(context as Activity).finish()
                    context?.startActivity(intent)
                    dismiss()


                }

                return
            }

            R.id.iv_ad_popup_close -> {
                dismiss()
                return
            }

        }

    }


}
