package com.accentrs.iofferbh.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_coupon_store.tv_coupon_store_title
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.adapter.delivery.DeliveryStoreAdapter
import com.accentrs.iofferbh.model.delivery.Store
import kotlinx.android.synthetic.main.activity_delivery_store.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DeliveryStoreActivity : BaseActivity() {
    private var cId: String = ""
    private var c_name: String = ""
    private lateinit var iv_nearest:ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.accentrs.iofferbh.R.layout.activity_delivery_store)
        //cId = " mm "+  intent.getStringExtra("id")
        fetchIntent()
        initToolBar()



        // cId = " mm "+  getIntent().getStringExtra("id")

        //showToast(cId)

        //tv_coupon_store_title.text = c_name
        //exp_dt.text=expiry_date
        hitCouponStoreApi()

        press=0

    }

    private fun initToolBar() {
        setIofferBhLogo()
        setSearchIconVisibility(true)
        setLeftIconDrawableVisibility(true)
        setLeftIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_arrow_back))
        //setRightIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_search_white))
    }



    private fun fetchIntent() {
        cId = intent.getStringExtra("id")
    }



    private fun hitCouponStoreApi() {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))

        val call = apiIneterfaceDe.dshoplocation(cId)
        call.enqueue(object : Callback<List <Store>>{
            override fun onFailure(call: Call<List <Store>>, t: Throwable) {
                dismissProgressDialog()
                showToast("err : \n" + t.message)
            }

            override fun onResponse(call: Call<List <Store>>, response: Response<List <Store>>) {
                if (response.isSuccessful) {
                    dismissProgressDialog()
                    if (response.isSuccessful) {
                        var modal = response.body()
                        if (modal != null) {
                            rv_delivery_store.layoutManager = GridLayoutManager(this@DeliveryStoreActivity, 2)
                            rv_delivery_store.adapter = DeliveryStoreAdapter(this@DeliveryStoreActivity, modal)
                        } else {
                            finish()
                        }
                    } else {
                        finish()
                    }
                }

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        super.onResume()
        Log.e("resume", "resume")
        hitCouponStoreApi()
        press=0
    }
}
