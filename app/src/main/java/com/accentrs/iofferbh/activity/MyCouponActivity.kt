package com.accentrs.iofferbh.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.coupon.MyCouponAdapter
import com.accentrs.iofferbh.model.coupon.MyCouponResponse
import com.accentrs.iofferbh.retrofit.ApiServices
import com.accentrs.iofferbh.retrofit.ServiceGenerator
import com.accentrs.iofferbh.utils.SharedPreferencesData
import com.accentrs.iofferbh.utils.Utils
import kotlinx.android.synthetic.main.activity_my_coupon.*
import kotlinx.android.synthetic.main.row_no_data_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.xml.datatype.DatatypeConstants.DAYS

import java.util.*
import java.util.concurrent.TimeUnit


class MyCouponActivity : BaseActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.accentrs.iofferbh.R.layout.activity_my_coupon)
        initToolBar()
        term.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(this@MyCouponActivity)

            builder.setTitle("Terms and Conditions ")
            builder.setMessage("١- نحن غير مسؤلين عن كوبون المشتريات في حال فقدان جهاز الهاتف"+
                    "\n\n۲- نحن غير مسؤلين عن كوبونات منتهية التاريخ"+
                    "\n\n1. We are not responsible for the purchased coupons if you loose this device."+
                    "\n\n2. We are not responsible if the coupon validity expires.")

            builder.setPositiveButton("Close") { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        })
        hitMyCouponApi()
        
    }

//    override fun onClick(v: View?) {
//        super.onClick(v)
//
//        when (v?.id) {
//            com.accentrs.iofferbh.R.id.ll_custom_ab_right -> login()
//        }
//
//    }

    private fun initToolBar() {
        setIofferBhLogo()
//        setRightIconDrawableVisibility(true)
//        setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sign_out_option))

        //iv_custom_ab_right
        setLeftIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_arrow_back))
    }



    private fun login(){
        val i = Intent(this, a_social_Login::class.java)
        startActivity(i)
    }

    private fun hitMyCouponApi() {

        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))
        val apiServices = ServiceGenerator.createService().create(ApiServices::class.java)

        val call = apiServices.getMyCouponResponse(unidev())
        call.enqueue(object : Callback<MyCouponResponse> {
            override fun onResponse(@NonNull call: Call<MyCouponResponse>, @NonNull response: Response<MyCouponResponse>) {
                dismissProgressDialog()
                if (response.isSuccessful() && response.body()?.status!!) {
                    val modal = response.body()?.data
                    //if (response.body()?.status!!) {
                        if (modal != null) {
                            rv_my_coupon.layoutManager = GridLayoutManager(this@MyCouponActivity, 2) as RecyclerView.LayoutManager?
                            rv_my_coupon.adapter = MyCouponAdapter(this@MyCouponActivity, modal)
                        } else {
                            //showToast("Data not available!!!")
                            rv_my_coupon.setVisibility(View.GONE)
                            val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                            val view: View = layoutInflater.inflate(com.accentrs.iofferbh.R.layout.row_no_data_layout, rv_my_coupon, false)
                            //view.tv_no_data.text = response.body()?.message
                            rv_my_coupon.addView(view, 0)
                        }
                    /*} else {
                        rv_my_coupon.setVisibility(View.GONE)
                        showToast("No coupon found")
                    }*/
                } else {
                    //showToast("Data not available!!!")
                    //rv_my_coupon.layoutManager = GridLayoutManager(this@MyCouponActivity, 2)
                    rv_my_coupon.setVisibility(View.GONE)
                    showToast("No coupon found")
                    /*val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                    val view: View = layoutInflater.inflate(R.layout.row_no_data_layout, rv_my_coupon, false)
                    view.tv_no_data.text = response.body()?.message
                    rv_my_coupon.addView(view, 0)*/
                }
            }

            override fun onFailure(@NonNull call: Call<MyCouponResponse>, @NonNull t: Throwable) {
                dismissProgressDialog()
                Log.e("FAIL_", t.message);
            }
        })
    }

    private fun uniqueDeviceId(): String {
        return SharedPreferencesData(this).getUserID1()
    }

    override fun onResume() {
        super.onResume()
        Log.e("resume", "resume")
        hitMyCouponApi()
    }

    private fun unidev():String{
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun onBackPressed() {
        super.onBackPressed()

//        val i = Intent(this, HomeScreenActivity::class.java)
//        startActivity(i)
        finish()
    }
}
