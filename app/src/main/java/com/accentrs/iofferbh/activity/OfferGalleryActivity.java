package com.accentrs.iofferbh.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.accentrs.apilibrary.utils.Constants;
import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.adapter.companyOfferImage.OfferGalleryImageAdapter;
import com.accentrs.iofferbh.adapter.companyOfferImage.OfferGalleryThumbnailAdapter;
import com.accentrs.iofferbh.adapter.delivery.OfferDeliveryWhatsappAdaptor;
import com.accentrs.iofferbh.helper.ViewPagerMultiTouchFix;
import com.accentrs.iofferbh.model.delivery.StoreInfo;
import com.accentrs.iofferbh.model.delivery.WhatsappDataAdaptor;
import com.accentrs.iofferbh.model.home.OfferImagesItem;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.retrofit.ApiServices;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.accentrs.iofferbh.retrofit.ServiceGenerator.createServiceDe;


public class OfferGalleryActivity extends HeaderActivity implements View.OnClickListener, View.OnTouchListener {


    private Toolbar toolbar;
    private View toolbarView;
    private ImageView ivBackIcon;
    private ImageView ivShareIcon;
    private ImageView ivShareDelivery;
    private TextView tvViewAllInHd;
    private OfferDeliveryWhatsappAdaptor mainAdaptor;
    private List<WhatsappDataAdaptor> dataAdaptors = new ArrayList<>();


    private String whatsapp;
    private String whatsapp_num;
    private String[] whatsappArr;
    private String[] whatsappArr_num;


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
    private String status;
    private boolean imageViewHdStatus;

    private Dialog dialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_gallery);

        configureToolBar();
        fetchIntentData();
        hitApi(offersItem.getCompanyId());
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
        ivShareDelivery.setOnClickListener(this);
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
        ivShareDelivery = (ImageView) toolbarView.findViewById(R.id.iv_share_delivery);

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
//            status = Intent.getIntent().getStringExtra().getString("status");
            status = getIntent().getStringExtra("status");
            Log.d("eeeeeeee","n "+status);

            if(status.equals("No")){
                ivShareDelivery.setVisibility(View.GONE);
            }
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
        offerGalleryImageAdapter = new OfferGalleryImageAdapter(OfferGalleryActivity.this, this, new ArrayList<OfferImagesItem>(offersItem.getOfferImages()), false);
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

            case R.id.iv_share_delivery:
                ivShareDelivery();
                break;

            case R.id.iv_back_arrow:
                onBackPressed();
                break;


            case R.id.tv_view_all_in_hd:
                notifyAdapter();
                break;

        }
    }

    private void ivShareDelivery() {
        showProgressDialog(getString(R.string.msg_loading));

        dialog1("Purchase by whatsapp");

//        String shareUrl = Constants.OFFER_SHARE_URL.concat(Constants.PARAMETER_QUES)
//                .concat(Constants.ID).concat(Constants.PARAMETER_EQUALS).concat(offersItem.getId());
//        String url = "https://api.whatsapp.com/send?phone=" + "+917094021262" + "&text=" + shareUrl;
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));
//        try {
////                                startActivity(whatsappIntent);
//            startActivity(i);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(OfferGalleryActivity.this, "Whatsapp have not been installed. ", Toast.LENGTH_SHORT).show();
//        }

    }


    private void notifyAdapter() {
        offerGalleryImageAdapter = new OfferGalleryImageAdapter(OfferGalleryActivity.this, this, new ArrayList<OfferImagesItem>(offersItem.getOfferImages()), true);
        mViewPagerMultiTouchFix.setAdapter(offerGalleryImageAdapter);
        mViewPagerMultiTouchFix.setCurrentItem(position);
    }


    private void shareOffer(String imagePath) {
//        showProgressDialog(getString(R.string.msg_loading));
//        String imagePath = downloadImage();
        String shareUrl = Constants.OFFER_SHARE_URL.concat(Constants.PARAMETER_QUES)
                .concat(Constants.ID).concat(Constants.PARAMETER_EQUALS).concat(offersItem.getId());
        //Toast.makeText(this, shareUrl, Toast.LENGTH_SHORT).show();
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
                        String filesDir = getExternalFilesDir(null) + File.separator + "Image";
                        File dir = new File(filesDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File storeFile = new File(dir, "image1.jpg");
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
//                            Toast.makeText(OfferGalleryActivity.this, "" + storeFile.getPath(), Toast.LENGTH_SHORT).show();
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

    private void dialog1(String Title) {
        dialogs = new Dialog(this);
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogs.setCancelable(false);
        dialogs.setContentView(R.layout.delivery_indo_dialog);
        dialogs.show();
        dialogs.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView body = dialogs.findViewById(R.id.tv_title);
        ImageView imageView2 = dialogs.findViewById(R.id.imageView2);
        ImageView iv_icon = dialogs.findViewById(R.id.iv_icon);
        RecyclerView rcv = dialogs.findViewById(R.id.rcv);

        body.setText(Title);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
                dismissProgressDialog();
            }
        });

        iv_icon.setImageResource(R.drawable.whatsapp_ba);
        String shareUrl = Constants.OFFER_SHARE_URL.concat(Constants.PARAMETER_QUES)
                .concat(Constants.ID).concat(Constants.PARAMETER_EQUALS).concat(offersItem.getId());
        mainAdaptor = new OfferDeliveryWhatsappAdaptor(this, dataAdaptors, shareUrl);
        rcv.setAdapter(mainAdaptor);
        rcv.setLayoutManager(new GridLayoutManager(this, 1));


    }

    private void hitApi(String sId) {
        showProgressDialog(getString(com.accentrs.iofferbh.R.string.msg_loading));

        ApiServices apiServices = createServiceDe().create(ApiServices.class);
            Call<List<StoreInfo>> call = apiServices.dshopinfodet(sId);
        call.enqueue(new Callback<List<StoreInfo>>() {
            @Override
            public void onResponse(Call<List<StoreInfo>> call, Response<List<StoreInfo>> response) {
                dismissProgressDialog();
                List<StoreInfo> dataAbout = response.body();
//                Toast.makeText(OfferGalleryActivity.this, "" + dataAbout, Toast.LENGTH_SHORT).show();
                Log.d("dataabouttt", "aa " + dataAbout.toString());
//                for (StoreInfo info : dataAbout) {
//                    whatsapp = info.getWhatsappName();
//                    whatsapp_num = info.getWhatsapp();
//
//                }

                whatsapp = dataAbout.get(0).getWhatsapp();
                whatsapp_num = dataAbout.get(0).getWhatsappName();
                whatsappData();
            }

            @Override
            public void onFailure(Call<List<StoreInfo>> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(OfferGalleryActivity.this, "err " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void whatsappData() {
        whatsappArr = whatsapp.split(Pattern.quote("|"));
        whatsappArr_num = whatsapp_num.split(Pattern.quote("|"));


        for (int i = 0; i < whatsappArr.length; i++) {

            WhatsappDataAdaptor adaptor = new WhatsappDataAdaptor(whatsappArr[i], whatsappArr_num[i]);
//            dataAdaptors.setKey(whatsappArr[i]);
//            dataAdaptors.setKey(whatsappArr_num[i]);
            dataAdaptors.add(adaptor);
        }
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


