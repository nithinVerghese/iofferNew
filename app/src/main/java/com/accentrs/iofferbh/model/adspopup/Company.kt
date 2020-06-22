package com.accentrs.iofferbh.model.adspopup

import com.google.gson.annotations.SerializedName

data class Company(

        @field:SerializedName("website")
        val website: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("name_ar")
        val nameAr: Any? = null,

        @field:SerializedName("description_en")
        val descriptionEn: String? = null,

        @field:SerializedName("logo")
        val logo: String? = null,

        @field:SerializedName("banner")
        val banner: Any? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("contact_number")
        val contactNumber: String? = null,

        @field:SerializedName("name_en")
        val nameEn: String? = null,

        @field:SerializedName("description_ar")
        val descriptionAr: Any? = null,

        @field:SerializedName("status")
        val status: String? = null
)