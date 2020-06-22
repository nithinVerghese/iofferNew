package com.accentrs.iofferbh.model.delivery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Store {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("shop_name")
    @Expose
    private var shopName: String? = null

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

    @SerializedName("whatsapp")
    @Expose
    private var whatsapp: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

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

    fun getWhatsapp(): String? {
        return whatsapp
    }

    fun setWhatsapp(whatsapp: String?) {
        this.whatsapp = whatsapp
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

}