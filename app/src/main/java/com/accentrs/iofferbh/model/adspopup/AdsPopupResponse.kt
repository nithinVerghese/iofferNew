package com.accentrs.iofferbh.model.adspopup

import com.google.gson.annotations.SerializedName

data class AdsPopupResponse(

        @field:SerializedName("data")
        val data: Data? = null,

        @field:SerializedName("status")
        val status: Boolean? = null,

        @field:SerializedName("message")
        val message: String? = null
)