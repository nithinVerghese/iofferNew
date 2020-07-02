package com.accentrs.iofferbh.adapter.companyoffer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferDetailActivity;
import com.accentrs.iofferbh.activity.OfferGalleryActivity;
import com.accentrs.iofferbh.activity.multiClickDissable;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.companydetail.Company;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.offerdetail.CompanyOfferDetailViewHolder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompanyOfferDetailAdapter extends RecyclerView.Adapter<CompanyOfferDetailViewHolder> {

    private View view;
    private OffersItem offersItem;
    private Context context;
    private String sID,status;
    private long mLastClickTime = 0;

    public CompanyOfferDetailAdapter(Context context, OffersItem offersItem,String sID,String status) {
        this.context = context;
        this.offersItem = offersItem;
        this.sID = sID;
        this.status = status;
    }


    @Override
    public CompanyOfferDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_company_offer_detail_layout, parent, false);
        return new CompanyOfferDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyOfferDetailViewHolder holder, int position) {

        final String offerImageUrl = Constants.BASE_URL + offersItem.getOfferImages().get(position).getUrl();

        Log.d("offerimage", offerImageUrl + "");
        holder.rlOfferImagePlaceholder.setVisibility(View.VISIBLE);

        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                holder.rlOfferImagePlaceholder.setVisibility(View.GONE);

                GlideApp.with(context)
                        .load(offerImageUrl)
                        .override(Target.SIZE_ORIGINAL)
                        .into(holder.ivCompanyOffer);
            }

        };

        GlideApp.with(context)
                .asBitmap()
                .load(offerImageUrl)
                .into(target);


//        GlideApp.with(context).asBitmap().placeholder(R.drawable.ioffer_rounded).load(offerImageUrl).into(new BitmapImageViewTarget(holder.ivCompanyOffer) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                holder.ivCompanyOffer.setImageBitmap(resource);
//            }
//        });
//

//        setOfferDate(offersItem,holder);

        multiClickDissable dis = multiClickDissable.Singleton();

        holder.cvOfferDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              Boolean x =  dis.disab();

               if (!x){
                   return;
               }


                holder.cvOfferDetail.setEnabled(false);
                    Intent intent = new Intent(context, OfferGalleryActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(Constants.OFFER_DATA_KEY, offersItem);
                    mBundle.putInt(Constants.OFFER_POSITION_KEY, holder.getAdapterPosition());
                    intent.putExtra("status",status);
                    intent.putExtras(mBundle);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Pair<View, String> p1 = Pair.create((View) holder.ivCompanyOffer, context.getString(R.string.shared_offer_image_name));
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((OfferDetailActivity) context), p1);
                        context.startActivity(intent, options.toBundle());
                    } else {
                        context.startActivity(intent);

                    }
                holder.cvOfferDetail.setEnabled(true);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (offersItem != null) {
            return offersItem.getOfferImages().size();
        } else {
            return 0;
        }

    }


    private void setOfferDate(OffersItem offersItem, CompanyOfferDetailViewHolder holder) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd", new Locale("en", "IN"));
        try {
            Date startDate = simpleDateFormat.parse(offersItem.getStartDate());
            Date endDate = simpleDateFormat.parse(offersItem.getEndDate());
            String offersValidDays = Utils.getOfferDaysLeft(startDate, endDate);

            String offerEndDate = Utils.formateDateInWords(offersItem.getEndDate());
            String offerDate = offerEndDate.concat("(").concat(offersValidDays).concat(context.getString(R.string.d_text)).concat(")");
            holder.tvOfferValidity.setText(context.getString(R.string.valid_till_text).concat(offerDate));


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
