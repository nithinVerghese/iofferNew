package com.accentrs.iofferbh.model.coupon

import com.google.gson.annotations.SerializedName

data class CouponPurchaseResponse(

        @field:SerializedName("status")
        var status: Boolean? = null,

        //@field:SerializedName("message")
        var message: String? = null

        /*@field:SerializedName("data")
        var data: List<MyCouponDetailData>? = null*/
)

class CouponPurchaseData {

    @SerializedName("d_id")
    var d_id: String? = null

    @SerializedName("coupon_name")
    var coupon_name: String? = null

    @SerializedName("coupon_desc")
    var coupon_desc: String? = null

    @SerializedName("coupon_no")
    var coupon_no: String? = null

    @SerializedName("coupon_image")
    var coupon_image: String? = null

    @SerializedName("s_img")
    var s_img: String? = null

    @SerializedName("s_id")
    var s_id: String? = null

    @SerializedName("s_name")
    var s_name: String? = null

    @SerializedName("purchase_date")
    var purchase_date: String? = null

    @SerializedName("no_of_coupons")
    var no_of_coupons: String? = null
}
