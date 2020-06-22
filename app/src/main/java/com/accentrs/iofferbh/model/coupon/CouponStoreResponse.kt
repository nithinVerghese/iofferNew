package com.accentrs.iofferbh.model.coupon

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CouponStoreResponse(

        @field:SerializedName("status")
        var status: Boolean? = null,

        @field:SerializedName("message")
        var message: String? = null,

        @field:SerializedName("data")
        var data: List<CouponStoreData>? = null
)

class CouponStoreData {

    @SerializedName("s_id")
    var s_id: String? = null

    @SerializedName("s_name")
    var s_name: String? = null

    @SerializedName("s_img")
    var s_img: String? = null

    @SerializedName("s_loc")
    var s_loc: String? = null

    @SerializedName("s_cont")
    var s_cont: String? = null

    @SerializedName("s_web")
    var s_web: String? = null

    @SerializedName("s_menu_img")
    var s_menu_img: String? = null

    @SerializedName("expiry_date")
    var expiry_date: String? = null

    @SerializedName("total_coupon")
    var total_coupon: Int? = null

    @field:SerializedName("locations")
    var locations: List<Locations>? = null



}

/*class Locations {
    @SerializedName("l_id")
    var l_id: String? = null

    @SerializedName("s_id")
    var s_id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("lat")
    var lat: String? = null

    @SerializedName("lng")
    var lng: String? = null

    @SerializedName("status")
    var status: String? = null
}*/

class Locations : Serializable {
    //@SerializedName("l_id")
   // var l_id: String? = null

    @SerializedName("s_id")
    var s_id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("lat")
    var lat: String? = null

    @SerializedName("lng")
    var lng: String? = null

   @SerializedName("status")
    var status: String? = null

    override fun toString(): String {
        return "Locations{" +
                "address = '" + address + '\''.toString() +

                ",lng = '" + lng + '\''.toString() +
                //",l_id = '" + l_id + '\''.toString() +
                ",name = '" + name + '\''.toString() +
                ",status = '" + status + '\''.toString() +
                ",s_id = '" + s_id + '\''.toString() +
                ",lat = '" + lat + '\''.toString() +
                "}"
    }
}
