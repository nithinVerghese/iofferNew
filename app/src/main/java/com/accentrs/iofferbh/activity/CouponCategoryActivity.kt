package com.accentrs.iofferbh.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.multi.press

import com.accentrs.iofferbh.adapter.coupon.CouponCategoryAdapter
import com.accentrs.iofferbh.model.coupon.CouponCategoryResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_coupon_category.*
import kotlinx.android.synthetic.main.activity_coupon_category.rl_coupon_category
import kotlinx.android.synthetic.main.activity_my_coupon.*
import kotlinx.android.synthetic.main.row_no_data_layout.view.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CouponCategoryActivity : BaseActivity() {

    private var rvOfferSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isCouponCategoryRefreshing: Boolean = false
    private lateinit var mQueue: RequestQueue

//    override fun onClick(v: View?) {
//        super.onClick(v)
//
//        when (v?.id) {
//            com.accentrs.iofferbh.R.id.ll_custom_ab_right -> gotomycoupon()
//        }
//
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_category)


        mQueue = Volley.newRequestQueue(this)

       val my_cup = findViewById(R.id.my_cup) as ImageView


            val url = "http://coupon.infoline.website/api/coupon_module"
            val request = JsonObjectRequest(Request.Method.GET, url, null,
                    com.android.volley.Response.Listener { response ->
                        try {
                            val jsonArray = response.getJSONArray("data")
                            val data = jsonArray.getJSONObject(0)
                            val coupon_module = data.getString("coupon_module")
                            val my_coupon_module = data.getString("my_coupon_module")

                            //Toast.makeText(HomeScreenActivity.this, "coupon = "+coupon_module+"\n my_coupon_module = "+my_coupon_module, Toast.LENGTH_SHORT).show();

                          //  showToast("coupon = "+coupon_module+"\n my_coupon_module = "+my_coupon_module)
                            if (Integer.parseInt(coupon_module) == 0) {

//                                val i = Intent(this, HomeScreenActivity::class.java)
//                                startActivity(i)
                                finish()

                            } else {
                                hitCouponCategoryApi()

                            }

                            if (Integer.parseInt(my_coupon_module) == 0) {

                                my_cup.visibility = View.GONE

                            } else {

                                my_cup.visibility = View.VISIBLE
                            }


                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }, com.android.volley.Response.ErrorListener { error -> Toast.makeText(this@CouponCategoryActivity, "Error: $error", Toast.LENGTH_SHORT).show() })

            mQueue.add(request)





        initializeToolbar()
        initializeViews()



        //hitCouponCategoryApi()


        my_cup.setOnClickListener{

            val dis = multiClickDissable.Singleton()
            val x = dis.disab()

            if (!x) {

                return@setOnClickListener
            }

            val i = Intent(this, MyCouponActivity::class.java)
                startActivity(i)
                finish()


        }

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

            //val apiServices = ServiceGenerator.createService().create(ApiServices::class.java)
            val call = getApiIneterface().getCouponCategoryResponse()

            call.enqueue(object : Callback<CouponCategoryResponse> {
                override fun onResponse(@NonNull call: Call<CouponCategoryResponse>, @NonNull response: Response<CouponCategoryResponse>) {

                    dismissProgressDialog()

                     if (response.isSuccessful() && response.body()?.status!!) {
                            val modal = response.body()?.data

                                    if(modal != null){
                                        rv_coupon_category.layoutManager = GridLayoutManager(this@CouponCategoryActivity, 1)
                                        rv_coupon_category.adapter = CouponCategoryAdapter(this@CouponCategoryActivity, modal)
                                    }

                                    else{
                                        finish()

//                                        rv_my_coupon.setVisibility(View.GONE)
//                                        val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
//                                        val view: View = layoutInflater.inflate(com.accentrs.iofferbh.R.layout.row_no_data_layout, rv_my_coupon, false)
//                                        //view.tv_no_data.text = response.body()?.message
//                                        rv_my_coupon.addView(view, 0)

                                    }
                            // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.

                            // Access the RecyclerView Adapter and load the data into it


                        } else {
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
                             view.tv_no_data.text = response.body()?.message
                            // Finally, add the view/custom layout to the activity root layout
                            rl_coupon_category.addView(view, 0)

                         showToast("No Category found")

                        }
                }

                override fun onFailure(@NonNull call: Call<CouponCategoryResponse>, @NonNull t: Throwable) {
                    dismissProgressDialog()
                    Log.e("FAIL_", t.message);
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
        press=0;

       // hitCouponCategoryApi()
    }
}
