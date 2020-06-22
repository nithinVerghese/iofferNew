package com.accentrs.iofferbh.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.company.CompanyOfferAdapter;
import com.accentrs.iofferbh.adapter.search.SearchOfferAdapter;
import com.accentrs.iofferbh.adapter.wishlist.WishlistOfferAdapter;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.model.bookmark.BookmarkModel;
import com.accentrs.iofferbh.model.bookmark.BookmarksItem;
import com.accentrs.iofferbh.model.home.HomeScreenModel;
import com.accentrs.iofferbh.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends HeaderActivity {


    private LinearLayout llSearchParent;
    private RecyclerView rvSearch;
    private HomeScreenModel homeScreenModel;
    private TextView tvNoSearch;
    private EditText mSearchEditText;
    private ImageView ivSearchOffer;
    private RelativeLayout rlSearchBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        intializeViews();
        setListener();
    }


    private void intializeViews() {
        rvSearch = findViewById(R.id.rv_search_offer);
        tvNoSearch = findViewById(R.id.tv_no_search);
        mSearchEditText = findViewById(R.id.et_main_search);
        ivSearchOffer = findViewById(R.id.iv_main_search);
        llSearchParent = findViewById(R.id.ll_search_parent);
        rlSearchBack = findViewById(R.id.rl_search_back);

        mSearchEditText.requestFocus();
    }

    private void setListener() {
        ivSearchOffer.setOnClickListener(this);
        rlSearchBack.setOnClickListener(this);

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });


    }



    private void hitSearchApi(String searchText) {

        showProgressDialog(getString(R.string.msg_loading));

        Results mResults = new Results(this);
        mResults.searchOfferApi(searchText);
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();
                homeScreenModel = new Gson().fromJson(response.getStringResponse().toString(), HomeScreenModel.class);
                setWishlistAdapter();


            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
            }
        });


    }

    private void setWishlistAdapter() {
        if (homeScreenModel != null && homeScreenModel.getOffers().size() > 0) {
            tvNoSearch.setVisibility(View.GONE);
            rvSearch.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            SearchOfferAdapter wishlistOfferAdapter = new SearchOfferAdapter(this, homeScreenModel.getOffers(), R.layout.row_company_offer_grid_layout);
            rvSearch.setAdapter(wishlistOfferAdapter);
        } else {
            rvSearch.setVisibility(View.GONE);
            tvNoSearch.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.iv_main_search:


                performSearch();

                break;


            case R.id.rl_search_back:
                finish();
                break;


        }

    }


    private boolean isValid() {

        if (TextUtils.isEmpty(mSearchEditText.getText().toString())) {
            return false;
        }
        return true;
    }


    private void performSearch() {
        if (isValid()) {

            if (Utils.isConnectedToInternet(SearchActivity.this)) {
                hideKeyboard(llSearchParent);
                hitSearchApi(mSearchEditText.getText().toString());
            } else {
                showToast(getString(R.string.error_no_internet_msg));
            }

        } else {
            showToast(getString(R.string.text_enter_search_keyword));
        }
    }


}
