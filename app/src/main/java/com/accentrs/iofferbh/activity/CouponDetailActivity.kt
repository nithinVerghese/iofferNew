package com.accentrs.iofferbh.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.iofferbh.fragment.CouponLocationFragment
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.coupon.CouponDetailData
import com.accentrs.iofferbh.model.coupon.CouponDetailResponse
import com.accentrs.iofferbh.model.coupon.CouponPurchaseResponse
import com.accentrs.iofferbh.utils.Utils
import kotlinx.android.synthetic.main.activity_coupon_detail.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import android.R
import android.app.Dialog
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.accentrs.iofferbh.utils.SharedPreferencesData
import com.github.chrisbanes.photoview.PhotoView
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64;

class CouponDetailActivity : BaseActivity(), View.OnClickListener {

    private var couponDetailDataList: List<CouponDetailData>? = null
    lateinit var s_id: String
    lateinit var s_name: String
    lateinit var s_img: String
    //lateinit var s_loc: String
    lateinit var s_cont: String
    lateinit var s_web: String
    lateinit var s_menu_img: Array<String>
    var total_coupon: Int = 0
    var coupon_price: Double = 0.0
    lateinit var exp_date: String
    lateinit var end_date: String
    lateinit var coupon_image: String
    lateinit var coupon_name: String
    lateinit var coupon_desc: String//coupon_desc_ar
    lateinit var coupon_desc_ar: String
    var left_coupon: Int = 0
    var have_limit: Int = 0
    var quantity: Int = 1
    var qty_limit:Int = 0
    var user_purchase:Int = 0


    lateinit var amt: String



    lateinit var list: List<*>


    var formatter = DecimalFormat("#0.000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // val nf = NumberFormat.getCurrencyInstance(Locale.US)
        // formatter = nf as DecimalFormat
        // formatter.applyPattern("#0.000")
        setContentView(com.accentrs.iofferbh.R.layout.activity_coupon_detail)

        term_tv.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(this@CouponDetailActivity)

            builder.setTitle("Terms and Conditions ")
            builder.setMessage("١- نحن غير مسؤلين عن كوبون المشتريات في حال فقدان جهاز الهاتف"+
                    "\n\n۲- نحن غير مسؤلين عن كوبونات منتهية التاريخ"+
                    "\n\n1. We are not responsible for the purchased coupons if you loose this device."+
                    "\n\n2. We are not responsible if the coupon validity expires.")

            builder.setPositiveButton("Close") { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        //DIALOG BOX

        })

        checkBox.setOnClickListener(View.OnClickListener {
            if (checkBox.isChecked) {
                btn_buy_now.setVisibility(View.VISIBLE)
            } else {
                btn_buy_now.setVisibility(View.INVISIBLE)
            }
        })

        fetchIntents()
        initToolBar()
        hitCouponDetailApi()
        //setViews()
    }

    private fun initToolBar() {
        setIofferBhLogo()
        setLeftIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_arrow_back))
    }

    private fun fetchIntents() {
        s_id = intent.getStringExtra("s_id")
        s_name = intent.getStringExtra("s_name")
        s_img = intent.getStringExtra("s_img")
        //s_loc = intent.getStringExtra("s_loc")
        s_cont = intent.getStringExtra("s_cont")
        s_web = intent.getStringExtra("s_web")
        s_menu_img = intent.getStringExtra("s_menu_img").split(";").toTypedArray()
        list = intent.getSerializableExtra("locations") as List<*>
    }//fetching the value for the top pare

    private fun setViews(status: Boolean) {
        if (status) {
            GlideApp.with(this)
                    .load(IMAGE_URL +"coupon/"+ coupon_image).centerCrop()
                    .placeholder(com.accentrs.iofferbh.R.drawable.download_controls_bg)
                    .fitCenter()
                    .into(iv_coupon_detail_image)

            iv_coupon_detail_image.setOnClickListener(){


                val img_url = IMAGE_URL +"coupon/"+ coupon_image

                val intent = Intent(this, coupon_view::class.java)
                intent.putExtra("img_url", img_url)
                intent.putExtras(intent)
                startActivity(intent)

            }

            tv_coupon_image_title.text = coupon_desc
            tv_coupon_title_ar.text = coupon_desc_ar
            btn_buy_now.setOnClickListener(this)
            onTextChangedManually()
            et_qty.setText(quantity.toString())



            tv_price.setText(formatter.format(coupon_price))
            exp_.setText("Coupon Valid Till: "+( convertTime().formatDate(end_date)).toString())

           // end_dt.setText("Coupon Valid Till: "+convertTime().formatDate(end_date))


            if(left_coupon<=1){
                tot_cpn.setText("Available coupon: "+left_coupon.toString())
            }else{
                tot_cpn.setText("Available coupons: "+left_coupon.toString())
            }

        }


        GlideApp.with(this).load(IMAGE_URL +"store/"+s_img).placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder).into(iv_coupon_store_logo)
        tv_coupon_store_title.text = s_name

        GlideApp.with(this).load(IMAGE_URL +"menu/"+ s_menu_img[0]).centerCrop().placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder).into(   iv_coupon_detail_menu)
        fl_coupon_detail_menu.setOnClickListener(this)
        tv_coupon_detail_location.setOnClickListener(this)

        tv_coupon_detail_contact.setOnClickListener(this)
        tv_coupon_detail_website.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        super.onClick(v)

        when (v.id) {
            com.accentrs.iofferbh.R.id.fl_coupon_detail_menu -> goToCouponGalleryActivity()
            com.accentrs.iofferbh.R.id.tv_coupon_detail_location -> goToLocationFragment()
            com.accentrs.iofferbh.R.id.tv_coupon_detail_contact -> goToDialNumber(s_cont)
            com.accentrs.iofferbh.R.id.tv_coupon_detail_website -> goToStoreWebsite(s_web)
            //com.accentrs.iofferbh.R.id.checkBox -> goToStoreWebsite(s_web)
            com.accentrs.iofferbh.R.id.btn_buy_now ->
                if (Utils.isConnectedToInternet(this))
                    goToPurchase()
                else
                    showToast(this.getString(com.accentrs.iofferbh.R.string.error_no_internet_msg))
            //else -> println("Number too high")
        }
    }

    private fun goToPurchase() {

        //quantity = et_qty.text.toString().toInt()
        if (!(et_qty.text.toString().length <= 0)) {
            if (quantity > 0 && quantity <= total_coupon) {
                display(quantity)
                hitPurchaseCouponApi(uniqueDeviceId(), coupon_name, quantity.toString(), s_name)
            } else
                showToast("Enter valid quantity")
        } else
            showToast("Enter valid quantity")

    }

    private fun terms(){

    }

    private fun goToStoreWebsite(storeUrl: String) {
        //showToast("website")
        var companyUrl = storeUrl
        if (!companyUrl.startsWith("https://") && !companyUrl.startsWith("http://")) {
            companyUrl = "http://$companyUrl"
        }
        val companyUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(companyUrl))
        startActivity(companyUrlIntent)
    }

    private fun goToDialNumber(contactNumber: String) {
        //showToast("contact")
        val call = Uri.parse("tel:$contactNumber")
        val dialIntent = Intent(Intent.ACTION_DIAL, call)
        startActivity(dialIntent)
    }


    private fun goToLocationFragment(/*locationsItems: ArrayList<LocationsItem>*/) {
        //showToast("location")
        val fragment = CouponLocationFragment()
        val bundle = Bundle()
        bundle.putSerializable("locations", list as Serializable)
        fragment.arguments = bundle
        fragment.show(this@CouponDetailActivity.supportFragmentManager, CouponLocationFragment.TAG)
    }

    private fun goToCouponGalleryActivity() {
        val intent = Intent(this, CouponGalleryActivity::class.java)
        intent.putExtra("s_menu_img", s_menu_img)
        intent.putExtra("s_img", s_img)
        intent.putExtra("s_name", s_name)
        intent.putExtras(intent)
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val p1 = Pair(iv_coupon_detail_menu as View, this.getString(R.string.shared_offer_image_name))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this as CouponGalleryActivity, p1)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }*/
        startActivity(intent)
    }

    fun increaseQuantity(view: View) {

        if (!(et_qty.text.toString().length <= 0)) {
            quantity = et_qty.text.toString().toInt()
            if (!quantity.equals(null) && quantity < total_coupon) {
                quantity = quantity + 1
                display(quantity)
            } else
                display(1)
        } else {
            et_qty.setText("" + 1)
        }
    }

    fun decreaseQuantity(view: View) {

        if (!(et_qty.text.toString().length <= 0)) {

            quantity = et_qty.text.toString().toInt()
            if (quantity > 1)
                quantity = quantity - 1
            display(quantity)
        } else
            showToast("Enter valid quantity")
    }

    private fun onTextChangedManually() {
        et_qty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().equals("")) {
                    quantity = s.toString().toInt()
                    if (quantity < 1) {
                        et_qty.setError("Invalid Value")
                        tv_price.setText("" + formatter.format(quantity.toDouble() * coupon_price))
                        //display(quantity)
                    } else if (!(quantity <= total_coupon)) {
                        et_qty.setError("Amount not Available")
                        tv_price.setText("" + formatter.format(quantity.toDouble() * coupon_price))
                        //display(quantity)
                    } else {
                        //display(quantity)
                        tv_price.setText("" + formatter.format(quantity.toDouble() * coupon_price))
                    }
                } else {
                    // display(1)
                }
            }
        })
    }


    private fun display(qty: Int) {
        if (qty.toString().length <= 3) {
            et_qty.setText(qty.toString())
            et_qty.setSelection(qty.toString().length)
            tv_price.setText("" + formatter.format(quantity.toDouble() * coupon_price))
            amt = formatter.format(quantity.toDouble() * coupon_price)
        } else {
            showToast("Enter valid quantity")
        }
    }

    private fun hitCouponDetailApi() {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))


        val call = getApiIneterface().getCouponDetailResponse(s_id,uniqueDeviceId())

        call.enqueue(object : Callback<CouponDetailResponse> {
            override fun onResponse(@NonNull call: Call<CouponDetailResponse>, @NonNull response: Response<CouponDetailResponse>) {
                dismissProgressDialog()
                if (response.isSuccessful() && response.body()?.status!!) {
                    couponDetailDataList = response.body()?.data
                    left_coupon = couponDetailDataList?.get(0)?.left_coupon!!.toInt()
                    total_coupon = couponDetailDataList?.get(0)?.left_coupon!!.toInt()//coupon_desc_ar
                    coupon_name = couponDetailDataList?.get(0)?.coupon_name!!
                    coupon_image = couponDetailDataList?.get(0)?.coupon_image!!
                    coupon_desc = couponDetailDataList?.get(0)?.coupon_desc!!
                    coupon_desc_ar = couponDetailDataList?.get(0)?.coupon_desc_ar!!
                    coupon_price = couponDetailDataList?.get(0)?.coupon_price!!.toDouble()
                    exp_date = couponDetailDataList?.get(0)?.exp_date!!
                    end_date = couponDetailDataList?.get(0)?.end_date!!
                    have_limit = couponDetailDataList?.get(0)?.have_limit!!.toInt()
                    user_purchase = couponDetailDataList?.get(0)?.user_purchase!!.toInt()
                    qty_limit = couponDetailDataList?.get(0)?.qty_limit!!.toInt()
                    setViews(response.body()?.status!!)

                } else {
                    setViews(response.body()?.status!!)
                    ll_coupon.setVisibility(View.GONE)
                    tv_no_coupon_found.setVisibility(View.VISIBLE)
                    println("Problem in else")
                }
            }

            override fun onFailure(@NonNull call: Call<CouponDetailResponse>, @NonNull t: Throwable) {
                dismissProgressDialog()
                Log.e("FAIL_", t.message)
                println("Problem in else")
            }
        })
    }

    private fun hitPurchaseCouponApi(device_id: String, coupon_name: String, qty: String, s_name: String) {


        var can_buy = user_purchase+quantity;

        if(have_limit == 1) {
            if ((quantity >= qty_limit) && (user_purchase >= qty_limit)) {
                showToast("You have exceeded the maximum Purchase limit of "+qty_limit)
            } else {
                if (user_purchase >= qty_limit) {

                    showToast("You have exceeded the maximum Purchase limit of "+qty_limit)

                } else if (can_buy > qty_limit) {
                    showToast("You have exceeded the maximum Purchase limit of "+qty_limit)
                } else
                {
                    val de2: String = "\nCoupon Name: " + coupon_name+"deviceid:"+device_id
                    val de1: String = coupon_name + coupon_name + quantity
                    var de: String = de1.replace("_", "")


                    de = de.replace(".", "")
                    de = de.replace(" ", "")
                    de = de.replace("%", "")
                    de = de.replace(",", "")
                    de = de.replace("!", "")
                    de = de.replace("@", "")
                    de = de.replace("#", "")
                    de = de.replace("$", "")
                    de = de.replace("^", "")
                    de = de.replace("&", "")
                    de = de.replace("*", "")
                    de = de.replace("(", "")
                    de = de.replace(")", "")
                    de = de.replace("-", "")
                    de = de.replace("+", "")
                    de = de.replace("=", "")
                    de = de.replace("`", "")
                    de = de.replace("|", "")
                    de = de.replace(":", "")
                    de = de.replace(";", "")
                    de = de.replace("?", "")
                    de = de.replace(",", "")
                    de = de.replace("<", "")
                    de = de.replace(">", "")
                    de = de.replace("[", "")
                    de = de.replace("]", "")
                    de = de.replace("}", "")
                    de = de.replace("{", "")
                    de = de.replace("'/'", "")
                    //de = de.replace("'\'","")
                    //de = de.replace("'''","")

                    var D_pay = payment_debit().pay(amt, de)
                    var C_pay = payment_credit().pay(amt, de)

                    var msg = "Total No.of Coupons: " + quantity +
                            "\nTotal Price: " + amt + " BHD"


                    if (coupon_price == 0.00) {


                        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))
                        val call = apiIneterface.getCouponPurchaseResponse(unidev(), coupon_name, qty, s_name, s_id)
                        call.enqueue(object : Callback<CouponPurchaseResponse> {
                            override fun onResponse(@NonNull call: Call<CouponPurchaseResponse>, @NonNull response: Response<CouponPurchaseResponse>) {
                                dismissProgressDialog()
                                if (response.isSuccessful && response.body()?.status == true) {
                                    showToast(response.body()?.message)
                                    val intent = Intent(this@CouponDetailActivity, MyCouponActivity::class.java)
                                    // set the new task and clear flags
                                    startActivity(intent)
                                    finish()
                                } else
                                    showToast(response.body()?.message)
                            }

                            override fun onFailure(@NonNull call: Call<CouponPurchaseResponse>, @NonNull t: Throwable) {
                                dismissProgressDialog()
                                Log.e("FAIL_", t.message)
                            }
                        })


                    }//payment equal to 0.00 bdh

                    else {

                        val dialogs = Dialog(this)
                        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialogs.setCancelable(false)
                        dialogs.setContentView(com.accentrs.iofferbh.R.layout.purchase)
                        dialogs.show()

                        dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        val body = dialogs.findViewById(com.accentrs.iofferbh.R.id.tv_title) as TextView
                        body.text = "Payment Type"
                        val msg1 = dialogs.findViewById(com.accentrs.iofferbh.R.id.tv_msg) as TextView
                        msg1.text = msg.toString()
                        val tv_credit = dialogs.findViewById(com.accentrs.iofferbh.R.id.tv_credit) as TextView
                        val tv_debit = dialogs.findViewById(com.accentrs.iofferbh.R.id.tv_debit) as TextView
                        val tv_cancel = dialogs.findViewById(com.accentrs.iofferbh.R.id.tv_cancel) as TextView

                        tv_credit.setOnClickListener {

                            val intent = Intent(this@CouponDetailActivity, PaymentActivity::class.java);
                            intent.putExtra("url", C_pay)
                            intent.putExtra("s_id", s_id)
                            intent.putExtra("coupon_name", coupon_name)
                            intent.putExtra("quantity", quantity.toString())
                            intent.putExtra("s_name", s_name)
                            intent.putExtra("device_id", unidev())
                            startActivity(intent)
                            finish()
                        }//credit pay

                        tv_cancel.setOnClickListener {
                            dialogs.dismiss()
                        }//on cancel

                        tv_debit.setOnClickListener {
                            //Toast.makeText(applicationContext, pay, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CouponDetailActivity, PaymentActivity::class.java);
                            intent.putExtra("url", D_pay)
                            intent.putExtra("s_id", s_id)
                            intent.putExtra("coupon_name", coupon_name)
                            intent.putExtra("quantity", quantity.toString())
                            intent.putExtra("s_name", s_name)
                            intent.putExtra("device_id", unidev())
                            startActivity(intent)
                            finish()
                        }//debit pay


                    }//payment greter than 0.00 bdh


                }

            }

        }


            if (have_limit == 0)
            {
                val de2: String = "Store Name:  " + s_name +
                        "\nCoupon Name: " + coupon_name
                val de1: String = coupon_name + coupon_name + quantity
                var de: String = de1.replace("_", "")


                de = de.replace(".", "")
                de = de.replace(" ", "")
                de = de.replace("%", "")
                de = de.replace(",", "")
                de = de.replace("!", "")
                de = de.replace("@", "")
                de = de.replace("#", "")
                de = de.replace("$", "")
                de = de.replace("^", "")
                de = de.replace("&", "")
                de = de.replace("*", "")
                de = de.replace("(", "")
                de = de.replace(")", "")
                de = de.replace("-", "")
                de = de.replace("+", "")
                de = de.replace("=", "")
                de = de.replace("`", "")
                de = de.replace("|", "")
                de = de.replace(":", "")
                de = de.replace(";", "")
                de = de.replace("?", "")
                de = de.replace(",", "")
                de = de.replace("<", "")
                de = de.replace(">", "")
                de = de.replace("[", "")
                de = de.replace("]", "")
                de = de.replace("}", "")
                de = de.replace("{", "")
                de = de.replace("'/'", "")
                //de = de.replace("'\'","")
                //de = de.replace("'''","")

                var D_pay = payment_debit().pay(amt, de)
                var C_pay = payment_credit().pay(amt, de)

                var msg = "Total No.of Coupons: " + quantity +
                        "\nTotal Price: " + amt + " BHD"


                if (coupon_price == 0.00) {


                    showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))
                    val call = apiIneterface.getCouponPurchaseResponse(unidev(), coupon_name, qty, s_name, s_id)
                    call.enqueue(object : Callback<CouponPurchaseResponse> {
                        override fun onResponse(@NonNull call: Call<CouponPurchaseResponse>, @NonNull response: Response<CouponPurchaseResponse>) {
                            dismissProgressDialog()
                            if (response.isSuccessful && response.body()?.status == true) {
                                showToast(response.body()?.message)
                                val intent = Intent(this@CouponDetailActivity, MyCouponActivity::class.java)
                                // set the new task and clear flags
                                startActivity(intent)
                                finish()
                            } else
                                showToast(response.body()?.message)
                        }

                        override fun onFailure(@NonNull call: Call<CouponPurchaseResponse>, @NonNull t: Throwable) {
                            dismissProgressDialog()
                            Log.e("FAIL_", t.message)
                        }
                    })


                }//payment equal to 0.00 bdh

                else {

                    val dialogs = Dialog(this)
                    dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialogs.setCancelable(false)
                    dialogs.setContentView(com.accentrs.iofferbh.R.layout.on_buy)
                    dialogs.show()

                    dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    val body = dialogs.findViewById(com.accentrs.iofferbh.R.id.Title) as TextView
                    body.text = "Payment Type"
                    val msg1 = dialogs.findViewById(com.accentrs.iofferbh.R.id.message_t) as TextView
                    msg1.text = msg.toString()
                    val tv_credit = dialogs.findViewById(com.accentrs.iofferbh.R.id.credit) as TextView
                    val tv_debit = dialogs.findViewById(com.accentrs.iofferbh.R.id.debit) as TextView
                    val tv_cancel = dialogs.findViewById(com.accentrs.iofferbh.R.id.cancel) as TextView

                    tv_credit.setOnClickListener {

                        val intent = Intent(this@CouponDetailActivity, PaymentActivity::class.java);
                        intent.putExtra("url", C_pay)
                        intent.putExtra("s_id", s_id)
                        intent.putExtra("coupon_name", coupon_name)
                        intent.putExtra("quantity", quantity.toString())
                        intent.putExtra("s_name", s_name)
                        intent.putExtra("device_id", unidev())
                        startActivity(intent)
                        finish()
                    }//credit pay

                    tv_cancel.setOnClickListener {
                        dialogs.dismiss()
                    }//on cancel

                    tv_debit.setOnClickListener {
                        //Toast.makeText(applicationContext, pay, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CouponDetailActivity, PaymentActivity::class.java);
                        intent.putExtra("url", D_pay)
                        intent.putExtra("s_id", s_id)
                        intent.putExtra("coupon_name", coupon_name)
                        intent.putExtra("quantity", quantity.toString())
                        intent.putExtra("s_name", s_name)
                        intent.putExtra("device_id", unidev())
                        startActivity(intent)
                        finish()
                    }//debit pay


                }//payment greter than 0.00 bdh


            }

    }



    private fun uniqueDeviceId(): String {

//        return SharedPreferencesData(this).getUserID1()

        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

    }//DEVICE ID

    private fun unidev():String{
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }//device id

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }//back press


}
