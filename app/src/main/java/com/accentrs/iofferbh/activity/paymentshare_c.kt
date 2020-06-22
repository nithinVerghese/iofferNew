package com.accentrs.iofferbh.activity

import com.accentrs.iofferbh.activity.BaseActivity
import com.accentrs.iofferbh.activity.CouponCategoryActivity
import com.accentrs.iofferbh.activity.HomeScreenActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.model.coupon.CouponPurchaseResponse
import com.accentrs.iofferbh.utils.SharedPreferencesData
import kotlinx.android.synthetic.main.activity_payment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class paymentshare_c : BaseActivity() {

    var mywebview: WebView? = null
    lateinit var s_id: String
    lateinit var coupon_name: String
    lateinit var qty: String
    lateinit var s_name: String
    lateinit var device_id: String
    lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentshare)



        SharedPreferencesData(this).putcart(false)

//        val builder = AlertDialog.Builder(this@paymentshare)
//
//        builder.setTitle("Payment Type")
//        builder.setMessage(SharedPreferencesData(this@paymentshare).getMSG())
//
//        builder.setPositiveButton("Debit") { delivery_indo_dialog, which ->
        var strUser = SharedPreferencesData(this@paymentshare_c).get_C_PAY()
        s_id = SharedPreferencesData(this@paymentshare_c).get_SID()
        coupon_name = SharedPreferencesData(this@paymentshare_c).get_C_NAM()
        qty = SharedPreferencesData(this@paymentshare_c).get_QTY()
        s_name = SharedPreferencesData(this@paymentshare_c).get_S_NM()
        device_id = SharedPreferencesData(this@paymentshare_c).get_D_ID()
        url = strUser

        // }

//        builder.setNegativeButton("Credit") { delivery_indo_dialog, which ->
//            var strUser = SharedPreferencesData(this@paymentshare).get_C_PAY()
//            s_id = SharedPreferencesData(this@paymentshare).get_SID()
//            coupon_name = SharedPreferencesData(this@paymentshare).get_C_NAM()
//            qty = SharedPreferencesData(this@paymentshare).get_QTY()
//            s_name = SharedPreferencesData(this@paymentshare).get_S_NM()
//            device_id = SharedPreferencesData(this@paymentshare).get_D_ID()
//            url = strUser
//
//        }

//        val delivery_indo_dialog: AlertDialog = builder.create()
//        delivery_indo_dialog.show()


        // Get the web view settings instance
        val settings = web_view.settings

        // Enable java script in web view
        settings.javaScriptEnabled = true

        // Enable and setup web view cache
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)


        // Enable zooming in web view
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = true


        // Zoom web view text
        settings.textZoom = 100


        // Enable disable images in web view
        settings.blockNetworkImage = false
        // Whether the WebView should load image resources
        settings.loadsImagesAutomatically = true


        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.safeBrowsingEnabled = true  // api 26
        }
        //settings.pluginState = WebSettings.PluginState.ON
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        // settings.mediaPlaybackRequiresUserGesture = false


        // More optional settings, you can enable it by yourself
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        //settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true

        // WebView settings
        web_view.fitsSystemWindows = true
        web_view.loadUrl(url)

        /*
            if SDK version is greater of 19 then activate hardware acceleration
            otherwise activate software acceleration
        */
        web_view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
// Set web view client
        web_view.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something
                showToast("Don't Press Back button and Refresh button")

                // Enable disable back forward button
                // button_back.isEnabled = web_view.canGoBack()
//                button_forward.isEnabled = web_view.canGoForward()
            }


            override fun onPageFinished(view: WebView, url: String) {
                var fin = view.url.toString()
                fin = fin.replace("%20"," ")
                fin = fin.replace("="," ")
                fin = fin.replace("&"," ")
                var cou = 1
                //toast(fin)
                if(fin.contains("Problem")){
                    showToast("Problem with Payment")
                    SharedPreferencesData(this@paymentshare_c).putcart(false)
                    val intent = Intent(this@paymentshare_c, HomeScreenActivity::class.java);
                    startActivity(intent)
                    finish()
                }
                if(fin.contains("CANCELLED")){
                    showToast("Payment Cancelled")
                    SharedPreferencesData(this@paymentshare_c).putcart(false)
                    val intent = Intent(this@paymentshare_c, HomeScreenActivity::class.java);
                    startActivity(intent)
                    finish()
                }


                if((fin.contains("CAPTURED"))||(fin.contains("captured"))){

                    hitPurchaseCouponApi(device_id,coupon_name,qty,s_name)

                }
            }
        }

        // Set web view chrome client
        web_view.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progress_bar.progress = newProgress
            }
        }
    }

    private fun hitPurchaseCouponApi(device_id: String, coupon_name: String, qty: String, s_name: String) {

        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))
        val call = apiIneterface.getCouponPurchaseResponse(device_id, coupon_name, qty, s_name, s_id)
        call.enqueue(object : Callback<CouponPurchaseResponse> {
            override fun onFailure(call: Call<CouponPurchaseResponse>, t: Throwable) {
                dismissProgressDialog()
                Log.e("FAIL_", t.message)
            }

            override fun onResponse(call: Call<CouponPurchaseResponse>, response: Response<CouponPurchaseResponse>) {
                dismissProgressDialog()
                if (response.isSuccessful && response.body()?.status == true) {
                    showToast(response.body()?.message)
                    val intent = Intent(this@paymentshare_c, HomeScreenActivity::class.java)  //back to home
                    //set the new task and clear flags
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }else
                    showToast(response.body()?.message)
            }
        })

    }

    private fun showAppExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Please confirm")
        builder.setMessage("No back history found, want to exit the app?")
        builder.setCancelable(true)

        builder.setPositiveButton("Yes", { _, _ ->
            intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        })

        builder.setNegativeButton("No", { _, _ ->
            // Do something when want to stay in the app
            showToast("thank you.")
        })

        // Create the alert delivery_indo_dialog using alert delivery_indo_dialog builder
        val dialog = builder.create()

        // Finally, display the delivery_indo_dialog when user press back button
        dialog.show()
    }
    // Handle back button press in web view

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            // If web view have back history, then go to the web view back history
            //web_view.goBack()
            //toast("Going to back history")
        } else {
            // Ask the user to exit the app or stay in here
            showAppExitDialog()
        }
    }
}
// Extension function to show toast message

