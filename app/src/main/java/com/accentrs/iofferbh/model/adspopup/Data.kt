package com.accentrs.iofferbh.model.adspopup

import com.accentrs.iofferbh.model.coupon.CouponStoreData
import com.google.gson.annotations.SerializedName

data class Data(

        @field:SerializedName("end_date")
        val endDate: String? = null,

        @field:SerializedName("company_id")
        val companyId: Int? = null,

        @field:SerializedName("ads_popup_name")
        val adsPopupName: String? = null,

        @field:SerializedName("ads_image_url")
        val adsImageUrl: String? = null,

        @field:SerializedName("company")
        val company: Company? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("start_date")
        val startDate: String? = null
)
