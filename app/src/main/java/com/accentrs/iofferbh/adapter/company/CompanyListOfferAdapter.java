package com.accentrs.iofferbh.adapter.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.HomeScreenActivity;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.activity.multiClickDissable;
import com.accentrs.iofferbh.application.IOfferBhApplication;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.company.CompanyOfferViewHolder;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CompanyListOfferAdapter extends RecyclerView.Adapter<CompanyOfferViewHolder> {

    public static final int WISHLIST_ACTION_RESULT = 201;

    private View view;
    private ArrayList<OffersItem> offersItemList;
    private Context context;
    private int resourceId;
    private boolean isGridSelected;
    private HashMap<String, String> bookmarksItemHashMap;
    private HomeScreenActivity homeScreenActivity;


    public CompanyListOfferAdapter(HomeScreenActivity homeScreenActivity, Context context, List<OffersItem> offersItemList, int resourceId, boolean isGridSelected) {
        this.context = context;
        this.homeScreenActivity = homeScreenActivity;
        this.offersItemList = new ArrayList<>(offersItemList);
        this.resourceId = resourceId;
        this.isGridSelected = isGridSelected;
        bookmarksItemHashMap = ((IOfferBhApplication) context.getApplicationContext()).getBookmarksItemHashMap();

    }


    @Override
    public CompanyOfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        return new CompanyOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyOfferViewHolder holder, int position) {

        String companyUrl = Constants.BASE_URL + offersItemList.get(position).getCompanyLogo();

        String companyOfferName = offersItemList.get(position).getNameEn();
//        String companyName = offersItemList.get(position).getC();

        if (offersItemList.get(position).getNameAr() == null) {
            holder.ivCompanyName.setText(companyOfferName);
        } else if (Utils.isEmptyString(offersItemList.get(position).getNameAr().toString())) {
            holder.ivCompanyName.setText(companyOfferName);
        } else {
            holder.ivCompanyName.setText(offersItemList.get(position).getNameAr().toString());
        }

        if (Utils.isEmptyString(offersItemList.get(position).getDescriptionAr())) {
            holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());
            holder.ivCompanyName.setVisibility(View.GONE);
        } else {
            holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());
            holder.ivCompanyName.setText(offersItemList.get(position).getDescriptionAr());
            holder.ivCompanyName.setVisibility(View.VISIBLE);
        }


        if (offersItemList.get(position).getOfferImages() != null && offersItemList.get(position).getOfferImages().size() > 0) {

            String offerImage = Constants.BASE_URL + offersItemList.get(position).getOfferImages().get(0).getUrl();


            if (bookmarksItemHashMap.containsKey(offersItemList.get(position).getId())) {
                holder.ivOfferListWishlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_offer_fav_selected));
            } else {
                holder.ivOfferListWishlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_offer_fav_un_selected));
            }

            GlideApp.with(context)
                    .load(offerImage)
                    .into(holder.ivCompanyOffer);


        }


        if (offersItemList.get(position).isIsViewed()) {
            holder.rlUserViewedOffer.setVisibility(View.VISIBLE);
        } else {
            holder.rlUserViewedOffer.setVisibility(View.GONE);
        }


        GlideApp.with(context)
                .load(companyUrl)
                .fitCenter()
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivCompanyLogo);

        setOfferDate(offersItemList.get(position), holder);

        multiClickDissable dis = multiClickDissable.Singleton();

        holder.cardOfferView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean x =  dis.disab();

                if (!x){
                    return;
                }

                setUserOfferView(holder, offersItemList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                Intent intent = new Intent(context, OfferDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Constants.OFFER_DATA_KEY, offersItemList.get(holder.getAdapterPosition()));
                intent.putExtras(mBundle);
                homeScreenActivity.startActivityForResult(intent, WISHLIST_ACTION_RESULT);
//                context.startActivity(intent);
            }
        });

        holder.ivShareOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeScreenActivity) context).downloadShareImage(offersItemList.get(holder.getAdapterPosition()));
            }
        });

        holder.ivOfferListWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!bookmarksItemHashMap.containsKey(offersItemList.get(holder.getAdapterPosition()).getId())) {
                    holder.ivOfferListWishlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_offer_fav_selected));
                } else {
                    holder.ivOfferListWishlist.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_offer_fav_un_selected));
                }

                ((HomeScreenActivity) context).callAddToWishlist(offersItemList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (offersItemList != null) {
            return offersItemList.size();
        } else {
            return 0;
        }

    }


    private void setOfferDate(OffersItem offersItem, CompanyOfferViewHolder holder) {
        String lang = new SharedPreferencesData(context).getLanguage();
        if (lang.isEmpty()) {
            lang = com.accentrs.iofferbh.utils.Constants.DEFAULT_LOCALE_LANGUAGE;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale(lang, com.accentrs.iofferbh.utils.Constants.DEFAULT_LOCALE_COUNTRY));
        try {
            Date startDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date endDate = simpleDateFormat.parse(offersItem.getEndDate());
            String offersValidDays = Utils.getOfferDaysLeft(startDate, endDate);

            String offerEndDate = Utils.formateDateInWords(offersItem.getEndDate());
            String offerDate = offerEndDate.concat(" (").concat(offersValidDays).concat(context.getString(R.string.d_text)).concat(")");
            holder.tvOfferValidityDays.setText(offersValidDays.concat(context.getString(R.string.offers_days_left_text)));

            if (isGridSelected) {
                holder.tvOfferValidity.setText(context.getString(R.string.valid_till_text).concat(offerDate));

            } else {
                holder.tvOfferValidity.setText(context.getString(R.string.valid_till_text).concat(offerEndDate));
            }

            if (offersItem.getViewCount() != null) {
                holder.tvOfferViews.setText(offersItem.getViewCount());
            } else {
                holder.tvOfferViews.setVisibility(View.INVISIBLE);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void addAll(ArrayList<OffersItem> offerArrayList) {


        int oldListItemscount = offersItemList.size() + 1;
        offersItemList.addAll(offerArrayList);
        notifyItemChanged(oldListItemscount + 1, offersItemList);

    }


    public void resetAdapter(ArrayList<OffersItem> offerArrayList) {
        offersItemList = offerArrayList;
        notifyDataSetChanged();
    }

    public void setUserOfferView(CompanyOfferViewHolder holder, OffersItem offersItem, int position) {

        if (!offersItem.isIsViewed()) {
            holder.rlUserViewedOffer.setVisibility(View.VISIBLE);
        }
        offersItem.setIsViewed(true);
        offersItemList.set(position, offersItem);

    }


    public void notifyWishlistDataset() {
        bookmarksItemHashMap = ((IOfferBhApplication) context.getApplicationContext()).getBookmarksItemHashMap();
        notifyDataSetChanged();
    }


}
