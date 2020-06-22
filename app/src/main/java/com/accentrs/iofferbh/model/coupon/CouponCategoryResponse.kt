package com.accentrs.iofferbh.model.coupon

import com.google.gson.annotations.SerializedName

data class CouponCategoryResponse(

        @field:SerializedName("status")
        var status: Boolean? = null,

        //@field:SerializedName("message")
        var message: String? = null,

        @field:SerializedName("data")
        var data: List<CouponCategoryData>? = null
)

class CouponCategoryData {


    @SerializedName("c_id")
    var c_id: String? = null

    @SerializedName("c_name")
    var c_name: String? = null

    @SerializedName("c_img")
    var c_img: String? = null
}