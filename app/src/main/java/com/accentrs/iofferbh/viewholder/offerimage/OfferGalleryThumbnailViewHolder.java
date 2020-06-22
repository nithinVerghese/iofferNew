package com.accentrs.iofferbh.viewholder.offerimage;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.accentrs.iofferbh.R;


/**
 * Created by ambab on 5/12/2016.
 */
public class OfferGalleryThumbnailViewHolder extends RecyclerView.ViewHolder {

    public ImageView offerGalleryThumbnailImageView;

    public OfferGalleryThumbnailViewHolder(View itemView) {
        super(itemView);
        offerGalleryThumbnailImageView= (ImageView) itemView.findViewById(R.id.iv_offer_thumbnail_image);
    }
}
