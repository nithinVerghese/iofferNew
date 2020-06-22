package com.accentrs.iofferbh.adapter.companyOfferImage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferGalleryActivity;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.utils.Constants;
import com.accentrs.iofferbh.viewholder.offerimage.OfferGalleryThumbnailViewHolder;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ambab on 5/11/2016.
 */
public class OfferGalleryThumbnailAdapter extends RecyclerView.Adapter<OfferGalleryThumbnailViewHolder> {

    private View view;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<OfferImagesItem> offerImagesItems;
    private ImageLoader mImageLoader;
    private OfferGalleryActivity offerGalleryActivity;

    public OfferGalleryThumbnailAdapter(OfferGalleryActivity offerGalleryActivity, Context mContext, ArrayList<OfferImagesItem> offerImagesItems){
        inflater= LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.offerImagesItems=offerImagesItems;
        this.offerGalleryActivity=offerGalleryActivity;
    }

    @Override
    public OfferGalleryThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(R.layout.row_offer_image_list_layout,null,false);

        return new OfferGalleryThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferGalleryThumbnailViewHolder holder, int position) {


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
