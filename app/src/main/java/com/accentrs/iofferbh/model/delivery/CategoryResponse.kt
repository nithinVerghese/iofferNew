package com.accentrs.iofferbh.model.delivery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryResponse {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("name_en")
    @Expose
    private var nameEn: String? = null

    @SerializedName("name_ar")
    @Expose
    private var nameAr: String? = null

    @SerializedName("logo")
    @Expose
    private var logo: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getNameEn(): String? {
        return nameEn
    }

    fun setNameEn(nameEn: String?) {
        this.nameEn = nameEn
    }

    fun getNameAr(): String? {
        return nameAr
    }

    fun setNameAr(nameAr: String?) {
        this.nameAr = nameAr
    }

    fun getLogo(): String? {
        return logo
    }

    fun setLogo(logo: String?) {
        this.logo = logo
    }


}