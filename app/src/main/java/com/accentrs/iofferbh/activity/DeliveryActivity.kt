package com.accentrs.iofferbh.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.adapter.delivery.DeliveryCategoryAdapter

import com.accentrs.iofferbh.model.delivery.CategoryResponse
import com.android.volley.RequestQueue
import kotlinx.android.synthetic.main.activity_delivery.*
import kotlinx.android.synthetic.main.row_no_data_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeliveryActivity : BaseActivity() {

    private var rvOfferSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isCouponCategoryRefreshing: Boolean = false
    private lateinit var mQueue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        initializeToolbar()
        initializeViews()
        hitCouponCategoryApi()

    }

    private fun initializeViews() {
    }

    private fun initializeToolbar() {
        setIofferBhLogo()
        setLeftIconDrawableVisibility(true)
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
//        setRightIconDrawableVisibility(true)
//        setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_my_coupons))
    }

    private fun hitCouponCategoryApi() {

        showProgressDialog(getString(R.string.msg_loading))

        val call = apiIneterfaceDe.dcategory()
        call.enqueue(object : Callback<List<CategoryResponse>> {
            override fun onResponse(call: Call<List<CategoryResponse>>, response: Response<List<CategoryResponse>>) {
                dismissProgressDialog()
                if (response.isSuccessful()) {
                    var modal = response.body()
                    if (modal != null) {
                        rv_delivery_category.layoutManager = GridLayoutManager(this@DeliveryActivity, 1)
                        rv_delivery_category.adapter = DeliveryCategoryAdapter(this@DeliveryActivity, modal)
                    } else {
                        finish()
                    }
                }else {
                    //showToast(response.body()?.message)
                    //rv_coupon_store.setVisibility(View.GONE)
                    // Get the LayoutInflater from Context
                    val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)

                    // Inflate the layout using LayoutInflater
                    val view: View = layoutInflater.inflate(
                            R.layout.row_no_data_layout, // Custom view/ layout
                            rl_coupon_category, // Root layout to attach the view
                            false // Attach with root layout or not
                    )
                    view.tv_no_data.text = response.body().toString()
                    // Finally, add the view/custom layout to the activity root layout
                    rl_coupon_category.addView(view, 0)

                    showToast("No Category found")

                }
            }

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                showToast("err : \n" + t.message)
            }

        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        press = 0


        finish()

        //finish()
    }

    override fun onResume() {
        super.onResume()
        Log.e("resume", "C_resume")
        press = 0;

        // hitCouponCategoryApi()
    }
}





