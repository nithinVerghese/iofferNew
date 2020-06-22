package com.accentrs.iofferbh.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.companyOfferImage.OfferGalleryImageAdapter;
import com.accentrs.iofferbh.adapter.companyOfferImage.OfferGalleryThumbnailAdapter;
import com.accentrs.iofferbh.helper.ViewPagerMultiTouchFix;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class OfferGalleryActivity extends HeaderActivity implements View.OnClickListener, View.OnTouchListener {


    private Toolbar toolbar;
    private View toolbarView;
    private ImageView ivBackIcon;
    private ImageView ivShareIcon;
    private TextView tvViewAllInHd;


    private boolean recyclerThumbnailViewVisible = true;
    private GestureDetectorCompat mDetector;


    private OfferGalleryImageAdapter offerGalleryImageAdapter;
    private OfferGalleryThumbnailAdapter offerGalleryThumbnailAdapter;

    private ViewPagerMultiTouchFix mViewPagerMultiTouchFix;
    private RecyclerView recyclerPdpGallery;
    private int position;

    private OffersItem offersItem;

    private TextView tvOfferImageCount;
    private TextView tvOfferImageCurrentPosition;

    private boolean imageViewHdStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_gallery);
        configureToolBar();
        fetchIntentData();
        initializeViews();
        setListeners();
        setRecyclerView();
        initializeGestureListener();
        setOfferAdapter();
        setOfferImageViewPagerData();
    }

    private void setListeners() {
        ivBackIcon.setOnClickListener(this);
        ivShareIcon.setOnClickListener(this);
        tvViewAllInHd.setOnClickListener(this);
    }

    protected void configureToolBar() {
        toolbar = (Toolbar) findViewById(R.id.offer_gallery_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        toolbarView = layoutInflater.inflate(R.layout.gallery_toolbar_layout, null);
        ivBackIcon = (ImageView) toolbarView.findViewById(R.id.iv_back_arrow);
        ivShareIcon = (ImageView) toolbarView.findViewById(R.id.iv_share_offer);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(toolbarView);
        }
    }

    public void fetchIntentData() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            offersItem = (OffersItem) bundle.getSerializable(Constants.OFFER_DATA_KEY);
            position = bundle.getInt(Constants.OFFER_POSITION_KEY);
//            initializeToolbar();

        }

    }


    private void initializeToolbar() {
        setIofferBhLogo();
//        setTitleTextView(offersItem.getCompanyNameEn()+"");
        setSearchIconVisibility(false);
        setLeftIconDrawableVisibility(true);
//        setRightIconDrawableVisibility(false);
//        setRightIconDrawable(ContextCompat.getDrawable(this,R.drawable.ic_offer_share_black));
        setLeftIconDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

    }


    private void initializeGestureListener() {
        mDetector = new GestureDetectorCompat(this, new GestureListener());
    }

    private void setOfferImageViewPagerData() {
        tvOfferImageCurrentPosition.setText(String.valueOf(position + 1));
        tvOfferImageCount.setText(String.valueOf(offersItem.getOfferImages().size()));
    }

    private void setOfferImageCurrentPageData() {
        tvOfferImageCurrentPosition.setText(String.valueOf(position + 1));
    }


    private void initializeViews() {
        mViewPagerMultiTouchFix = (ViewPagerMultiTouchFix) findViewById(R.id.vp_offergallery);
        recyclerPdpGallery = (RecyclerView) findViewById(R.id.rv_offergallery);
        tvOfferImageCount = findViewById(R.id.tv_offer_image_total_count);
        tvOfferImageCurrentPosition = findViewById(R.id.tv_offer_image_current_position);
        tvViewAllInHd = findViewById(R.id.tv_view_all_in_hd);
    }

    private void setRecyclerView() {
        recyclerPdpGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setOfferAdapter() {
        offerGalleryImageAdapter = new OfferGalleryImageAdapter(OfferGalleryActivity.this, this, new ArrayList<OfferImagesItem>(offersItem.getOfferImages()),false);
//         offerGalleryThumbnailAdapter = new OfferGalleryThumbnailAdapter(OfferGalleryActivity.this, this, new ArrayList<OfferImagesItem>(offersItem.getOfferImages()));
        mViewPagerMultiTouchFix.setAdapter(offerGalleryImageAdapter);
//         recyclerPdpGallery.setAdapter(offerGalleryThumbnailAdapter);
        CirclePageIndicator mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.view_pager_indicator);
        mCirclePageIndicator.setViewPager(mViewPagerMultiTouchFix);
        mViewPagerMultiTouchFix.setCurrentItem(position);

        mViewPagerMultiTouchFix.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int currentPosition) {
                position = currentPosition;
                setOfferImageCurrentPageData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_offer_thumbnail_image:
                position = Integer.parseInt(String.valueOf(v.getTag(R.string.click_position_key)));
                mViewPagerMultiTouchFix.setCurrentItem(position);
                break;

            case R.id.ll_custom_ab_right:
                showSnackbar(v, "Coming soon");
                break;

            case R.id.iv_share_offer:
                downloadImage();
                break;

            case R.id.iv_back_arrow:
                onBackPressed();
                break;


            case R.id.tv_view_all_in_hd:
                notifyAdapter();
                break;

        }
    }


    private void notifyAdapter(){
        offerGalleryImageAdapter = new OfferGalleryImageAdapter(OfferGalleryActivity.this, this, new ArrayList<OfferImagesItem>(offersItem.getOfferImages()),true);
        mViewPagerMultiTouchFix.setAdapter(offerGalleryImageAdapter);
        mViewPagerMultiTouchFix.setCurrentItem(position);
    }



    private void shareOffer(String imagePath) {
//        showProgressDialog(getString(R.string.msg_loading));
//        String imagePath = downloadImage();
        String shareUrl = Constants.OFFER_SHARE_URL.concat(Constants.PARAMETER_QUES)
                .concat(Constants.ID).concat(Constants.PARAMETER_EQUALS).concat(offersItem.getId());
        ShareCompat.IntentBuilder
                .from(this) // getActivity() or activity field if within Fragment
                .setStream(Uri.parse(imagePath))
                .setText(offersItem.getDescriptionEn().concat("\n").concat(shareUrl))
                .setType("image/*") // most general text sharing MIME type
                .setChooserTitle("Share with")
                .startChooser();

    }

    private void downloadImage() {
        showProgressDialog(getString(R.string.msg_loading));
//        SimpleTarget<Bitmap> target = ;
                Glide.with(this)
                        .asBitmap()
                        .load(com.accentrs.apilibrary.utils.Constants.BASE_URL.concat(offersItem.getOfferImages().get(position).getUrl()))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap newBitmap, @Nullable Transition<? super Bitmap> transition) {
                                String filesDir = getExternalFilesDir(null)+File.separator+"Image";
                                File dir = new File(filesDir);
                                if (!dir.exists()) {
                                    dir.mkdirs();
                                }
                                File storeFile = new File(dir,"image1.jpg");
                                storeFile.deleteOnExit();
                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                                try {
                                    storeFile.createNewFile();
                                    FileOutputStream fo = new FileOutputStream(storeFile);
                                    fo.write(bytes.toByteArray());
                                    fo.close();
                                    bytes.close();
                                    dismissProgressDialog();
                                    shareOffer(storeFile.getPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


//        FutureTarget<Bitmap> futureTarget =
//                Glide.with(this)
//                        .asBitmap()
//                        .load(com.accentrs.apilibrary.utils.Constants.BASE_URL.concat(offersItem.getOfferImages().get(position).getUrl()))
//                        .submit(720, 1280);
//
//        try {
//            Bitmap newBitmap = futureTarget.get();
//            String filesDir = getFilesDir()+File.separator+"Image";
//            File dir = new File(filesDir);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File storeFile = new File(dir,"image1.jpg");
//            storeFile.deleteOnExit();
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            try {
//                storeFile.createNewFile();
//                FileOutputStream fo = new FileOutputStream(storeFile);
//                fo.write(bytes.toByteArray());
//                fo.close();
//                bytes.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return storeFile.getPath();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mDetector.onTouchEvent(motionEvent);
        return true;
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

//            Log.d("gesture", "single tap");
//
//            if (recyclerThumbnailViewVisible) {
//
//                recyclerPdpGallery.setVisibility(View.GONE);
//                recyclerThumbnailViewVisible = false;
//
//            } else {
//                recyclerPdpGallery.setVisibility(View.VISIBLE);
//
//                recyclerThumbnailViewVisible = true;
//            }

            return true;
        }


    }


}
