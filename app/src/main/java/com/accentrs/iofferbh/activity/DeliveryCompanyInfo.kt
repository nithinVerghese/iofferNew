package com.accentrs.iofferbh.activity

import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.accentrs.apilibrary.utils.Constants
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.delivery.DeliveryOnlineAdaptor
import com.accentrs.iofferbh.adapter.delivery.DeliveryPhoneAdaptor
import com.accentrs.iofferbh.adapter.delivery.DeliveryWhatsappAdaptor
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.model.delivery.OnlineDataAdaptor
import com.accentrs.iofferbh.model.delivery.PhoneDataAdaptor
import com.accentrs.iofferbh.model.delivery.StoreInfo
import com.accentrs.iofferbh.model.delivery.WhatsappDataAdaptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DeliveryCompanyInfo : BaseActivity() {

    private var sId: String = ""
    private var sName: String = ""
    private var sImg: String = ""
    private var sDisEn: String = "Loading.....!"
    private var sDisAr: String = "Loading.....!"

    private var whatsapp: String = ""
    private var whatsapp_num: String = ""
    lateinit var whatsappArr: Array<String>
    lateinit var whatsappArr_num: Array<String>

    private var phone: String = ""
    private var phone_num: String = ""
    lateinit var phoneArr: Array<String>
    lateinit var phoneArr_num: Array<String>

    private var online: String = ""
    private var online_num: String = ""
    lateinit var onlineArr: Array<String>
    lateinit var onlineArr_num: Array<String>

    private val dataAdaptors: MutableList<WhatsappDataAdaptor> = ArrayList<WhatsappDataAdaptor>()
    private val phoneAdaptors: MutableList<PhoneDataAdaptor> = ArrayList<PhoneDataAdaptor>()
    private val onlineAdaptor: MutableList<OnlineDataAdaptor> = ArrayList<OnlineDataAdaptor>()

    private var mainAdaptor: DeliveryWhatsappAdaptor? = null
    private var PhoneMainAdaptor: DeliveryPhoneAdaptor? = null
    private var OnlineMainAdaptor: DeliveryOnlineAdaptor? = null

    private lateinit var storeImg: ImageView
    private lateinit var eng_TV: TextView
    private lateinit var araTxt: TextView
    private lateinit var cv_online: CardView
    private lateinit var cv_whatsapp: CardView
    private lateinit var cv_phone: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_store_info)

        storeImg = findViewById(R.id.storeImg)
        eng_TV = findViewById(R.id.eng_TV)
        araTxt = findViewById(R.id.araTxt)

        cv_online = findViewById(R.id.cv_online)
        cv_whatsapp = findViewById(R.id.cv_whatsapp)
        cv_phone = findViewById(R.id.cv_phone)

        initToolBar()
        fetchIntent()
        hitApi()
        setView()



        cv_online.setOnClickListener {
            Onlinedialog("Purchase by online")
        }

        cv_whatsapp.setOnClickListener {
            dialog1("Purchase by whatsapp")
        }

        cv_phone.setOnClickListener {
            Phonedialog("Purchase by phone")
        }

    }

    private fun initToolBar() {
        setIofferBhLogo()
        setSearchIconVisibility(true)
        setLeftIconDrawableVisibility(true)
        setLeftIconDrawable(ContextCompat.getDrawable(this, com.accentrs.iofferbh.R.drawable.ic_arrow_back))
    }

    private fun fetchIntent() {
        sId = intent.getStringExtra("s_id")
    }

    private fun hitApi() {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading))
        val call = apiIneterfaceDe.dshopinfodet(sId)
        call.enqueue(object : Callback<List<StoreInfo>> {

            override fun onFailure(call: Call<List<StoreInfo>>, t: Throwable) {
                dismissProgressDialog()
                showToast("err : \n" + t.message)
            }

            override fun onResponse(call: Call<List<StoreInfo>>, response: Response<List<StoreInfo>>) {
                dismissProgressDialog()
                val dataAbout: List<StoreInfo> = response.body()!!

                for (x in dataAbout) {

                    sImg = x.getLogo().toString()

                    sDisEn = x.getDescription().toString()
                    sDisAr = x.getDescriptionAr().toString()

                    whatsapp = x.getWhatsapp().toString()
                    whatsapp_num = x.getWhatsappName().toString()

                    phone = x.getPhone().toString()
                    phone_num = x.getPhoneName().toString()

                    online = x.getWebAddr().toString()
                    online_num = x.getWebAddrWebImg().toString()
                }
                setView()
                whatsappData()
                phoneData()
                onlineData()
            }
        })
    }

    private fun setView() {

        val sLogo: String = Constants.IMAGE_URL_DE + sImg
        GlideApp.with(this@DeliveryCompanyInfo)
                .load(sLogo)
                .placeholder(com.accentrs.iofferbh.R.drawable.iofferbh_placeholder)
                .into(storeImg)

        araTxt.text = sDisAr
        eng_TV.text = sDisEn
    }

    private fun dialog1(Title: String) {
        val dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(com.accentrs.iofferbh.R.layout.delivery_indo_dialog)
        dialogs.show()
        dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val body = dialogs.findViewById(R.id.tv_title) as TextView
        val imageView2 = dialogs.findViewById(R.id.imageView2) as ImageView
        val iv_icon = dialogs.findViewById(R.id.iv_icon) as ImageView
        val rcv: RecyclerView = dialogs.findViewById(R.id.rcv) as RecyclerView

        body.text = Title

        imageView2.setOnClickListener { v: View? -> dialogs.dismiss() }
        iv_icon.setImageResource(R.drawable.whatsapp_ba)

        mainAdaptor = DeliveryWhatsappAdaptor(this, dataAdaptors)
        rcv.adapter = mainAdaptor
        rcv.layoutManager = GridLayoutManager(this, 1)

    }

    private fun Phonedialog(Title: String) {
        val dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(com.accentrs.iofferbh.R.layout.delivery_indo_dialog)
        dialogs.show()
        dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val body = dialogs.findViewById(R.id.tv_title) as TextView
        val imageView2 = dialogs.findViewById(R.id.imageView2) as ImageView
        val iv_icon = dialogs.findViewById(R.id.iv_icon) as ImageView
        val rcv: RecyclerView = dialogs.findViewById(R.id.rcv) as RecyclerView

        body.text = Title

        imageView2.setOnClickListener { v: View? -> dialogs.dismiss() }
        iv_icon.setImageResource(R.drawable.phone_ba)
        PhoneMainAdaptor = DeliveryPhoneAdaptor(this, phoneAdaptors)
        rcv.adapter = PhoneMainAdaptor
        rcv.layoutManager = GridLayoutManager(this, 1)

    }

    private fun Onlinedialog(Title: String) {
        val dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(com.accentrs.iofferbh.R.layout.delivery_indo_dialog)
        dialogs.show()
        dialogs.getWindow()!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val body = dialogs.findViewById(R.id.tv_title) as TextView
        val imageView2 = dialogs.findViewById(R.id.imageView2) as ImageView
        val iv_icon = dialogs.findViewById(R.id.iv_icon) as ImageView
        val rcv: RecyclerView = dialogs.findViewById(R.id.rcv) as RecyclerView
        body.text = Title
        imageView2.setOnClickListener { v: View? -> dialogs.dismiss() }

        OnlineMainAdaptor = DeliveryOnlineAdaptor(this, onlineAdaptor)
        iv_icon.setImageResource(R.drawable.online_ba)
        rcv.adapter = OnlineMainAdaptor
        rcv.layoutManager = GridLayoutManager(this, 1)

    }

    private fun whatsappData(){
        whatsappArr_num = whatsapp.split("|").toTypedArray()
        whatsappArr = whatsapp_num.split("|").toTypedArray()
        for ((index, value) in whatsappArr.withIndex()) {
            val adaptor:WhatsappDataAdaptor = WhatsappDataAdaptor(whatsappArr_num.get(index), whatsappArr.get(index))
            dataAdaptors.add(adaptor)
        }
    }

    private fun phoneData(){
        phoneArr_num = phone.split("|").toTypedArray()
        phoneArr = phone_num.split("|").toTypedArray()
        for ((index, value) in phoneArr.withIndex()) {
            val adaptor:PhoneDataAdaptor = PhoneDataAdaptor(phoneArr_num.get(index), phoneArr.get(index))
            phoneAdaptors.add(adaptor)
        }
    }

    private fun onlineData(){
        onlineArr = online.split("|").toTypedArray()
        onlineArr_num = online_num.split("|").toTypedArray()
        for ((index, value) in onlineArr.withIndex()) {
            val adaptor:OnlineDataAdaptor = OnlineDataAdaptor(onlineArr.get(index), onlineArr_num.get(index))
            onlineAdaptor.add(adaptor)
        }
    }
}





