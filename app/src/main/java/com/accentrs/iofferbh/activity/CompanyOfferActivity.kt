package com.accentrs.iofferbh.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import com.accentrs.apilibrary.callback.Results
import com.accentrs.apilibrary.interfaces.ResponseType
import com.accentrs.apilibrary.utils.Constants
import com.accentrs.iofferbh.R
import com.accentrs.iofferbh.adapter.AdPopupCompanyOfferAdapter
import com.accentrs.iofferbh.application.IOfferBhApplication
import com.accentrs.iofferbh.model.home.HomeScreenModel
import com.accentrs.iofferbh.model.home.OffersItem
import com.accentrs.iofferbh.service.WishlistService
import com.accentrs.iofferbh.service.WishlistService.WISHLIST_REMOVE_RESULT
import com.accentrs.iofferbh.service.WishlistService.WISHLIST_RESULT
import com.accentrs.iofferbh.utils.SharedPreferencesData
import com.accentrs.iofferbh.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_company_offer.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class CompanyOfferActivity : BaseActivity() {

    private var companyId: String? = null
    private var companyOfferAdapter: AdPopupCompanyOfferAdapter? = null
    private var bookmarksItemHashMap: HashMap<String, String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_offer)
        initializeToolbar()
        fetchIntentData()
        rv_company_offers.layoutManager = LinearLayoutManager(this)
        if (companyId != null && companyId!!.isNotEmpty()) {
            hitCompanyOfferApi()
        }
    }

    private fun initializeToolbar() {
        setIofferBhLogo()
        setSearchIconVisibility(false)
        setLeftIconDrawableVisibility(true)
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
    }

    private fun fetchIntentData() {
        if (intent.extras != null) {
            companyId = intent.extras!!.getInt("company_id_key").toString()
        }
    }

    private fun hitCompanyOfferApi() {

        val companyOfferUrl = companyId
        showProgressDialog(getString(R.string.msg_loading))

        val mResult = Results(this)
        mResult.companyOffers(companyOfferUrl)
        mResult.setOnResultsCallBack(object : Results.ResultsCallBack {
            override fun onSuccess(response: ResponseType) {
                dismissProgressDialog()

                val companyOfferModel: HomeScreenModel = Gson().fromJson(response.stringResponse.toString(), HomeScreenModel::class.java)

                if (companyOfferModel.offers != null && companyOfferModel.offers.size > 0) {

                    setAdapter(companyOfferModel.offers)
                }
            }

            override fun onError(error: String) {
                dismissProgressDialog()

            }
        })

    }

    private fun setAdapter(offerList: List<OffersItem>) {
        if (companyOfferAdapter == null) {
            companyOfferAdapter = AdPopupCompanyOfferAdapter(this, offerList)
            rv_company_offers.adapter = companyOfferAdapter
        } else {
            companyOfferAdapter!!.notifyDataSetChanged()
        }
    }

    private fun fetchFavOfferData() {
        bookmarksItemHashMap = (applicationContext as IOfferBhApplication).bookmarksItemHashMap

    }

    fun callAddToWishlist(offersItem: OffersItem) {
        if (Utils.isConnectedToInternet(this)) {
            fetchFavOfferData()
            if (bookmarksItemHashMap!!.containsKey(offersItem.id)) {
                removeWishlist(offersItem.id)
            } else {
                addToWishlist(offersItem.id)
            }


        } else {
            showToast(getString(R.string.error_no_internet_msg))
        }
    }

    private fun addToWishlist(offerId: String) {
        val userDeviceId = SharedPreferencesData(this).userId
        if (TextUtils.isEmpty(userDeviceId)) {
            WishlistService.startActionDevice(this, offerId, WishlistReceiver(Handler()))
        } else {
            WishlistService.startActionBookmark(this, offerId, WishlistReceiver(Handler()))
        }

    }

    private fun removeWishlist(offerId: String) {
        WishlistService.startActionRemoveBookmark(this, offerId, WishlistReceiver(Handler()))
    }

    fun downloadShareImage(offersItem: OffersItem) {
        showProgressDialog(getString(R.string.msg_loading))
        Glide.with(this)
                .asBitmap()
                .load(com.accentrs.apilibrary.utils.Constants.BASE_URL + offersItem.offerImages[0].url)
                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(newBitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        val filesDir = getExternalFilesDir(null).toString() + File.separator + "Image"
                        val dir = File(filesDir)
                        if (!dir.exists()) {
                            dir.mkdirs()
                        }
                        val storeFile = File(dir, "image1.jpg")
                        storeFile.deleteOnExit()
                        val bytes = ByteArrayOutputStream()
                        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                        try {
                            storeFile.createNewFile()
                            val fo = FileOutputStream(storeFile)
                            fo.write(bytes.toByteArray())
                            fo.close()
                            bytes.close()
                            dismissProgressDialog()
                            shareOffer(storeFile.path, offersItem)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                })

    }

    private fun shareOffer(imagePath: String, offersItem: OffersItem) {
        val shareUrl = Constants.OFFER_SHARE_URL + Constants.PARAMETER_QUES + Constants.ID + Constants.PARAMETER_EQUALS + offersItem.id
        ShareCompat.IntentBuilder
                .from(this) // getActivity() or activity field if within Fragment
                .setStream(Uri.parse(imagePath))
                .setText(offersItem.descriptionEn + "\n" + shareUrl)
                .setType("image/*") // most general text sharing MIME type
                .setChooserTitle("Share with")
                .startChooser()

    }

    private inner class WishlistReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultCode == WISHLIST_RESULT) {


                if (resultData != null) {
                    val bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY)
                    val bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY)
                    val offerId = resultData.getString(com.accentrs.iofferbh.utils.Constants.OFFER_ID_KEY)
                    if (bookmarkStatus) {
                        if (offerId != null) {
                            addBookmarkData(offerId)
                        }
                        if (companyOfferAdapter != null) {
                            companyOfferAdapter!!.notifyWishlistDataset()
                        }

                    }
                    showToast(bookmarkMessgage)
                }


            } else if (resultCode == WISHLIST_REMOVE_RESULT) {
                if (resultData != null) {
                    val bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY)
                    val bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY)
                    val offerId = resultData.getString(com.accentrs.iofferbh.utils.Constants.OFFER_ID_KEY)
                    if (bookmarkStatus) {
                        if (offerId != null) {
                            removeBookmarkData(offerId)
                        }
                        if (companyOfferAdapter != null) {
                            companyOfferAdapter!!.notifyWishlistDataset()
                        }
                    }
                    showToast(bookmarkMessgage)
                }


            }
        }
    }

    private fun addBookmarkData(offerId: String) {

        val bookmarkHashmap = (applicationContext as IOfferBhApplication).bookmarksItemHashMap
        bookmarkHashmap[offerId] = ""
        (applicationContext as IOfferBhApplication).setUserBookmarkList(bookmarkHashmap)
    }

    private fun removeBookmarkData(offerId: String) {
        val bookmarkHashmap = (applicationContext as IOfferBhApplication).bookmarksItemHashMap
        if (bookmarkHashmap != null && bookmarkHashmap.size == 0)
            return

        if (bookmarkHashmap != null) {
            bookmarkHashmap.remove(offerId)
            (applicationContext as IOfferBhApplication).setUserBookmarkList(bookmarkHashmap)

        }

    }

}
