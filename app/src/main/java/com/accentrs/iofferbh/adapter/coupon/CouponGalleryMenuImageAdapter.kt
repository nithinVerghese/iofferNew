package com.accentrs.iofferbh.adapter.coupon

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.accentrs.apilibrary.utils.Constants
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.helper.GlideApp
import com.accentrs.iofferbh.volley.VolleySingleton
import com.android.volley.toolbox.ImageLoader
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


class CouponGalleryMenuImageAdapter(/*private val couponGalleryActivity: CouponGalleryActivity,*/
        private val mContext: Context, private val arrayList: Array<String>?
        /*, private val imageViewHdStatus: Boolean*/) : PagerAdapter() {
    private val layoutInflater: LayoutInflater
    private val bitmap: Bitmap? = null

    private val mImageLoader: ImageLoader
    private var viewInHdStatus: Boolean = false

    init {
        layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mImageLoader = VolleySingleton.getInstance(mContext).imageLoader

    }

    override fun getCount(): Int {
        return arrayList?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.row_coupon_gallery_menu_image_layout, container, false)


        val viewPageImage = itemView.findViewById<com.accentrs.iofferbh.helper.TouchImageView>(R.id.tv_gallery_image)
        val pbGallery = itemView.findViewById<ProgressBar>(R.id.pb_gallery)

        //val imageUrl = Constants.BASE_URL + arrayList!![position].url
        val imageUrl = Constants.IMAGE_URL +"menu/"+ arrayList?.get(position)
        pbGallery.visibility = View.VISIBLE
        viewPageImage.visibility = View.GONE


        val target = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                pbGallery.visibility = View.GONE
                viewPageImage.visibility = View.VISIBLE


                /*if (!imageViewHdStatus) {
                    Log.d("called ", "notify blurry")
                    viewPageImage.setImageBitmap(resource)
                    viewPageImage.alpha = 0.9f

                    viewInHdStatus = true
                } else {
                    Log.d("called ", "notify new")

                    viewPageImage.setImageBitmap(resource)
                }*/

                viewPageImage.setImageBitmap(resource)
            }

        }
        GlideApp.with(mContext)
                .asBitmap()
                .load(imageUrl)
                .into(target)

        container.addView(itemView)


        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}