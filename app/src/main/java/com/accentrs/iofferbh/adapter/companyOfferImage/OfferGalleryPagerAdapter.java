package com.accentrs.iofferbh.adapter.companyOfferImage;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferGalleryActivity;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.viewholder.offerimage.OfferGalleryImageViewHolder;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ambab on 5/11/2016.
 */
public class OfferGalleryPagerAdapter extends RecyclerView.Adapter<OfferGalleryImageViewHolder> {

    private View view;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<OfferImagesItem> offerImagesItems;
    private ImageLoader mImageLoader;
    private OfferGalleryActivity offerGalleryActivity;

    public OfferGalleryPagerAdapter(OfferGalleryActivity offerGalleryActivity, Context mContext, ArrayList<OfferImagesItem> offerImagesItems){
        inflater= LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.offerImagesItems=offerImagesItems;
        this.offerGalleryActivity=offerGalleryActivity;
    }

    @Override
    public OfferGalleryImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.row_offer_image_pager_layout,null,false);

        return new OfferGalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferGalleryImageViewHolder holder, int position) {


        GlideApp.with(mContext)
                .load(com.accentrs.apilibrary.utils.Constants.BASE_URL+ offerImagesItems.get(position).getUrl())
                .into(holder.offerGalleryThumbnailImageView);

        holder.offerGalleryThumbnailImageView.setTag(R.string.click_position_key,position);
        holder.offerGalleryThumbnailImageView.setOnClickListener(offerGalleryActivity);


    }

    @Override
    public int getItemCount() {

        if(offerImagesItems!=null){
            return offerImagesItems.size();
        }else{
            return 0;
        }
    }
}
