package com.accentrs.iofferbh.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.company.CompanyListOfferAdapter;
import com.accentrs.iofferbh.adapter.home.HomeAdapter;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.fcm.FCM_RegistrationIntentService;
import com.accentrs.iofferbh.fcm.FCM_SharedPreferenceData;
import com.accentrs.iofferbh.fragment.AdsPopupDialogFragment;
import com.accentrs.iofferbh.fragment.BetterLuckNextTimeFragment;
import com.accentrs.iofferbh.fragment.FragmentDrawer;
import com.accentrs.iofferbh.fragment.LuckyWheelGameFragment;
import com.accentrs.iofferbh.interfaces.AdsPopupListener;
import com.accentrs.iofferbh.model.adspopup.AdsPopupResponse;
import com.accentrs.iofferbh.model.coupon.CouponModuleResponse;
import com.accentrs.iofferbh.model.home.HomeScreenModel;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.model.spinninggame.SpinningGameModel;
import com.accentrs.iofferbh.retrofit.ApiServices;
import com.accentrs.iofferbh.service.WishlistService;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;

import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_REMOVE_RESULT;
import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_RESULT;

import static com.accentrs.iofferbh.activity.multi.press;

public class HomeScreenActivity extends DrawerActivity implements FragmentDrawer.DrawerEventsCallback, FragmentDrawer.FragmentDrawerListener, View.OnClickListener, AdsPopupListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 123;
    public static final String TAG = HomeScreenActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private FragmentDrawer drawerFragment;
    private boolean backPressedToExitOnce = false;
    private Toast toast = null;
    private RecyclerView mOffersRecyclerView;
    private LinearLayoutManager mLayoutManager;



    private RequestQueue mQueue;

    public int PAGE_COUNT = 1;
    public int PAGE_SIZE = 10;
    private boolean hit_api = true;
    private boolean noAPIHit;
    private boolean loading = true;
    private int companyId;
    private HomeScreenModel companyOfferModel;
    private RelativeLayout rlProgress;
    private ProgressBar pbHome;
    private HomeAdapter homeAdapter;
    private HomeScreenModel homeScreenModel;
    private View homeParentView;
    private boolean homeSelectedGrid = false;
    private HashMap<String, String> bookmarksItemHashMap;
    private FloatingActionButton fabScrollToTop;
    private SwipeRefreshLayout rvOfferSwipeRefreshLayout;
    private boolean isHomeOfferRefreshing;
    private boolean isLastItemReached;
    private boolean isGameEnabled;
    boolean mIsStateAlreadySaved = false;
    private String message = "";
    boolean mPendingShowGameDialog = false;
    boolean mPendingShowAdPopuDialog = false;
    private boolean isAdsPopupEnabled;
    private boolean isGameDataLoaded = false;
    private LuckyWheelGameFragment spinningWheelFragment;

    public static String coupon_module;

    public static String my_coupon_module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        press=0;

        mQueue = Volley.newRequestQueue(this);
        //jsonpars();
        initializeViews();
        initToolBar();
        setNavigationDrawer();
        initializeRecyclerView();
        setScrollListener();
        fetchOffers();
    }

    private void initializeViews() {
        rlProgress = findViewById(R.id.rl_home_progress);
        rvOfferSwipeRefreshLayout = findViewById(R.id.rv_offers_swipe);
        pbHome = findViewById(R.id.pb_home);
        homeParentView = findViewById(R.id.cl_home);
        fabScrollToTop = findViewById(R.id.fab_category);
        fabScrollToTop.setOnClickListener(this);


        rvOfferSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //jsonpars();
                        reinitializeOffersData();
                        homeAdapter = null;
                        isHomeOfferRefreshing = true;
                        fetchOffers();

                    }
                }
        );
    }


//    public void jsonpars(){
//
//        String url = "https://dev.sayg.co/api/coupon_module";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            JSONArray jsonArray =response.getJSONArray("data");
//                            JSONObject data = jsonArray.getJSONObject(0);
//                             coupon_module = data.getString("coupon_module");
//                             my_coupon_module = data.getString("my_coupon_module");
//
//                            //Toast.makeText(HomeScreenActivity.this, "coupon = "+coupon_module+"\n my_coupon_module = "+my_coupon_module, Toast.LENGTH_SHORT).show();
//
////                            if(Integer.parseInt(coupon_module) == 0){
////
////                                ImageView ivCoupon = findViewById(R.id.iv_coupon);
////                                ivCoupon.setVisibility(View.GONE);
////
////                            }
////                            else {
////                                ImageView ivCoupon = findViewById(R.id.iv_coupon);
////                                ivCoupon.setVisibility(View.VISIBLE);
////                            }
////
////                            if(Integer.parseInt(my_coupon_module) == 0){
////
////                                ImageView ivCoupon = findViewById(R.id.iv_my_coupon);
////                                ivCoupon.setVisibility(View.GONE);
////
////                            }
////                            else {
////                                ImageView ivCoupon = findViewById(R.id.iv_my_coupon);
////                                ivCoupon.setVisibility(View.VISIBLE);
////                            }
//
//                            //fetchOffers();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(HomeScreenActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//        mQueue.add(request);
//
//    }


    private void initializeRecyclerView() {
        mOffersRecyclerView = (RecyclerView) findViewById(R.id.rv_offers);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mOffersRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void initToolBar() {
        setIofferBhLogo();
        setSearchIconVisibility(true);
        setSearchIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_list));

        setRightIconDrawableVisibility(true);
        setRightIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_white));

        setWishlistIconVisibility(true);
        setWishlistIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_show_wishlist));

        setCategoryIconVisibility(false);
        setGameIcon();
    }

    private void setGameIcon() {
        isGameEnabled = new SharedPreferencesData(this).getUserGamePlayedStatus();
        setCategoryIconVisibility(true);
        if (isGameEnabled) {
            setCategoryIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_game_enabled));
        } else {
            setCategoryIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_game_disabled));
        }
    }

    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    public void onOpen() {
    }

    @Override
    public void onClose() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            backPressTwiceToExit();
        }
    }

    private void backPressTwiceToExit() {
        if (backPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.backPressedToExitOnce = true;
            showToastMsg(getString(R.string.press_back_again_to_exit_text));
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;
                }
            }, 2000);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        killToast();
        mIsStateAlreadySaved = true;
    }


    private void showToastMsg(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }

        // Showing toast finally
        this.toast.show();
    }

    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    private void initApp() {
        startFCMRegistrationService();
    }

    private void startFCMRegistrationService() {

        boolean isTokenGenerated = FCM_SharedPreferenceData.getInstance(this.getApplicationContext()).getFCM_Token() != null;

        if ((!isTokenGenerated) | (!new SharedPreferencesData(this).getRegistrationTokenStatus())) {
            Intent intent = new Intent(this, FCM_RegistrationIntentService.class);
            startService(intent);
        }

    }


    private void fetchOffers() {
        if (!isHomeOfferRefreshing)
            showHomeProgress();
        Results mResult = new Results(this);
        mResult.homeOffers();
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                isHomeOfferRefreshing = false;
                rvOfferSwipeRefreshLayout.setRefreshing(false);
                homeScreenModel = new Gson().fromJson(response.getStringResponse().toString(), HomeScreenModel.class);

                //Log.e("force_upp",String.valueOf(homeScreenModel));

                if (checkAppUpdateStatus(homeScreenModel)) {

                    showAppUpdateDialog(homeScreenModel.isForceUpdate());
                    setHomeAdapter();
                    hideHomeProgress();
                } else {
                    setHomeAdapter();
                    hideHomeProgress();
                }

                hitSpinningWheelGameApi();

            }

            @Override
            public void onError(String error) {
                isHomeOfferRefreshing = false;
                rvOfferSwipeRefreshLayout.setRefreshing(false);
                if (!isHomeOfferRefreshing)
                    hideHomeProgress();
            }
        });

    }

    private boolean checkAppUpdateStatus(HomeScreenModel companyOfferModel) {

        try {
            int currentAppVersion = Utils.getApplicationVersion(this);
            Log.e("version_nn",String.valueOf(currentAppVersion));
            Log.e("force_upp",String.valueOf(companyOfferModel));
            if (companyOfferModel != null && companyOfferModel.getAppVersion() != null) {
                Log.e("force_upp",String.valueOf(companyOfferModel.getAppVersion()));

                if (companyOfferModel.getAppVersion().length() > 0) {
                    int latestAppVersion = Integer.parseInt(companyOfferModel.getAppVersion());
                    //Log.e("force_upp",String.valueOf(latestAppVersion));

                    if (latestAppVersion > currentAppVersion) {

                        //Log.e("force_upp",String.valueOf(latestAppVersion));

                        return true;
                    }

                }
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean forceupdate(HomeScreenModel companyOfferModel) {

        try {
            int currentAppVersion = Utils.getApplicationVersion(this);
            Log.e("version_nn",String.valueOf(currentAppVersion));
            Log.e("force_upp",String.valueOf(companyOfferModel));
            if (companyOfferModel != null && companyOfferModel.getAppVersion() != null) {
                Log.e("force_upp",String.valueOf(companyOfferModel.getAppVersion()));

                if (companyOfferModel.getAppVersion().length() > 0) {
                    int latestAppVersion = Integer.parseInt(companyOfferModel.getAppVersion());
                    //Log.e("force_upp",String.valueOf(latestAppVersion));

                    if (latestAppVersion > currentAppVersion) {

                        //Log.e("force_upp",String.valueOf(latestAppVersion));

                        return true;
                    }

                }
            }

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private void setHomeAdapter() {
        if (homeAdapter == null) {
            homeAdapter = new HomeAdapter(this, this, homeScreenModel, companyId, homeSelectedGrid);
            mOffersRecyclerView.setAdapter(homeAdapter);
        } else {
            homeAdapter.addAll(new ArrayList<OffersItem>(companyOfferModel.getOffers()), homeSelectedGrid);
        }

        loading = true;
        hit_api = false;


    }

    private void setHomeCompanyOffersAdapter() {

        if (homeAdapter == null) {
            homeAdapter = new HomeAdapter(this, this, homeScreenModel, companyId, homeSelectedGrid);
            mOffersRecyclerView.setAdapter(homeAdapter);
        } else {
            homeAdapter.setCompanyOfferAdapter(new ArrayList<OffersItem>(companyOfferModel.getOffers()), companyId, homeSelectedGrid);
            homeAdapter.notifyItemChanged(homeAdapter.getItemCount() - 1);
        }

        loading = true;
        hit_api = false;


    }


    private void showHomeProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        pbHome.setVisibility(View.VISIBLE);
    }


    private void hideHomeProgress() {
        rlProgress.setVisibility(View.GONE);
        pbHome.setVisibility(View.GONE);
    }


    private void setScrollListener() {

        mOffersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (Utils.isConnectedToInternet(HomeScreenActivity.this)) {


                    if (dy > 0) //check for scroll downmLayoutManager
                    {

                        int visibleItemCount = mLayoutManager.getChildCount();
                        int totalItemCount = mLayoutManager.getItemCount();
                        int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                        if (loading && !hit_api && !noAPIHit) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                loading = false;
                                hit_api = true;
                                PAGE_COUNT = PAGE_COUNT + 1;
                                hitCompanyOfferApi();
                            }
                        }
                    } else {
                        loading = true;
                    }

                } else {
                }

                if (dy > 0 && fabScrollToTop.getVisibility() == View.GONE) {
                    setScrollToTopFabVisibility(true);
                } else if (dy < 0 && fabScrollToTop.getVisibility() == View.VISIBLE) {
                    setScrollToTopFabVisibility(false);
                }


            }
        });

    }


    private void hitCompanyOfferApi() {
        String companyOfferUrl = companyId + "&" + Constants.PAGE_KEY + "=" + PAGE_COUNT + "&" + Constants.PAGE_SIZE_KEY + "=" + PAGE_SIZE;

        Results mResult = new Results(this);
        mResult.companyOffers(companyOfferUrl);
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                companyOfferModel = new Gson().fromJson(response.getStringResponse().toString(), HomeScreenModel.class);

                if (PAGE_COUNT > 1) {
                    if (companyOfferModel.getOffers() != null && companyOfferModel.getOffers().size() > 0) {
                        setHomeAdapter();
                    } else {
                        noAPIHit = true;
                        isLastItemReached = true;
                    }
                } else {

                    if (companyOfferModel.getOffers() != null && companyOfferModel.getOffers().size() == 0) {
                        showSnackbar(homeParentView, getString(R.string.offer_not_found_text));
                    }

                    setHomeCompanyOffersAdapter();
                }

            }

            @Override
            public void onError(String error) {
            }
        });

    }

    private void setScrollToTopFabVisibility(boolean status) {
        if (status && fabScrollToTop.getVisibility() == View.GONE) {
            fabScrollToTop.setVisibility(View.VISIBLE);
        } else {
            fabScrollToTop.setVisibility(View.GONE);
        }
    }


    public void setCompanyOffers(int companyOffersId) {
        companyId = companyOffersId;
        reinitializeOffersData();
        hitCompanyOfferApi();
    }

    private void reinitializeOffersData() {
        PAGE_COUNT = 1;
        noAPIHit = false;
        loading = true;
        hit_api = false;


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {


            case R.id.ll_custom_ab_left:
                onOpen();
                break;

            case R.id.ll_custom_ab_right:
                startSearch();

                break;


            case R.id.ll_search:

                if (homeSelectedGrid) {
                    homeSelectedGrid = false;
                    setListIconToolbar();
                } else {
                    homeSelectedGrid = true;
                    setGridIconToolbar();
                }
                changeHomeScreenLayout();
                break;


            case R.id.ll_wishlist:
                Intent intent = new Intent(HomeScreenActivity.this, WishlistActivity.class);
                startActivity(intent);
                break;


            case R.id.ll_category:

                if (Utils.isConnectedToInternet(this)) {
                    if (isGameEnabled) {
                        Intent mIntent = new Intent(HomeScreenActivity.this, SpinningWheelActivity.class);
                        startActivity(mIntent);
                    } else {
                        showBetterLuckNextTimeDialogFragment(message);
                    }

                } else {
                    showSnackbar(homeParentView, getString(R.string.error_no_internet_msg));
                }

                break;

            case R.id.fab_category:

                scrollToTop();
                break;


        }

    }

    private void showBetterLuckNextTimeDialogFragment(String message) {
        BetterLuckNextTimeFragment betterLuckNextTimeFragment = new BetterLuckNextTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        betterLuckNextTimeFragment.setArguments(bundle);
        betterLuckNextTimeFragment.setCancelable(false);
        betterLuckNextTimeFragment.show(getSupportFragmentManager(), "BetterLuckNextTimeFragment");
    }

    private void changeHomeScreenLayout() {

        if (homeAdapter != null) {
            homeAdapter.changeHomeLayout(homeSelectedGrid);
        }

    }

    private void setListIconToolbar() {
        setSearchIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_list));
    }

    private void setGridIconToolbar() {
        setSearchIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home_grid));
    }


    private void startSearch() {
        Intent mIntent = new Intent(this, SearchActivity.class);
        startActivity(mIntent);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CompanyListOfferAdapter.WISHLIST_ACTION_RESULT:


                if (resultCode == RESULT_CANCELED) {
                    notifyHomeListViewAdapter();
                }

                break;


        }
    }

    private void shareOffer(String imagePath, OffersItem offersItem) {
        String shareUrl = Constants.OFFER_SHARE_URL.concat(Constants.PARAMETER_QUES)
                .concat(Constants.ID).concat(Constants.PARAMETER_EQUALS).concat(offersItem.getId());
        ShareCompat.IntentBuilder
                .from(this) // getActivity() or activity field if within Fragment
                .setStream(Uri.parse(imagePath))
                .setText(offersItem.getDescriptionEn().concat("\n").concat(shareUrl))
                .setType("image/*") // most general text sharing MIME type
                .setChooserTitle("Share with")
                .startChooser();

    }

    public void downloadShareImage(final OffersItem offersItem) {
        showProgressDialog(getString(R.string.msg_loading));
        Glide.with(this)
                .asBitmap()
                .load(com.accentrs.apilibrary.utils.Constants.BASE_URL.concat(offersItem.getOfferImages().get(0).getUrl()))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap newBitmap, @Nullable Transition<? super Bitmap> transition) {
                        String filesDir = getExternalFilesDir(null) + File.separator + "Image";
                        File dir = new File(filesDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File storeFile = new File(dir, "image1.jpg");
                        storeFile.deleteOnExit();
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        try {
                            storeFile.createNewFile();
                            FileOutputStream fo = new FileOutputStream(storeFile);
                            fo.write(bytes.toByteArray());
                            fo.close();
                            bytes.close();
                            dismissProgressDialog();
                            shareOffer(storeFile.getPath(), offersItem);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void fetchFavOfferData() {
        bookmarksItemHashMap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();

    }

    public void callAddToWishlist(OffersItem offersItem) {
        if (Utils.isConnectedToInternet(this)) {
            fetchFavOfferData();
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
        WishlistService.startActionRemoveBookmark(this, offerId, new WishlistReceiver(new Handler()));
    }

    @Override
    public void showSpinningWheelSpinner() {
        boolean isGameEnabled = new SharedPreferencesData(this).getUserGamePlayedStatus();

        if (isGameEnabled) {
            showPlayGameDialog();
        }

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
                    String offerId = resultData.getString(com.accentrs.iofferbh.utils.Constants.OFFER_ID_KEY);
                    if (bookmarkStatus) {
                        addBookmarkData(offerId);
                        if (homeAdapter != null) {
                            homeAdapter.refreshCompanyOffersListing();
                        }

                    }
                    showSnackbar(homeParentView, bookmarkMessgage);
                }


            } else if (resultCode == WISHLIST_REMOVE_RESULT) {
                if (resultData != null) {
                    String bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY);
                    boolean bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY);
                    String offerId = resultData.getString(com.accentrs.iofferbh.utils.Constants.OFFER_ID_KEY);
                    if (bookmarkStatus) {
                        removeBookmarkData(offerId);
                        if (homeAdapter != null) {
                            homeAdapter.refreshCompanyOffersListing();
                        }
                    }
                    showSnackbar(homeParentView, bookmarkMessgage);
                }


            }
        }
    }


    private void addBookmarkData(String offerId) {

        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();
        bookmarkHashmap.put(offerId, "");
        ((IOfferBhApplication) getApplicationContext()).setUserBookmarkList(bookmarkHashmap);
    }

    private void removeBookmarkData(String offerId) {
        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();
        if (bookmarkHashmap != null && bookmarkHashmap.size() == 0)
            return;

        if (bookmarkHashmap != null) {
            bookmarkHashmap.remove(offerId);
            ((IOfferBhApplication) getApplicationContext()).setUserBookmarkList(bookmarkHashmap);

        }

    }

    private void notifyHomeListViewAdapter() {
        if (!homeSelectedGrid) {
            if (homeAdapter != null)
                homeAdapter.refreshCompanyOffersListing();
        }
    }


    public void refreshActivity(){
        Intent i = new Intent(this, HomeScreenActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("resume_val","resume");
        mIsStateAlreadySaved = false;
        reinitializeOffersData();

        setGameIcon();
        notifyHomeListViewAdapter();
        checkSpinWheelStatus();
        //jsonpars();



        press = 0;

    }

    private void checkSpinningWheelGameAndPopupStatus() {

        boolean adsPopupStatus = new SharedPreferencesData(this).getAdsPopupStatus();


        if (adsPopupStatus) {
            showAdsPopUpDialog();
        } else if (isGameEnabled) {
            //showAdsPopUpDialog();
            showPlayGameDialog();
        }
    }

    private void checkSpinWheelStatus() {
        if (mPendingShowGameDialog) {
            mPendingShowGameDialog = false;
            showPlayGameDialog();
        }
    }


    public void scrollToTop() {
        mOffersRecyclerView.smoothScrollToPosition(0);
        setScrollToTopFabVisibility(false);
    }

    private void showAppUpdateDialog(final boolean isCancellable) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.text_new_version_available));

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.text_new_version_available_desc));

        alertDialog.setCancelable(!isCancellable);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton(getString(R.string.text_update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                updateApp();
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.text_no_thanks), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (isCancellable)
                    finish();

                dialog.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }


    private void updateApp() {
        try {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(i);

            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.technapse.iofferbh")));
            }
        } catch (Exception e) {
        }
        finish();
    }

    private void hitSpinningWheelGameApi() {
        showProgressDialog(getString(R.string.msg_loading));
        Results mResults = new Results(this);
        mResults.fetchSpinningWheelGame();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                if (response != null && response.getStringResponse().toString() != null) {
                    new SharedPreferencesData(HomeScreenActivity.this).setSpinningWheelData(response.getStringResponse().toString());
                } else {
                    new SharedPreferencesData(HomeScreenActivity.this).setSpinningWheelData("");
                }

                hitAdsPopupApi();
            }

            @Override
            public void onError(String error) {
                if (error != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(error);
                        if (jsonObject.has("status")) {
                            if (!jsonObject.getBoolean("status")) {
                                if (jsonObject.has("message")) {
                                    message = jsonObject.getString("message");
                                    isGameEnabled = false;
                                    new SharedPreferencesData(HomeScreenActivity.this).setUserGamePlayedStatus(isGameEnabled);
                                    setGameIcon();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hitAdsPopupApi();
            }
        });

    }


    private void setSpinWheelGameData() {

        String spinWheelGameData = new SharedPreferencesData(this).getSpinningWheelData();

        SpinningGameModel spinningGameModel = new Gson().fromJson(spinWheelGameData, SpinningGameModel.class);

        if (spinningGameModel != null) {
            if (spinningGameModel.isStatus()) {
                if (spinningGameModel.getData() != null) {

                    if (spinningGameModel.isStatus() && spinningGameModel.getData().getWheelItems() != null) {
                        isGameEnabled = true;
                        message = "";
                        new SharedPreferencesData(HomeScreenActivity.this).setUserGamePlayedStatus(isGameEnabled);
                        setGameIcon();
                        setAdsPopupData();
                    } else {
                        isGameEnabled = true;
                        message = "";
                        new SharedPreferencesData(HomeScreenActivity.this).setUserGamePlayedStatus(isGameEnabled);
                        setAdsPopupData();
                    }
                }
            } else {
                isGameEnabled = false;
                message = spinningGameModel.getMessage();
                new SharedPreferencesData(HomeScreenActivity.this).setUserGamePlayedStatus(isGameEnabled);
                setGameIcon();
                setAdsPopupData();
            }
        }

    }


    private void hitAdsPopupApi() {
        Results mResults = new Results(this);
        mResults.fetchAdsPopup();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
                isGameDataLoaded = true;
                if (response != null && response.getStringResponse().toString() != null) {
                    new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupData(response.getStringResponse().toString());
                } else {
                    new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupData("");
                }
                setSpinWheelGameData();

            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
                if (error != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(error);
                        if (jsonObject.has("status")) {
                            if (!jsonObject.getBoolean("status")) {
                                if (jsonObject.has("message")) {
                                    message = jsonObject.getString("message");
                                    isAdsPopupEnabled = false;
                                    new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupStatus(isAdsPopupEnabled);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void setAdsPopupData() {

        String adsPopupData = new SharedPreferencesData(this).getAdsPopupData();

        AdsPopupResponse adsPopupModel = new Gson().fromJson(adsPopupData, AdsPopupResponse.class);

        if (adsPopupModel != null) {
            if (adsPopupModel.getStatus() != null) {
                if (adsPopupModel.getData() != null) {

                    if (adsPopupModel.getStatus() && adsPopupModel.getData() != null) {
                        isAdsPopupEnabled = true;
                        message = "";
                        new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupStatus(isAdsPopupEnabled);
                        new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupData(adsPopupData);
                    }
                }
            } else {
                isAdsPopupEnabled = false;
                message = adsPopupModel.getMessage();
                new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupStatus(isAdsPopupEnabled);
                new SharedPreferencesData(HomeScreenActivity.this).setAdsPopupData("");

            }
        }

        checkSpinningWheelGameAndPopupStatus();

    }


    private void showPlayGameDialog() {
        if (mIsStateAlreadySaved) {
            mPendingShowGameDialog = true;
        } else {
            spinningWheelFragment = new LuckyWheelGameFragment();
            spinningWheelFragment.show(getSupportFragmentManager(), "spinningWheelFragment");
        }
    }

    private void showAdsPopUpDialog() {

        if (mIsStateAlreadySaved) {
            mPendingShowAdPopuDialog = true;
        } else {
            AdsPopupDialogFragment adsPopupDialogFragment = new AdsPopupDialogFragment();
            adsPopupDialogFragment.show(getSupportFragmentManager(), "AdsPopupDialogFragment");
        }
    }


}
