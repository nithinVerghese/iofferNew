package com.accentrs.iofferbh.activity

import android.content.Intent
import android.opengl.Matrix
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.widget.Switch
import com.accentrs.iofferbh.adapter.coupon.CouponStoreAdapter
import com.accentrs.iofferbh.model.coupon.CouponStoreResponse
import kotlinx.android.synthetic.main.activity_coupon_store.*
import kotlinx.android.synthetic.main.activity_coupon_store.tv_coupon_store_title
import kotlinx.android.synthetic.main.row_coupon_store_layout.*
import kotlinx.android.synthetic.main.row_no_data_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.R
import androidx.annotation.IntegerRes
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.model.coupon.CouponStoreData


class CouponStoreActivity : BaseActivity() {
    private var c_id: String = ""
    private var c_name: String = ""
    private var expiry_date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.accentrs.iofferbh.R.layout.activity_coupon_store)
        fetchIntent()
        initToolBar()


        tv_coupon_store_title.text = c_name
        //exp_dt.text=expiry_date
        hitCouponStoreApi()

        press=0



    }

    private fun initToolBar() {
        setIofferBhLogo()
        setSearchIconVisibility(true)
        setLeftIconDrawableVisibility(true)
        setLeftIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_arrow_back))
        setRightIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_search_white))
    }



//        val mIntent = Intent(this, SearchActivity::class.java)
//        startActivity(mIntent)

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v?.id) {
            com.accentrs.iofferbh.R.id.ll_custom_ab_right -> gotosearch()
        }

    }


    private fun gotosearch(){

        val i = Intent(this, Coupon_search_new::class.java)
        i.putExtra("c_id", c_id)
        //showToast("C_ID "+c_id+"/n C_name : "+c_name)
        startActivity(i)
        finish()

    }


    private fun fetchIntent() {
        c_id = intent.getStringExtra("c_id")
        c_name = intent.getStringExtra("c_name")

        //expiry_date = intent.getStringExtra("expiry_date")

      //  showToast(expiry_date)

    }



    private fun hitCouponStoreApi() {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))

        val call = getApiIneterface().getCouponStoreResponse(c_id, 0, 300)
        call.enqueue(object : Callback<CouponStoreResponse> {
            override fun onResponse(@NonNull call: Call<CouponStoreResponse>, @NonNull response: Response<CouponStoreResponse>) {
                dismissProgressDialog()
                if (response.isSuccessful() ) {
                    val modal = response.body()?.data

                    if (modal != null && response.body()?.status!!) {

                        Collections.sort(modal, object : Comparator<CouponStoreData> {
                            override fun compare(obj1: CouponStoreData, obj2: CouponStoreData): Int {
                                // ## Ascending order
                                //return obj1.total_coupon().compareToIgnoreCase(obj2.getCompanyName) // To compare string values
                                return Integer.valueOf(obj2.total_coupon!!.toInt()).compareTo(obj1.total_coupon!!.toInt()); // To compare integer values

                                // ## Descending order
                                // return obj2.getCompanyName().compareToIgnoreCase(obj1.getCompanyName()); // To compare string values
                                // return Integer.valueOf(obj2.getId()).compareTo(obj1.getId()); // To compare integer values
                            }
                        })

                        rv_coupon_store.layoutManager = GridLayoutManager(this@CouponStoreActivity, 2)
                        rv_coupon_store.adapter = CouponStoreAdapter(this@CouponStoreActivity, modal)
                    } else {
                        rv_coupon_store.setVisibility(GONE)
                        val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                            val view: View = layoutInflater.inflate(com.accentrs.iofferbh.R.layout.row_no_data_layout, rl_coupon_store, false)
                        view.tv_no_data.text = response.body()?.message
                        rl_coupon_store.addView(view, 0)
                    }
                } else {
                    rv_coupon_store.setVisibility(GONE)
                    val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                    val view: View = layoutInflater.inflate(com.accentrs.iofferbh.R.layout.row_no_data_layout, rl_coupon_store, false)
                    view.tv_no_data.text = response.body()?.message
                    rl_coupon_store.addView(view, 0)
                }
            }

            override fun onFailure(@NonNull call: Call<CouponStoreResponse>, @NonNull t: Throwable) {
                dismissProgressDialog()
                Log.e("FAIL_", t.message);
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
