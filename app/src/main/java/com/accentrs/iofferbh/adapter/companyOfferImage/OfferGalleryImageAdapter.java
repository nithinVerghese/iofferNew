package com.accentrs.iofferbh.adapter.companyOfferImage;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.OfferGalleryActivity;
import com.accentrs.iofferbh.helper.GlideApp;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.volley.VolleySingleton;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;


/**
 * Created by ambab on 5/11/2016.
 */

public class OfferGalleryImageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<OfferImagesItem> arrayList;
    private OfferGalleryActivity offerGalleryActivity;
    private Bitmap bitmap;

    private ImageLoader mImageLoader;
    private boolean viewInHdStatus;
    private boolean imageViewHdStatus;

    public OfferGalleryImageAdapter(OfferGalleryActivity offerGalleryActivity, Context mContext, ArrayList<OfferImagesItem> arrayList,boolean imageViewHdStatus) {
        this.mContext = mContext;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        this.offerGalleryActivity = offerGalleryActivity;
        this.imageViewHdStatus = imageViewHdStatus;
        mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();

    }

    @Override
    public int getCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View itemView = layoutInflater.inflate(R.layout.row_offer_image_pager_layout, container, false);


        final com.accentrs.iofferbh.helper.TouchImageView viewPageImage = itemView.findViewById(R.id.tv_gallery_image);
        final ProgressBar pbGallery = itemView.findViewById(R.id.pb_gallery);

        String imageUrl = Constants.BASE_URL + arrayList.get(position).getUrl();
        pbGallery.setVisibility(View.VISIBLE);
        viewPageImage.setVisibility(View.GONE);


        SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                pbGallery.setVisibility(View.GONE);
                viewPageImage.setVisibility(View.VISIBLE);


                if(!imageViewHdStatus){
                    Log.d("called ","notify blurry");
                    viewPageImage.setImageBitmap(resource);
                    viewPageImage.setAlpha(0.9f);

                    viewInHdStatus = true;
                }else{
                    Log.d("called ","notify new");

                    viewPageImage.setImageBitmap(resource);
                }


            }

        };
        GlideApp.with(mContext)
                .asBitmap()
                .load(imageUrl)
                .into(target);

        container.addView(itemView);


        return itemView;
    }



    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }





    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}