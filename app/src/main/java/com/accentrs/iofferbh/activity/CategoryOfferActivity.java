package com.accentrs.iofferbh.activity;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.categoryOffer.HomeCategoryAdapter;
import com.accentrs.iofferbh.model.categoryOffer.CategoryModel;
import com.accentrs.iofferbh.model.company.CompanyModel;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CategoryOfferActivity extends BaseActivity {

    private RecyclerView rvCategoryOffer;

    private RelativeLayout rlProgress;
    private ProgressBar pbHome;
    private CompanyModel companyModel;
    private CategoryModel companyOfferModel;

    private CategoryModel homeCategoryModel;

    private View homeParentView;


    private HomeCategoryAdapter homeCategoryAdapter;
    private LinearLayoutManager mLayoutManager;

    public int PAGE_COUNT = 1;
    public int PAGE_SIZE = 10;
    private boolean hit_api = false;
    private boolean noAPIHit;
    private boolean loading = true;
    private int categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_offer);
        initializeViews();
        initializeToolbar();
        setScrollListener();
        fetchCategoryOffers();
    }

    private void initializeToolbar() {
//        setTitleTextView(offersItem.getCompanyNameEn());
        setIofferBhLogo();
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
    }


    private void initializeViews() {
        rvCategoryOffer = findViewById(R.id.rv_category_offer);
        rlProgress = findViewById(R.id.rl_home_progress);
        pbHome = findViewById(R.id.pb_home);
        homeParentView = findViewById(R.id.rl_cat_home);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCategoryOffer.setLayoutManager(mLayoutManager);

    }

    private void fetchCategoryOffers() {
        showHomeProgress();
        Results mResult = new Results(this);
        mResult.categoryOffers();
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                homeCategoryModel = new Gson().fromJson(response.getStringResponse().toString(), CategoryModel.class);
                setCategoryAdapter();

                hideHomeProgress();

            }

            @Override
            public void onError(String error) {
                hideHomeProgress();
            }
        });

    }

    private void setCategoryAdapter() {

        if (homeCategoryAdapter == null) {
            homeCategoryAdapter = new HomeCategoryAdapter(this, homeCategoryModel, 0);
            rvCategoryOffer.setAdapter(homeCategoryAdapter);
        }

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

        rvCategoryOffer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (Utils.isConnectedToInternet(CategoryOfferActivity.this)) {

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


            }
        });

    }


    private void hitCompanyOfferApi() {

//        showProgressDialog(getString(R.string.msg_loading));


        String categoryOfferUrl = categoryId + "&" + Constants.PAGE_KEY + "=" + PAGE_COUNT + "&" + Constants.PAGE_SIZE_KEY + "=" + PAGE_SIZE;

        Results mResult = new Results(this);
        mResult.categoryOffers(categoryOfferUrl);
        mResult.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {

                companyOfferModel = new Gson().fromJson(response.getStringResponse().toString(), CategoryModel.class);

                if (PAGE_COUNT > 1) {
                    if (companyOfferModel.getOffers() != null && companyOfferModel.getOffers().size() > 0) {
                        setHomeAdapter();
                    } else {
                        noAPIHit = true;
                    }
                } else {

                    if (companyOfferModel.getOffers() != null && companyOfferModel.getOffers().size() == 0) {
                        showSnackbar(homeParentView, getString(R.string.offer_not_found_text));
                    }

                    setHomeCompanyOffersAdapter();
                }

//                dismissProgressDialog();

            }

            @Override
            public void onError(String error) {
//                dismissProgressDialog();
            }
        });

    }

    private void setHomeCompanyOffersAdapter() {

        if (homeCategoryAdapter == null) {
            homeCategoryAdapter = new HomeCategoryAdapter(this, homeCategoryModel, categoryId);
            rvCategoryOffer.setAdapter(homeCategoryAdapter);
        } else {

            homeCategoryAdapter.setCompanyOfferAdapter(new ArrayList<OffersItem>(companyOfferModel.getOffers()), categoryId, true);
            homeCategoryAdapter.notifyDataSetChanged();
        }

        loading = true;
        hit_api = false;


    }


    private void setHomeAdapter() {

        if (homeCategoryAdapter == null) {
            homeCategoryAdapter = new HomeCategoryAdapter(this, homeCategoryModel, categoryId);
            rvCategoryOffer.setAdapter(homeCategoryAdapter);
        } else {

            Log.d("get offers", companyOfferModel.getOffers().size() + "");

            homeCategoryAdapter.addAll(new ArrayList<OffersItem>(companyOfferModel.getOffers()), true);
            homeCategoryAdapter.notifyDataSetChanged();
        }

        loading = true;
        hit_api = false;


    }


    public void setCompanyOffers(int companyOffersId) {
        categoryId = companyOffersId;
        reinitializeOffersData();
        hitCompanyOfferApi();


    }

    private void reinitializeOffersData() {
        PAGE_COUNT = 1;
        noAPIHit = false;
        loading = true;
        hit_api = false;

    }

}
