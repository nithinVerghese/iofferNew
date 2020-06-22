package com.accentrs.iofferbh.model.coupon

import com.google.gson.annotations.SerializedName

data class CouponModuleResponse(

        @field:SerializedName("status")
        var status: Boolean? = null,

        //@field:SerializedName("message")
        var message: String? = null,

        @field:SerializedName("data")
        var data: List<coup_stat>? = null

)

class coup_stat {


        @SerializedName("coupon_module")
        var coupon_module: String? = null

        @SerializedName("my_coupon_module")
        var my_coupon_module: String? = null

}
