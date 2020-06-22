package com.accentrs.iofferbh.model.adspopup

import com.accentrs.iofferbh.model.coupon.CouponStoreData
import com.google.gson.annotations.SerializedName


data class Data2(

        @field:SerializedName("coupons_popup_name")
        val coupons_popup_name: String? = null,

        @field:SerializedName("coupons_image_url")
        val coupons_image_url: String? = null,

        @field:SerializedName("coupon_name")
        val coupon_name: String? = null,

        @field:SerializedName("store")
        val store: CouponStoreData? = null
)