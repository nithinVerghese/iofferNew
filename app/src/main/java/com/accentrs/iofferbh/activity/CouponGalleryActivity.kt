package com.accentrs.iofferbh.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.accentrs.apilibrary.utils.Constants.IMAGE_URL
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.coupon.CouponGalleryMenuImageAdapter
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.helper.ViewPagerMultiTouchFix
import com.accentrs.iofferbh.model.home.OffersItem
import com.bumptech.glide.Glide
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_coupon_gallery.*


class CouponGalleryActivity : BaseActivity(), View.OnClickListener, View.OnTouchListener {


    private var tvViewAllInHd: TextView? = null

    private var mDetector: GestureDetectorCompat? = null


    private var offerGalleryImageAdapter: CouponGalleryMenuImageAdapter? = null

    private var mViewPagerMultiTouchFix: ViewPagerMultiTouchFix? = null
    private var recyclerPdpGallery: RecyclerView? = null
    private var position: Int = 0

    private var offersItem: OffersItem? = null

    private var tvOfferImageCount: TextView? = null
    private var tvOfferImageCurrentPosition: TextView? = null

    private val imageViewHdStatus: Boolean = false

    lateinit var s_menu_img: Array<String>
    lateinit var s_name: String
    lateinit var s_img: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_gallery)
        initToolBar()
        fetchIntentData()
        initializeViews()
        setRecyclerView()
        initializeGestureListener()
        setOfferAdapter()
        setOfferImageViewPagerData()
    }


    fun fetchIntentData() {
        if (intent.extras != null) {
            val bundle = intent.extras
            s_menu_img = intent.getStringArrayExtra("s_menu_img")
            s_img = intent.getStringExtra("s_img")
            s_name = intent.getStringExtra("s_name")
        }
    }


    private fun initToolBar() {
        setIofferBhLogo()
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
    }


    private fun initializeGestureListener() {
        mDetector = GestureDetectorCompat(this, GestureListener())
    }

    private fun setOfferImageViewPagerData() {
        tvOfferImageCurrentPosition!!.text = (position + 1).toString()
        //tvOfferImageCount!!.text = offersItem!!.offerImages.size.toString()
        tvOfferImageCount!!.text = s_menu_img.size.toString()
    }

    private fun setOfferImageCurrentPageData() {
        tvOfferImageCurrentPosition!!.text = (position + 1).toString()
    }


    private fun initializeViews() {
        mViewPagerMultiTouchFix = findViewById<View>(R.id.vp_offergallery) as ViewPagerMultiTouchFix
        recyclerPdpGallery = findViewById<View>(R.id.rv_offergallery) as RecyclerView
        tvOfferImageCount = findViewById(R.id.tv_offer_image_total_count)
        tvOfferImageCurrentPosition = findViewById(R.id.tv_offer_image_current_position)
        tvViewAllInHd = findViewById(R.id.tv_view_all_in_hd)

        GlideApp.with(this)
                .load(IMAGE_URL +"store/"+ s_img)
                .placeholder(R.drawable.iofferbh_placeholder)
                .into(iv_coupon_gallery_logo)

        tv_coupon_gallery_title.setText(s_name)
    }

    private fun setRecyclerView() {
        recyclerPdpGallery!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setOfferAdapter() {
        offerGalleryImageAdapter = CouponGalleryMenuImageAdapter(this@CouponGalleryActivity, s_menu_img/*this,*/ /*ArrayList(offersItem!!.offerImages), false*/)
        mViewPagerMultiTouchFix!!.adapter = offerGalleryImageAdapter
        val mCirclePageIndicator = findViewById<View>(R.id.view_pager_indicator) as CirclePageIndicator
        mCirclePageIndicator.setViewPager(mViewPagerMultiTouchFix)
        mViewPagerMultiTouchFix!!.currentItem = position

        mViewPagerMultiTouchFix!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(currentPosition: Int) {
                position = currentPosition
                setOfferImageCurrentPageData()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


    }


    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.iv_offer_thumbnail_image -> {
                position = Integer.parseInt(v.getTag(R.string.click_position_key).toString())
                mViewPagerMultiTouchFix!!.currentItem = position
            }

            R.id.ll_custom_ab_right -> showSnackbar(v, "Coming soon")

            R.id.iv_share_offer -> downloadImage()

            R.id.iv_back_arrow -> onBackPressed()


            R.id.tv_view_all_in_hd -> notifyAdapter()
        }
    }


    private fun notifyAdapter() {
        // offerGalleryImageAdapter = OfferGalleryImageAdapter(this@CouponGalleryActivity, this, ArrayList(offersItem!!.offerImages), true)
        mViewPagerMultiTouchFix!!.adapter = offerGalleryImageAdapter
        mViewPagerMultiTouchFix!!.currentItem = position
    }


    private fun downloadImage() {
        showProgressDialog(getString(R.string.msg_loading))
        //        SimpleTarget<Bitmap> target = ;
        Glide.with(this)
                .asBitmap()
                .load(com.accentrs.apilibrary.utils.Constants.BASE_URL + offersItem!!.offerImages[position].url)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        this.mDetector!!.onTouchEvent(motionEvent)
        return true
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
