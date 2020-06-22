package com.accentrs.iofferbh.activity

import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.coupon.CouponRedeemAdapter
import com.accentrs.iofferbh.interfaces.OnItemClick
import kotlinx.android.synthetic.main.activity_coupon_redeem.*

class CouponRedeemActivity : BaseActivity(), OnItemClick {
    override fun onClick(value: Int) {
        no_of_coupons = value.toString()
        tv_my_coupon_redeem_left_title.text = no_of_coupons + " left"
    }

    private var s_id: String = ""
    lateinit var coupon_name: String
    lateinit var coupon_image: String
    lateinit var no_of_coupons: String
    lateinit var s_name: String
    var coupon_left: String? = null

    //val list: ArrayList<CouponRedeemData>? = null
    val list: MutableList<Int> = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_redeem)
        fetchIntent()
        initToolBar()
        setViews()
        setAdapter()
    }

    private fun fetchIntent() {
        coupon_image = intent.getStringExtra("coupon_image")
        no_of_coupons = intent.getStringExtra("no_of_coupons")
        s_id = intent.getStringExtra("s_id")
        coupon_name = intent.getStringExtra("coupon_name")
        s_name = intent.getStringExtra("s_name")

       // showToast("no_of_coupons: "+no_of_coupons+"\n s_id : "+s_id+"\ncoupon_name : "+coupon_name+"\ns_name :"+s_name)
    }

    private fun setViews() {
        tv_my_coupon_redeem_title.text = s_name
        tv_my_coupon_redeem_left_title.text = no_of_coupons + " left"
    }

    private fun initToolBar() {
        setIofferBhLogo()
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
    }

    private fun setAdapter() {
        var a = no_of_coupons.toInt()

        for (i in 0..a) {
            list.add(i)
            //println(i)
        }

        rv_coupon_redeem.layoutManager = GridLayoutManager(this@CouponRedeemActivity, 1)
        rv_coupon_redeem.adapter = CouponRedeemAdapter(this@CouponRedeemActivity, list, coupon_image, s_id, coupon_name, this)
    }

    public fun unidev():String{
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
