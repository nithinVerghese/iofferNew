package com.accentrs.iofferbh.adapter.company;

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
import com.accentrs.iofferbh.activity.HomeScreenActivity;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.activity.multiClickDissable;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.company.CompanyOfferViewHolder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompanyOfferAdapter extends RecyclerView.Adapter<CompanyOfferViewHolder> {

    private View view;
    private ArrayList<OffersItem> offersItemList;
    private Context context;
    private int resourceId;
    private boolean isGridSelected;
    private HomeScreenActivity homeScreenActivity;

    public CompanyOfferAdapter(HomeScreenActivity homeScreenActivity,Context context, List<OffersItem> offersItemList, int resourceId, boolean isGridSelected) {
        this.context = context;
        this.offersItemList = new ArrayList<>(offersItemList);
        this.resourceId = resourceId;
        this.homeScreenActivity = homeScreenActivity;
        this.isGridSelected = isGridSelected;
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

        }else{
            if(offersItemList.get(position).getNameAr() == null){
                holder.ivCompanyName.setText(companyOfferName);
            }else if(Utils.isEmptyString(offersItemList.get(position).getNameAr().toString())){
                holder.ivCompanyName.setText(companyOfferName);
            }else{
                holder.ivCompanyName.setText(offersItemList.get(position).getNameAr().toString());
            }

        }

        holder.rlOfferPlaceHolder.setVisibility(View.VISIBLE);

        holder.tvOfferDescription.setText(offersItemList.get(position).getDescriptionEn());

        if (offersItemList.get(position).getOfferImages() != null && offersItemList.get(position).getOfferImages().size() > 0) {

            final String offerImage = Constants.BASE_URL + offersItemList.get(position).getOfferImages().get(0).getUrl();


//            GlideApp.with(context)
//                    .load(offerImage)
//                    .placeholder(R.drawable.offer1)
//                    .into(holder.ivCompanyOffer);


            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    if(isGridSelected){
                        holder.rlOfferImagePlaceHolder.setVisibility(View.GONE);
                        holder.rlOfferPlaceHolder.setVisibility(View.GONE);
                    }else{
                        holder.rlOfferImagePlaceHolder.setVisibility(View.VISIBLE);
                        holder.rlOfferPlaceHolder.setVisibility(View.GONE);

                    }

//                    holder.ivCompanyOffer.layout(0, 0, 0, 0);

                    GlideApp.with(context)
                            .load(offerImage)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.ivCompanyOffer);
                }

            };
            GlideApp.with(context)
                    .asBitmap()
                    .load(offerImage)
                    .into(target);


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

        holder.rlOfferView.setOnClickListener(new View.OnClickListener() {
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
                context.startActivity(intent);
            }
        });

        holder.ivShareOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeScreenActivity)context).downloadShareImage(offersItemList.get(holder.getAdapterPosition()));
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale("en", "IN"));
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
                holder.tvOfferViews.setText(offersItem.getViewCount().concat(context.getString(R.string.views_text)));
            }else{
                holder.tvOfferViews.setVisibility(View.INVISIBLE);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void addAll(ArrayList<OffersItem> offerArray) {


        int oldListItemscount = offersItemList.size() + 1;
        offersItemList.addAll(offerArray);
        notifyItemChanged(oldListItemscount + 1, offersItemList);
//        notifyDataSetChanged();
        Log.d("adapter ", "refreshed " + offersItemList.size());
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


}
