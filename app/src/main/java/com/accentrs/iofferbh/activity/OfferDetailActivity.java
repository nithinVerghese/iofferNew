package com.accentrs.iofferbh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.apilibrary.utils.SessionSharedPreference;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.companyoffer.OfferAdapter;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.model.companydetail.CompanyDetailModel;
import com.accentrs.iofferbh.model.home.LocationsItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.service.WishlistService;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_REMOVE_RESULT;
import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_RESULT;

public class OfferDetailActivity extends BaseActivity {

    private RecyclerView rvOfferDetail;
    private LinearLayoutManager mLayoutManager;

    private RelativeLayout rlProgress;
    private ProgressBar pbHome;
    private CompanyDetailModel companyModel;

    private OfferAdapter companyOfferDetailAdapter;
    private OffersItem offersItem;

    private boolean offerWishlistStatus;
    private boolean currentWishlistStatus;

    private HashMap<String, String> bookmarksItemHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        initializeViews();
        fetchIntentData();
        fetchFavOfferData();
    }

    private void initializeToolbar() {
//        setTitleTextView(offersItem.getCompanyNameEn());
        setIofferBhLogo();
        setTitleTextView(getString(R.string.app_name));
        setRightIconDrawableVisibility(true);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
    }

    private void fetchFavOfferData() {
        bookmarksItemHashMap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();

    }


    private void setFavOfferImageDataWithStatus() {

        if (bookmarksItemHashMap.containsKey(offersItem.getId())) {
            offerWishlistStatus = true;
            setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav_selected));
        } else {
            offerWishlistStatus = false;
            setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav_unselected));
        }

        currentWishlistStatus = offerWishlistStatus;

    }

    private void setFavOfferImageDataWithCurrentStatus() {

        if (bookmarksItemHashMap.containsKey(offersItem.getId())) {
            currentWishlistStatus = true;
            setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav_selected));
        } else {
            currentWishlistStatus = false;
            setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav_unselected));
        }
    }


    public void fetchIntentData() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            offersItem = (OffersItem) bundle.getSerializable(Constants.OFFER_DATA_KEY);
            initializeToolbar();
            fetchFavOfferData();
            setFavOfferImageDataWithStatus();
            hitUserOfferViewApi();
            setOfferDetailAdapter();
            fetchCompanyDetail();
        }

    }


    private void initializeViews() {
        rvOfferDetail = findViewById(R.id.rv_offer_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvOfferDetail.setLayoutManager(linearLayoutManager);

        rlProgress = findViewById(R.id.rl_home_progress);
        pbHome = findViewById(R.id.pb_home);
    }

    private void setCompanyData() {

    }


    private void fetchCompanyDetail() {
//        showHomeProgress();
        Results mResult = new Results(this);
        mResult.companyDetail(offersItem.getId());
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
//                hideHomeProgress();
                companyModel = new Gson().fromJson(response.getStringResponse().toString(), CompanyDetailModel.class);
                setCompanyDetailData();

            }

            @Override
            public void onError(String error) {
//                hideHomeProgress();
            }
        });

    }


    private void setOfferDetailAdapter() {
        companyOfferDetailAdapter = new OfferAdapter(this, offersItem);
        rvOfferDetail.setAdapter(companyOfferDetailAdapter);
    }


    private void showHomeProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        pbHome.setVisibility(View.VISIBLE);
    }


    private void hideHomeProgress() {
        rlProgress.setVisibility(View.GONE);
        pbHome.setVisibility(View.GONE);
    }


    private void setCompanyDetailData() {
        if (companyOfferDetailAdapter != null) {
            companyOfferDetailAdapter.addCompanyOfferDetail(companyModel);
        }
    }


    private void hitUserOfferViewApi() {
        String userId = new SessionSharedPreference(getApplicationContext()).getUserId();

        if (!TextUtils.isEmpty(userId)) {

            Results mResults = new Results(this);
            mResults.userOfferView(createOfferViewJsonObject());
            mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
                @Override
                public void onSuccess(ResponseType response) {
                }

                @Override
                public void onError(String error) {

                }
            });


        }


    }


    private JSONObject createOfferViewJsonObject() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("offer_id", offersItem.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.ll_custom_ab_right:
                callAddToWishlist();
                break;

            case R.id.ll_custom_ab_left:

                finishActvitiy();
                break;

        }

    }

    private void callAddToWishlist() {
        if (Utils.isConnectedToInternet(this)) {

            if (bookmarksItemHashMap.containsKey(offersItem.getId())) {
                removeWishlist(offersItem.getId());
            } else {
                addToWishlist(offersItem.getId());
            }


        } else {
            showToast(getString(R.string.error_no_internet_msg));
        }
    }


    private void addToWishlist(String offerId) {
        String userDeviceId = new SharedPreferencesData(this).getUserId();
        if (TextUtils.isEmpty(userDeviceId)) {
            WishlistService.startActionDevice(this, offerId, new WishlistReceiver(new Handler()));
        } else {
            WishlistService.startActionBookmark(this, offerId, new WishlistReceiver(new Handler()));
        }

    }

    private void removeWishlist(String offerId) {
        String userDeviceId = new SharedPreferencesData(this).getUserId();

        WishlistService.startActionRemoveBookmark(this, offerId, new WishlistReceiver(new Handler()));

    }


    private class WishlistReceiver extends ResultReceiver {

        public WishlistReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == WISHLIST_RESULT) {


                if (resultData != null) {
                    String bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY);
                    boolean bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY);
                    if (bookmarkStatus) {
                        addBookmarkData();
                        setFavOfferImageDataWithCurrentStatus();
                    }
                    showSnackbar(rvOfferDetail, bookmarkMessgage);

                }


            } else if (resultCode == WISHLIST_REMOVE_RESULT) {
                if (resultData != null) {
                    String bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY);
                    boolean bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY);
                    if (bookmarkStatus) {
                        removeBookmarkData();
                        setFavOfferImageDataWithCurrentStatus();
                    }
                    showSnackbar(rvOfferDetail, bookmarkMessgage);

                }


            }
        }
    }


    private void addBookmarkData() {

        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();
        bookmarkHashmap.put(offersItem.getId(), offersItem.getNameEn());
        ((IOfferBhApplication) getApplicationContext()).setUserBookmarkList(bookmarkHashmap);
    }

    private void removeBookmarkData() {
        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();
        if (bookmarkHashmap != null && bookmarkHashmap.size() == 0)
            return;

        if (bookmarkHashmap != null) {
            bookmarkHashmap.remove(offersItem.getId());
            ((IOfferBhApplication) getApplicationContext()).setUserBookmarkList(bookmarkHashmap);

        }

    }


    @Override
    public void onBackPressed() {
        finishActvitiy();
    }

    private void finishActvitiy() {
        if (com.accentrs.iofferbh.utils.Constants.IS_FROM_DEEPLINK) {
            com.accentrs.iofferbh.utils.Constants.IS_FROM_DEEPLINK = false;
            goToHomeScreenActivity();
        } else {
            goBack();
        }

    }

    private void goToHomeScreenActivity() {
        Intent mHomeScreenActivity = new Intent(this, HomeScreenActivity.class);
        mHomeScreenActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mHomeScreenActivity);
        finish();
    }

    private void goBack() {
        Intent intent = getIntent();


        if (currentWishlistStatus == offerWishlistStatus) {
            setResult(RESULT_OK, intent);

        } else {
            setResult(RESULT_CANCELED, intent);

        }
        finish();
    }


}


