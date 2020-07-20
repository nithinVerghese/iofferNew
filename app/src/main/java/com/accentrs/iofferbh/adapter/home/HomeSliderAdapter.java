package com.accentrs.iofferbh.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.LatestOffersItem;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.home.HomeSliderViewHolder;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderViewHolder> {


    private View view;
    private ArrayList<LatestOffersItem> latestOffersItems;
    private Context context;

    private ArrayList<String> data;

    public HomeSliderAdapter(Context context, List<LatestOffersItem> latestOffersItems) {
        this.context = context;
        this.latestOffersItems = new ArrayList<>(latestOffersItems);
    }

    @Override
    public HomeSliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_home_slider_child_layout, parent, false);
        return new HomeSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeSliderViewHolder holder, int position) {

        Log.d("inside adaper ", "called" + position);

//        String companyUrl =Constants.BASE_URL+ companyModel.getData().get(position).getLogo();

        if (latestOffersItems.get(position).getOfferImages() != null && latestOffersItems.get(position).getOfferImages().size() > 0) {
            OfferImagesItem latestOffersItem = latestOffersItems.get(position).getOfferImages().get(0);

            if(Utils.isValidContextForGlide(context)) {
                GlideApp.with(context)
                        .load(Constants.BASE_URL + latestOffersItem.getUrl())
                        .fitCenter()
                        .into(holder.ivHomeLatestSlider);


                GlideApp.with(context).asBitmap().load(Constants.BASE_URL + latestOffersItems.get(position).getCompanyLogo()).into(new BitmapImageViewTarget(holder.ivHomeCompanySlider) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivHomeCompanySlider.setImageDrawable(circularBitmapDrawable);
                    }
                });


//                GlideApp.with(context)
//                        .load(Constants.BASE_URL + latestOffersItems.get(position).getCompanyLogo())
//                        .fitCenter()
//                        .apply(RequestOptions.circleCropTransform())
//                        .into(holder.ivHomeCompanySlider);


            }

        }

        com.accentrs.iofferbh.activity.multiClickDissable dis =  com.accentrs.iofferbh.activity.multiClickDissable.Singleton();

        holder.cardLatestSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean x =  dis.disab();

                if (!x){
                    return;
                }

                Type type = new TypeToken<LatestOffersItem>() {
                }.getType();
                String offerJson = new Gson().toJson(latestOffersItems.get(holder.getAdapterPosition()), type);

                Log.d("offer model", offerJson + "");
                OffersItem offersItem = new Gson().fromJson(offerJson, OffersItem.class);


                Intent intent = new Intent(context, OfferDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Constants.OFFER_DATA_KEY, offersItem);
                intent.putExtras(mBundle);
                context.startActivity(intent);


            }

        });



    }

    @Override
    public int getItemCount() {

        if (latestOffersItems != null) {
            return latestOffersItems.size();
        } else {
            return 0;
        }


    }


}
