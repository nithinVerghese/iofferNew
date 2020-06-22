package com.accentrs.iofferbh.adapter.categoryOffer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.CategoryOfferActivity;
import com.accentrs.iofferbh.helper.RecyclerItemClickListener;
import com.accentrs.iofferbh.model.categoryOffer.CategoryModel;
import com.accentrs.iofferbh.model.home.HomeScreenModel;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.viewholder.MainViewHolder;
import com.accentrs.iofferbh.viewholder.home.HomeCategoryViewHolder;
import com.accentrs.iofferbh.viewholder.home.HomeCompanyOfferHolder;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context mContext;
    private CategoryModel homeScreenModel;
    private HomeScreenModel companyOfferModel;


    private final static int HOME_OFFER_SLIDER_VIEW_TYPE = 5;
    private final static int HOME_CATEGORY_VIEW_TYPE = 10;
    private final static int HOME_COMPANY_VIEW_TYPE = 15;
    private final static int HOME_COMPANY_OFFER_VIEW_TYPE = 20;
    private int companyId;


    private CategoryOfferAdapter companyOfferAdapter;
    private CategoryOfferAdapter companyListOfferAdapter;

    private CategoryAdapter companyAdapter;


    private View view;
    private int currentCompanyId;


    private boolean homeSelectedGrid = true;
    private int homeLayoutStatus;

    private ArrayList<OffersItem> offersItemArrayList;
    final private StaggeredGridLayoutManager mGridLayoutManager;
    final private LinearLayoutManager mLinearLayoutManager;


    public HomeCategoryAdapter(Context mContext, CategoryModel homeScreenModel, int currentCompanyId) {
        this.mContext = mContext;
        this.homeScreenModel = homeScreenModel;
        this.currentCompanyId = currentCompanyId;
        offersItemArrayList = new ArrayList<>(homeScreenModel.getOffers());
        mGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HOME_CATEGORY_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_category_layout, parent, false);
            return new HomeCategoryViewHolder(view);
        } else if (viewType == HOME_COMPANY_OFFER_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_category_offer_layout, parent, false);
            return new HomeCompanyOfferHolder(view);
        } else {
            return null;
        }


    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {




            case HOME_CATEGORY_VIEW_TYPE:

                HomeCategoryViewHolder categoryViewHolder = (HomeCategoryViewHolder) holder;
                setHomeCategoryRecyclerview(categoryViewHolder);

                break;


            case HOME_COMPANY_OFFER_VIEW_TYPE:

                HomeCompanyOfferHolder companyOfferHolder = (HomeCompanyOfferHolder) holder;
                setHomeCompanyOfferRecyclerview(companyOfferHolder);

                break;


        }


    }

    @Override
    public int getItemCount() {

        return 2;


    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return HOME_CATEGORY_VIEW_TYPE;
        } else {
            return HOME_COMPANY_OFFER_VIEW_TYPE;
        }

    }




    private void setHomeCategoryRecyclerview(final HomeCategoryViewHolder viewHolder) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.rvHomeCategory.setLayoutManager(mLayoutManager);


        if (companyAdapter == null) {
            companyAdapter = new CategoryAdapter(mContext, homeScreenModel.getCategories());
            viewHolder.rvHomeCategory.setAdapter(companyAdapter);

            viewHolder.rvHomeCategory.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {


                    if (homeScreenModel.getCategories() != null) {


                        if (position != 0) {
                            if (homeScreenModel.getCategories().get(position - 1).getId() != currentCompanyId) {

                                viewHolder.tvHomeCategoryName.setText(homeScreenModel.getCategories().get(position-1).getNameEn());
                                callCompanyOffer(homeScreenModel.getCategories().get(position - 1).getId());
                            }


                        } else {

                            if (currentCompanyId != position) {
                                viewHolder.tvHomeCategoryName.setText(mContext.getString(R.string.category_all_text));
                                callCompanyOffer(0);
                            }

                        }

                        companyAdapter.setSelectedPosition(position);


                    }


                }

                @Override
                public void onItemLongPress(View childView, int position) {

                }
            }));
        }


    }


    private void callCompanyOffer(int categoryId) {
        ((CategoryOfferActivity) mContext).setCompanyOffers(categoryId);
    }


    private void setHomeCompanyOfferRecyclerview(HomeCompanyOfferHolder viewHolder) {

        if (companyOfferAdapter == null || homeLayoutStatus == 123) {

            homeLayoutStatus = -1;
            if (homeSelectedGrid) {
                viewHolder.rvHomeCompanyOffer.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                viewHolder.rvHomeCompanyOffer.setAdapter(null);
                companyOfferAdapter = new CategoryOfferAdapter(mContext, offersItemArrayList, R.layout.row_company_offer_grid_layout, true);
                viewHolder.rvHomeCompanyOffer.setAdapter(companyOfferAdapter);
            } else {
                viewHolder.rvHomeCompanyOffer.setLayoutManager(new LinearLayoutManager(mContext));
                viewHolder.rvHomeCompanyOffer.setAdapter(null);
                companyListOfferAdapter = new CategoryOfferAdapter(mContext, offersItemArrayList, R.layout.row_company_offer_layout, false);
                viewHolder.rvHomeCompanyOffer.setAdapter(companyListOfferAdapter);
            }
        }
    }


    public void addAll(ArrayList<OffersItem> offerArrayList,boolean layoutStatus) {
        if (companyOfferAdapter != null) {
            homeSelectedGrid = layoutStatus;

            if(homeSelectedGrid){
                companyOfferAdapter.addAll(new ArrayList<OffersItem>(offerArrayList));
            }else{
                companyOfferAdapter.addAll(new ArrayList<OffersItem>(offerArrayList));
            }

        }

    }

    public void setCompanyOfferAdapter(ArrayList<OffersItem> offerArrayList, int companyId,boolean layoutStatus) {
        if (companyOfferAdapter != null) {
            homeSelectedGrid = layoutStatus;
            offersItemArrayList = offerArrayList;

            if(homeSelectedGrid){
                companyOfferAdapter.resetAdapter(new ArrayList<OffersItem>(offerArrayList));
            }else{
                companyOfferAdapter.resetAdapter(new ArrayList<OffersItem>(offerArrayList));
            }

            currentCompanyId = companyId;
        }

    }

}
