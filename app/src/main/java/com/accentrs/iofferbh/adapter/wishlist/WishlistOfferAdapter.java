package com.accentrs.iofferbh.adapter.wishlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.activity.WishlistActivity;
import com.accentrs.iofferbh.activity.multiClickDissable;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.bookmark.BookmarksItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.company.CompanyOfferViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WishlistOfferAdapter extends RecyclerView.Adapter<CompanyOfferViewHolder> {

    private View view;
    private ArrayList<BookmarksItem> offersItemList;
    private Context context;
    private int resourceId;
    private boolean isGridSelected;
    private WishlistActivity wishlistActivity;

    public WishlistOfferAdapter(WishlistActivity wishlistActivity,Context context, List<BookmarksItem> offersItemList, int resourceId) {
        this.context = context;
        this.offersItemList = new ArrayList<>(offersItemList);
        this.resourceId = resourceId;
        this.isGridSelected = isGridSelected;
        this.wishlistActivity = wishlistActivity;
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
        if(!Utils.isArabicSelected(context)){
            holder.ivCompanyName.setText(companyOfferName);

            holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());
        }else{
            if(offersItemList.get(position).getNameAr() == null){
                holder.ivCompanyName.setText(companyOfferName);
            }else if(Utils.isEmptyString(offersItemList.get(position).getNameAr().toString())){
                holder.ivCompanyName.setText(companyOfferName);
            }else{
                holder.ivCompanyName.setText(offersItemList.get(position).getNameAr().toString());
            }

            if(offersItemList.get(position).getDescriptionAr() == null){
                holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());
            }else if(Utils.isEmptyString(offersItemList.get(position).getDescriptionAr().toString())) {
                holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());
            }else {
                holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionAr().toString());
            }
        }

        if (offersItemList.get(position).getOfferImages() != null && offersItemList.get(position).getOfferImages().size() > 0) {

            final String offerImage = Constants.BASE_URL + offersItemList.get(position).getOfferImages().get(0).getUrl();

           holder.rlOfferPlaceHolder.setVisibility(View.VISIBLE);


            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    holder.rlOfferPlaceHolder.setVisibility(View.GONE);


                    GlideApp.with(context)
                            .load(offerImage)
                            .into(holder.ivCompanyOffer);
                }

            };
            GlideApp.with(context)
                    .asBitmap()
                    .load(offerImage)
                    .into(target);


        }

        Glide.with(context)
                .load(companyUrl)
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


                Type type = new TypeToken<BookmarksItem>() {
                }.getType();
                String offerJson = new Gson().toJson(offersItemList.get(holder.getAdapterPosition()), type);

                Log.d("offer model", offerJson + "");
                OffersItem offersItem = new Gson().fromJson(offerJson, OffersItem.class);

                Intent intent = new Intent(wishlistActivity, OfferDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Constants.OFFER_DATA_KEY, offersItem);
                intent.putExtras(mBundle);
                wishlistActivity.startActivity(intent);
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


    private void setOfferDate(BookmarksItem offersItem, CompanyOfferViewHolder holder) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale("en", "IN"));
        try {
            Date startDate = simpleDateFormat.parse(offersItem.getStartDate());
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


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
