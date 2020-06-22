package com.accentrs.iofferbh.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.accentrs.apilibrary.callback.Results;
import com.accentrs.apilibrary.interfaces.ResponseType;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.wishlist.WishlistOfferAdapter;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.model.bookmark.BookmarkModel;
import com.accentrs.iofferbh.model.bookmark.BookmarksItem;
import com.accentrs.iofferbh.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.transform.Result;

public class WishlistActivity extends BaseActivity {


    private RecyclerView rvWishlist;
    private BookmarkModel bookmarkModel;
    private TextView tvNoWishlist;
    private ArrayList<BookmarksItem> bookmarksItemArrayList;
    private WishlistOfferAdapter wishlistOfferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        initializeToolbar();
        intializeViews();
        setUpRecyclerview();
        hitWishlistApi();
        setScrollListener();
    }


    private void initializeToolbar() {
        setTitleTextView(getString(R.string.wishlist_text));
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }

    private void intializeViews() {
        rvWishlist = findViewById(R.id.rv_wishlist_company_offer);
        tvNoWishlist = findViewById(R.id.tv_no_wishlist);
    }

    private void setUpRecyclerview() {
        final StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rvWishlist.setLayoutManager(mGridLayoutManager);

    }


    private void hitWishlistApi() {

        showProgressDialog(getString(R.string.msg_loading));

        Results mResults = new Results(this);
        mResults.userBookmarkApi();
        mResults.setOnResultsCallBack(new Results.ResultsCallBack() {
            @Override
            public void onSuccess(ResponseType response) {
                dismissProgressDialog();

                bookmarkModel = new Gson().fromJson(response.getStringResponse().toString(), BookmarkModel.class);
                setWishlistAdapter();


            }

            @Override
            public void onError(String error) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setWishlistAdapter();
    }

    private void setWishlistAdapter() {
        HashMap<String, String> wishlistHashmap = ((IOfferBhApplication) getApplicationContext()).getBookmarksItemHashMap();

        if (bookmarkModel != null && bookmarkModel.getBookmarks().size() > 0) {

            bookmarksItemArrayList = new ArrayList<>();

            for (int i = 0; i < bookmarkModel.getBookmarks().size(); i++) {

                if (wishlistHashmap.containsKey(bookmarkModel.getBookmarks().get(i).getId())) {
                    bookmarksItemArrayList.add(bookmarkModel.getBookmarks().get(i));
                }

            }
        }

        if (bookmarksItemArrayList != null && bookmarksItemArrayList.size() > 0) {
            rvWishlist.setVisibility(View.VISIBLE);
            tvNoWishlist.setVisibility(View.GONE);
            wishlistOfferAdapter = new WishlistOfferAdapter(this, this, bookmarksItemArrayList, R.layout.row_company_offer_grid_layout);
            rvWishlist.setAdapter(wishlistOfferAdapter);
            wishlistOfferAdapter.notifyDataSetChanged();
        } else {
            rvWishlist.setVisibility(View.GONE);
            tvNoWishlist.setVisibility(View.VISIBLE);
        }

    }

    private void setScrollListener() {
        rvWishlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy == 0) //check for scroll downmLayoutManager
                {
                    if (wishlistOfferAdapter != null) {
                        wishlistOfferAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
