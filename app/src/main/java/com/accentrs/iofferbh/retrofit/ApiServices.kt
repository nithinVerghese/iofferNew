package com.accentrs.iofferbh.retrofit

import com.accentrs.iofferbh.model.coupon.*
import com.accentrs.iofferbh.model.delivery.CategoryResponse
import com.accentrs.iofferbh.model.delivery.Store
import com.accentrs.iofferbh.model.delivery.StoreInfo
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("store_category")
    fun getCouponCategoryResponse(): Call<CouponCategoryResponse>

    @POST("store")
    @FormUrlEncoded
    fun getCouponStoreResponse(@Field("c_id") c_id: String, @Field("page_no") page_no: Int,
                               @Field("limit") limit: Int): Call<CouponStoreResponse>

    @POST("coupon_detail")
    @FormUrlEncoded
    fun getCouponDetailResponse(@Field("s_id") s_id: String, @Field("device_id") device_id: String): Call<CouponDetailResponse>

    @POST("my_coupon")
    @FormUrlEncoded
    fun getMyCouponResponse(@Field("device_id") device_id: String): Call<MyCouponResponse>

    @POST("redeem_coupon")
    @FormUrlEncoded
    fun getCouponRedeemResponse(@Field("device_id") device_id: String, @Field("s_id") s_id: String,
                                @Field("coupon_name") coupon_name: String): Call<CouponRedeemResponse>

    @POST("coupon_purchase")
    @FormUrlEncoded
    fun getCouponPurchaseResponse(@Field("device_id") device_id: String, @Field("coupon_name") coupon_name: String,
                                  @Field("qty") qty: String, @Field("s_name") s_name: String,
                                  @Field("s_id") s_id: String): Call<CouponPurchaseResponse>

    @GET("coupon_module")
    @FormUrlEncoded
    fun getCouponModuleResponse(): Call<CouponModuleResponse>

    @POST("search_store")
    @FormUrlEncoded
    fun getCouponSearchResponse(@Field("c_id") c_id: String, @Field("s_name") s_name: String): Call<CouponSearchResponse>

    @GET("dcategory")
    fun dcategory(): Call<List<CategoryResponse>>

    @GET("dshoplocation")
    fun dshoplocation(@Header("category") category:String ): Call<List <Store>>

    @GET("dshopinfo")
    fun dshopinfo(@Header("shop") category:String ): Call<List <StoreInfo>>

    @GET("dshoplocationcor")
    fun dshoplocation(@Header("category") category:String ,
                      @Header("latitude") latitude:String ,
                      @Header("longitude") longitude:String): Call<List <Store>>


    @GET("dshopinfodet")
    fun dshopinfodet(@Header("shop") category:String ): Call<List <StoreInfo>>

}
