package com.accentrs.iofferbh.adapter.companyoffer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.fragment.LocationFragment;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.companydetail.CompanyDetailModel;

import com.accentrs.iofferbh.model.companydetail.LocationsItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.service.WishlistService;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.MainViewHolder;
import com.accentrs.iofferbh.viewholder.offerdetail.CompanyDetailHeaderViewHolder;
import com.accentrs.iofferbh.viewholder.offerdetail.CompanyOfferImageListViewHolder;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_REMOVE_RESULT;
import static com.accentrs.iofferbh.service.WishlistService.WISHLIST_RESULT;

public class OfferAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context mContext;
    private OffersItem offersItem;
    private CompanyDetailModel companyDetailModel;

    private final static int OFFER_HEADER_VIEW_TYPE = 10;
    private final static int OFFER_IMAGE_VIEW_TYPE = 15;

    private View view;
    private HashMap<String, String> bookmarksItemHashMap;

    public OfferAdapter(Context mContext, OffersItem offersItem) {
        this.mContext = mContext;
        this.offersItem = offersItem;
        bookmarksItemHashMap = ((IOfferBhApplication) mContext.getApplicationContext()).getBookmarksItemHashMap();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == OFFER_HEADER_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company_offer_detail_header_layout_v1, parent, false);
            return new CompanyDetailHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_company_offer_image_list, parent, false);
            return new CompanyOfferImageListViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {


        switch (holder.getItemViewType()) {

            case OFFER_HEADER_VIEW_TYPE:

                CompanyDetailHeaderViewHolder companyDetailHeaderViewHolder = (CompanyDetailHeaderViewHolder) holder;

                if (bookmarksItemHashMap.containsKey(offersItem.getId())) {
                    companyDetailHeaderViewHolder.ivBookmark.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_offer_wishlist_selected));
                } else {
                    companyDetailHeaderViewHolder.ivBookmark.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_offer_wishlist));
                }


                if (offersItem.getDescriptionAr() != null && offersItem.getDescriptionAr().length() > 0) {
                    companyDetailHeaderViewHolder.tvOfferDescriptionAr.setText(offersItem.getDescriptionAr());
                } else {
                    companyDetailHeaderViewHolder.tvOfferDescriptionAr.setVisibility(View.GONE);
                }

                if (offersItem.getDescriptionEn() != null && offersItem.getDescriptionEn().length() > 0) {
                    companyDetailHeaderViewHolder.tvOfferDescription.setText(offersItem.getDescriptionEn());
                } else {
                    companyDetailHeaderViewHolder.tvOfferDescription.setVisibility(View.GONE);
                }

//                setOfferDate(offersItem,companyDetailHeaderViewHolder);




                if (companyDetailModel != null) {


                    companyDetailHeaderViewHolder.llCompanyCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(companyDetailModel != null){
                                String contactNumber = companyDetailModel.getCompany().getContactNumber();
                                if (contactNumber != null) {
                                    setCompanyCallActionData(contactNumber);
                                } else {
                                    showSnackbar("Contact number not found");
                                }

                            }
                            
                        }
                    });

                    companyDetailHeaderViewHolder.llCompanyLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(companyDetailModel != null){
                                if(companyDetailModel.getCompany() != null && companyDetailModel.getCompany().getLocations() != null){
                                    ArrayList<LocationsItem> locationsItems = new ArrayList<>(companyDetailModel.getCompany().getLocations());
                                    if(locationsItems.size() > 0 ){
                                        callLocationFragment(locationsItems);
                                    }else{
                                        showDataNotFoundDialog("No Location found");
                                    }
                                }else{
                                    showDataNotFoundDialog("No Location found");
                                }

                            }


                        }
                    });


                    companyDetailHeaderViewHolder.llCompanyWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(companyDetailModel != null){
                                String companyWebsite = companyDetailModel.getCompany().getWebsite();
                                if (companyWebsite != null) {
                                    setCompanyWebsiteData(companyWebsite);
                                } else {
                                    showSnackbar("Company website not found");
                                }

                            }


                        }
                    });


                    companyDetailHeaderViewHolder.llCompanyWishlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Utils.isConnectedToInternet(mContext)) {

                                if (bookmarksItemHashMap.containsKey(offersItem.getId())) {
                                    removeWishlist(offersItem.getId());
                                } else {
                                    addToWishlist(offersItem.getId());
                                }


                            } else {
                                showSnackbar(mContext.getString(R.string.error_no_internet_msg));
                            }
                        }
                    });


                }


                String companyImageUrl = Constants.BASE_URL + offersItem.getCompanyLogo();


//                GlideApp.with(mContext)
//                        .load(companyImageUrl)
//                        .fitCenter()
//                        .into(companyDetailHeaderViewHolder.ivCompanyBanner);
//
                GlideApp.with(mContext)
                        .load(companyImageUrl)
                        .fitCenter()
                        .apply(RequestOptions.circleCropTransform())
                        .into(companyDetailHeaderViewHolder.ivCompanyLogo);

                break;


            case OFFER_IMAGE_VIEW_TYPE:

                CompanyOfferImageListViewHolder listViewHolder = (CompanyOfferImageListViewHolder) holder;
                setOfferImageAdapter(listViewHolder);

                break;


        }

    }

    private void setOfferImageAdapter(CompanyOfferImageListViewHolder holder) {
//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 2);
        holder.rvOfferImage.setLayoutManager(new LinearLayoutManager(mContext));
        CompanyOfferDetailAdapter companyOfferDetailAdapter = new CompanyOfferDetailAdapter(mContext, offersItem);
        holder.rvOfferImage.setAdapter(companyOfferDetailAdapter);

    }

    @Override
    public int getItemCount() {

        if (offersItem != null) {
            return 2;
        } else {
            return 0;
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return OFFER_HEADER_VIEW_TYPE;
        } else {
            return OFFER_IMAGE_VIEW_TYPE;
        }
    }


    public void addCompanyOfferDetail(CompanyDetailModel companyDetailModel) {
        this.companyDetailModel = companyDetailModel;
        notifyItemChanged(0);
    }


    public void setCompanyCallActionData(String contactNumber) {
        Uri call = Uri.parse("tel:" + contactNumber);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, call);
        mContext.startActivity(dialIntent);
    }

    public void setCompanyWebsiteData(String companyUrl) {

        if (!companyUrl.startsWith("https://") && !companyUrl.startsWith("http://")) {
            companyUrl = "http://" + companyUrl;
        }

        Intent companyUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(companyUrl));
        mContext.startActivity(companyUrlIntent);
    }

    public void setCompanyLocationData() {

    }


    private void showSnackbar(String message) {
        if(view != null){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }

    }

    private void showDataNotFoundDialog(String message) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        };
        showAlertDialog(message,"Ok", onClickListener, true);

    }

    private void showAlertDialog(String message,String clickButtonText,DialogInterface.OnClickListener onClickListener, boolean isCancellable) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(isCancellable);
//        alertDialogBuilder.setPositiveButton(clickButtonText,onClickListener);
        alertDialogBuilder.setPositiveButton(clickButtonText, onClickListener);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(isCancellable);
        alertDialog.show();
    }


    private void setOfferDate(OffersItem offersItem, CompanyDetailHeaderViewHolder holder) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale("en", "IN"));
        try {

            Date startDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date endDate = simpleDateFormat.parse(offersItem.getEndDate());
            String offersValidDays = Utils.getOfferDaysLeft(startDate, endDate);

            String offerEndDate = Utils.formateDateInWords(offersItem.getEndDate());
            String offerDate = offerEndDate.concat(" (").concat(offersValidDays).concat(mContext.getString(R.string.offers_days_left_text)).concat(")");
//            holder.tvOfferValidity.setText(offerDate.concat(mContext.getString(R.string.offers_days_left_text)));

            holder.tvOfferValidity.setText(mContext.getString(R.string.valid_till_text).concat(offerDate));


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private void addToWishlist(String offerId) {
        String userDeviceId = new SharedPreferencesData(mContext).getUserId();
        if (TextUtils.isEmpty(userDeviceId)) {
            WishlistService.startActionDevice(mContext, offerId, new WishlistReceiver(new Handler()));
        } else {
            WishlistService.startActionBookmark(mContext, offerId, new WishlistReceiver(new Handler()));
        }

    }

    private void removeWishlist(String offerId) {
        String userDeviceId = new SharedPreferencesData(mContext).getUserId();

        WishlistService.startActionRemoveBookmark(mContext, offerId, new WishlistReceiver(new Handler()));

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
                        notifyDataSetChanged();
                    }
                    showSnackbar(bookmarkMessgage);
                    Log.d("added bookmark", "true " + bookmarkMessgage);

                }


            } else if (resultCode == WISHLIST_REMOVE_RESULT) {
                if (resultData != null) {
                    String bookmarkMessgage = resultData.getString(com.accentrs.iofferbh.utils.Constants.BOOKMARK_MESSAGE_KEY);
                    boolean bookmarkStatus = resultData.getBoolean(com.accentrs.iofferbh.utils.Constants.BOOKMARK_STATUS_KEY);
                    if (bookmarkStatus) {
                        removeBookmarkData();
                        notifyDataSetChanged();
                    }
                    showSnackbar(bookmarkMessgage);
                    Log.d("added bookmark", "true " + bookmarkMessgage);

                }


            }
        }
    }


    private void addBookmarkData() {

        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) mContext.getApplicationContext()).getBookmarksItemHashMap();
        bookmarkHashmap.put(offersItem.getId(), offersItem.getNameEn());
        ((IOfferBhApplication) mContext.getApplicationContext()).setUserBookmarkList(bookmarkHashmap);
    }

    private void removeBookmarkData() {
        HashMap<String, String> bookmarkHashmap = ((IOfferBhApplication) mContext.getApplicationContext()).getBookmarksItemHashMap();
        if (bookmarkHashmap != null && bookmarkHashmap.size() == 0)
            return;

        if (bookmarkHashmap != null) {
            bookmarkHashmap.remove(offersItem.getId());
            ((IOfferBhApplication) mContext.getApplicationContext()).setUserBookmarkList(bookmarkHashmap);
        }
    }

    private void callLocationFragment(ArrayList<LocationsItem> locationsItems){
        LocationFragment fragment = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.LOCATION_KEY, locationsItems);
        fragment.setArguments(bundle);
        fragment.show(((OfferDetailActivity) mContext).getSupportFragmentManager(), LocationFragment.TAG);

    }
}
