package com.accentrs.iofferbh.adapter.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.company.CompanyOfferViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CompanyOfferListAdapter extends RecyclerView.Adapter<CompanyOfferViewHolder> {

    private View view;
    private ArrayList<OffersItem> offersItemList;
    private Context context;

    public CompanyOfferListAdapter(Context context, List<OffersItem> offersItemList) {
        this.context = context;
        this.offersItemList = new ArrayList<>(offersItemList);
    }



    @Override
    public CompanyOfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_company_offer_layout,parent,false);
        return new CompanyOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyOfferViewHolder holder, int position) {

        String companyUrl =Constants.BASE_URL+ offersItemList.get(position).getCompanyLogo();

        String companyOfferName = offersItemList.get(position).getNameEn();
//        String companyName = offersItemList.get(position).getC();
        holder.ivCompanyName.setText(companyOfferName);

        if (offersItemList.get(position).getOfferImages() != null && offersItemList.get(position).getOfferImages().size() > 0) {

            String offerImage = Constants.BASE_URL+offersItemList.get(position).getOfferImages().get(0).getUrl();

            Glide.with(context)
                .load(offerImage)
                .into(holder.ivCompanyOffer);
        }

        Glide.with(context)
                .load(companyUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ivCompanyLogo);

        setOfferDate(offersItemList.get(position),holder);


        holder.cardOfferView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OfferDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Constants.OFFER_DATA_KEY,offersItemList.get(holder.getAdapterPosition()));
                intent.putExtras(mBundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(offersItemList != null){
            return offersItemList.size();
        }else{
            return 0;
        }

    }


    private void setOfferDate(OffersItem offersItem,CompanyOfferViewHolder holder){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale("en","IN"));
        try {
            Date startDate = simpleDateFormat.parse(offersItem.getStartDate());
            Date endDate = simpleDateFormat.parse(offersItem.getEndDate());
            String offersValidDays = Utils.getOfferDaysLeft(startDate,endDate);

            String offerEndDate = Utils.formateDateInWords(offersItem.getEndDate());
            String offerDate = offerEndDate.concat("(").concat(offersValidDays).concat(context.getString(R.string.d_text)).concat(")");
            holder.tvOfferValidity.setText(context.getString(R.string.valid_till_text).concat(offerDate));


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void addAll(ArrayList<OffersItem> offerArrayList) {


        int oldListItemscount = offersItemList.size() + 1;
        offersItemList.addAll(offerArrayList);
        notifyItemChanged(oldListItemscount + 1, offersItemList);

        Log.d("adapter ","refreshed "+offerArrayList.size());
    }


    public void resetAdapter(ArrayList<OffersItem> offerArrayList) {
        offersItemList = offerArrayList;
        notifyDataSetChanged();
    }



}
