package com.accentrs.iofferbh.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.activity.multi.press
import com.accentrs.iofferbh.adapter.delivery.DeliveryStoreAdapter
import com.accentrs.iofferbh.model.delivery.Store
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_delivery_store.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DeliveryStoreActivity : BaseActivity() {
    private var cId: String = ""
    private var c_name: String = ""
    private lateinit var iv_nearest:ImageView
    private val PRQUEST_CODE_LOCATION_PERMISION: Int = 1
    private lateinit var latitude:String
    private lateinit var longitude:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.accentrs.iofferbh.R.layout.activity_delivery_store)
        //cId = " mm "+  intent.getStringExtra("id")

        iv_nearest = findViewById(R.id.iv_nearest)

        fetchIntent()
        initToolBar()

        iv_nearest.setOnClickListener {
            showToast("near")
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@DeliveryStoreActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PRQUEST_CODE_LOCATION_PERMISION)
            } else {
                getLocation()
            }
        }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PRQUEST_CODE_LOCATION_PERMISION && grantResults.size > 0) {
            getLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLocation() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation


            // for ActivityCompat#requestPermissions for more details.
            Log.d("ddddddddd","a")
            return
        }
        LocationServices.getFusedLocationProviderClient(this@DeliveryStoreActivity)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@DeliveryStoreActivity).removeLocationUpdates(this)
                        if (locationResult != null && locationResult.locations.size > 0) {
                            val latestLocationIndex = locationResult.locations.size - 1
                            latitude = locationResult.locations[latestLocationIndex].latitude.toString()
                            longitude = locationResult.locations[latestLocationIndex].longitude.toString()
                            Log.d("ddddddddd", "dd\n$latitude\n$longitude")
                            hitNearStoreApi(latitude,longitude)

                        }
                    }
                }, Looper.getMainLooper())
    }

    private fun hitNearStoreApi(lat:String , lon:String) {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))

        val call = apiIneterfaceDe.dshoplocation(cId,lat,lon)
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

}
