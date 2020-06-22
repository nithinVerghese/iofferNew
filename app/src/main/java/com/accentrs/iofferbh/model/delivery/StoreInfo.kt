package com.accentrs.iofferbh.model.delivery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StoreInfo {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("shop_name")
    @Expose
    private var shopName: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("description_ar")
    @Expose
    private var descriptionAr: String? = null

    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null

    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null

    @SerializedName("logo")
    @Expose
    private var logo: String? = null

    @SerializedName("web_addr")
    @Expose
    private var webAddr: String? = null

    @SerializedName("web_addr_web_img")
    @Expose
    private var webAddrWebImg: String? = null

    @SerializedName("whatsapp")
    @Expose
    private var whatsapp: String? = null

    @SerializedName("whatsapp_name")
    @Expose
    private var whatsappName: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

    @SerializedName("phone_name")
    @Expose
    private var phoneName: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getShopName(): String? {
        return shopName
    }

    fun setShopName(shopName: String?) {
        this.shopName = shopName
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getDescriptionAr(): String? {
        return descriptionAr
    }

    fun setDescriptionAr(descriptionAr: String?) {
        this.descriptionAr = descriptionAr
    }

    fun getLatitude(): String? {
        return latitude
    }

    fun setLatitude(latitude: String?) {
        this.latitude = latitude
    }

    fun getLongitude(): String? {
        return longitude
    }

    fun setLongitude(longitude: String?) {
        this.longitude = longitude
    }

    fun getLogo(): String? {
        return logo
    }

    fun setLogo(logo: String?) {
        this.logo = logo
    }

    fun getWebAddr(): String? {
        return webAddr
    }

    fun setWebAddr(webAddr: String?) {
        this.webAddr = webAddr
    }

    fun getWebAddrWebImg(): String? {
        return webAddrWebImg
    }

    fun setWebAddrWebImg(webAddrWebImg: String?) {
        this.webAddrWebImg = webAddrWebImg
    }

    fun getWhatsapp(): String? {
        return whatsapp
    }

    fun setWhatsapp(whatsapp: String?) {
        this.whatsapp = whatsapp
    }

    fun getWhatsappName(): String? {
        return whatsappName
    }

    fun setWhatsappName(whatsappName: String?) {
        this.whatsappName = whatsappName
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun getPhoneName(): String? {
        return phoneName
    }

    fun setPhoneName(phoneName: String?) {
        this.phoneName = phoneName
    }

}