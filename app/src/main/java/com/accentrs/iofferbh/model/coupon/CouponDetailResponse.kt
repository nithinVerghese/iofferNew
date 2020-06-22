package com.accentrs.iofferbh.model.coupon

import com.google.gson.annotations.SerializedName

data class CouponDetailResponse(

        @field:SerializedName("status")
        var status: Boolean? = null,

//        @field:SerializedName("message")
//        var message: String? = null,


        @field:SerializedName("data")
        var data: List<CouponDetailData>? = null
)

class CouponDetailData {

    @SerializedName("left_coupon")
    var left_coupon: String? = null

    @SerializedName("coupon_name")
    var coupon_name: String? = null

    @SerializedName("coupon_image")
    var coupon_image: String? = null

    @SerializedName("coupon_desc")
    var coupon_desc: String? = null

    @SerializedName("coupon_price")
    var coupon_price: String? = null

    @SerializedName("expiry_date")
    var exp_date: String? = null

    @SerializedName("have_limit")
    var have_limit: String? = null

    @SerializedName("qty_limit")
    var qty_limit: String? = null

    @SerializedName("user_purchase")
    var user_purchase: String? = null

    @SerializedName("coupon_desc_ar")
    var coupon_desc_ar: String? = null

    @SerializedName("end_date")
    var end_date: String? = null
}
