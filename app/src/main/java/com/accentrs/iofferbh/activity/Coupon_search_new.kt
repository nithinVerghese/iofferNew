package com.accentrs.iofferbh.activity


import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.GridLayoutManager

import android.support.v7.widget.RecyclerView

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*

import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.coupon.CouponStoreAdapter
import com.accentrs.iofferbh.adapter.coupon.Coupon_search_new_Adaptor
import com.accentrs.iofferbh.model.coupon.CouponSearchResponse

import com.accentrs.iofferbh.model.home.HomeScreenModel
import com.accentrs.iofferbh.retrofit.ApiServices
import com.accentrs.iofferbh.retrofit.ServiceGenerator
import com.accentrs.iofferbh.utils.Utils
import kotlinx.android.synthetic.main.activity_coupon_search_new.*


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class Coupon_search_new : HeaderActivity() {
    private var llSearchParent: LinearLayout? = null
    private var rvSearch: RecyclerView? = null
    private var homeScreenModel: HomeScreenModel? = null
    private var tvNoSearch: TextView? = null
    private var mSearchEditText: EditText? = null
    private var ivSearchOffer: ImageView? = null
    private var rlSearchBack: RelativeLayout? = null

    lateinit var c_id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_search_new)

       // val i = Intent(this)


        intializeViews()
        setListener()
    }


    private fun intializeViews() {

        c_id = intent.getStringExtra("c_id")

        rvSearch = findViewById(R.id.rv_search_offer)
        tvNoSearch = findViewById(R.id.tv_no_search)
        mSearchEditText = findViewById(R.id.et_main_search)
        ivSearchOffer = findViewById(R.id.iv_main_search)
        llSearchParent = findViewById(R.id.ll_search_parent)
        rlSearchBack = findViewById(R.id.rl_search_back)

        mSearchEditText!!.requestFocus()
    }

    private fun setListener() {
        ivSearchOffer!!.setOnClickListener(this)
        rlSearchBack!!.setOnClickListener(this)

        mSearchEditText!!.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })


    }


    private fun hitSearchApi(c_name:String,searchText: String) {
        showProgressDialog(getString(R.string.msg_loading))

        val apiServices = ServiceGenerator.createService().create(ApiServices::class.java)

        val call = apiServices.getCouponSearchResponse(c_name,searchText)
//        Toast.makeText(this,call.toString(),Toast.LENGTH_SHORT).show()
        call.enqueue(object : Callback<CouponSearchResponse> {

            override fun onResponse(@NonNull call: Call<CouponSearchResponse>, @NonNull response: Response<CouponSearchResponse>) {
                dismissProgressDialog()

                if (response.isSuccessful() && response.body()?.status!!) {
                    val modal = response.body()?.data
                    //if (response.body()?.status!!) {
                        if (modal != null) {
                            rv_search_offer.layoutManager = GridLayoutManager(this@Coupon_search_new, 2)
                            rv_search_offer.adapter = Coupon_search_new_Adaptor(this@Coupon_search_new, modal)
                        } else {
                            //showToast("Data not available!!!")
                            rv_search_offer.setVisibility(View.GONE)
                            val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                            val view: View = layoutInflater.inflate(R.layout.row_no_data_layout, rv_search_offer, false)
                            //view.tv_no_data.text = response.body()?.message
                            rv_search_offer.addView(view, 0)
                        }
                    /*} else {
                        rv_my_coupon.setVisibility(View.GONE)
                        showToast("No coupon found")
                    }*/
                } else {
                    //showToast("Data not available!!!")
                    //rv_my_coupon.layoutManager = GridLayoutManager(this@MyCouponActivity, 2)


//                    rv_search_offer.setVisibility(View.GONE)
//                    showToast("No coupon found")
//                    var i = Intent(this@Coupon_search_new,Coupon_search_new::class.java)
//                    i.putExtra("c_id", c_id)
//                    startActivity(i)
//                    finish()

                    showToast("Coupon not found")
                    /*val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)
                    val view: View = layoutInflater.inflate(R.layout.row_no_data_layout, rv_my_coupon, false)
                    view.tv_no_data.text = response.body()?.message
                    rv_my_coupon.addView(view, 0)*/
                }
            }

            override fun onFailure(@NonNull call: Call<CouponSearchResponse>, @NonNull t: Throwable) {
                dismissProgressDialog()
                Log.e("FAIL_", t.message);
            }
        })

//        Toast.makeText(this,searchText,Toast.LENGTH_SHORT).show()

    }

//    private fun setWishlistAdapter() {
//        if (homeScreenModel != null && homeScreenModel!!.offers.size > 0) {
//            tvNoSearch!!.visibility = View.GONE
//            rvSearch!!.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//            val wishlistOfferAdapter = SearchOfferAdapter(this, homeScreenModel!!.offers, R.layout.row_company_offer_grid_layout)
//            rvSearch!!.adapter = wishlistOfferAdapter
//        } else {
//            rvSearch!!.visibility = View.GONE
//            tvNoSearch!!.visibility = View.VISIBLE
//        }
//
//    }


    override fun onClick(v: View) {
        super.onClick(v)

        when (v.id) {
            R.id.iv_main_search ->


                performSearch()


            R.id.rl_search_back -> finish()
        }

    }

//Empty text box
    private fun isValid(): Boolean {

        return if (TextUtils.isEmpty(mSearchEditText!!.text.toString())) {
            false
        } else true
    }

//Take value from text box
    private fun performSearch() {
        if (isValid()) {

            if (Utils.isConnectedToInternet(this@Coupon_search_new)) {
                hideKeyboard(llSearchParent)
                hitSearchApi(c_id,mSearchEditText!!.text.toString())

            } else {
                showToast(getString(R.string.error_no_internet_msg))
            }

        } else {
            showToast(getString(R.string.text_enter_search_keyword))
        }
    }
    }

