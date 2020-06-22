package com.accentrs.iofferbh.adapter.home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.CategoryOfferActivity;
import com.accentrs.iofferbh.activity.CouponCategoryActivity;
import com.accentrs.iofferbh.activity.DeliveryActivity;
import com.accentrs.iofferbh.activity.HomeScreenActivity;
import com.accentrs.iofferbh.activity.MyCouponActivity;
import com.accentrs.iofferbh.activity.multiClickDissable;
import com.accentrs.iofferbh.adapter.company.CompanyAdapter;
import com.accentrs.iofferbh.adapter.company.CompanyListOfferAdapter;
import com.accentrs.iofferbh.adapter.company.CompanyOfferAdapter;
import com.accentrs.iofferbh.helper.RecyclerItemClickListener;
import com.accentrs.iofferbh.model.home.CompaniesItem;
import com.accentrs.iofferbh.model.home.HomeScreenModel;
import com.accentrs.iofferbh.model.home.OffersItem;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.viewholder.MainViewHolder;
import com.accentrs.iofferbh.viewholder.home.HomeCategoryViewHolder;
import com.accentrs.iofferbh.viewholder.home.HomeCompanyOfferHolder;
import com.accentrs.iofferbh.viewholder.home.HomeCompanyViewHolder;
import com.accentrs.iofferbh.viewholder.home.HomeSliderHolder;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context mContext;
    private HomeScreenModel homeScreenModel;
    private HomeScreenModel companyOfferModel;




    private final static int HOME_OFFER_SLIDER_VIEW_TYPE = 5;
    private final static int HOME_CATEGORY_VIEW_TYPE = 10;
    private final static int HOME_COMPANY_VIEW_TYPE = 15;
    private final static int HOME_COMPANY_OFFER_VIEW_TYPE = 20;
    private final long SLIDER_TIME = 6000;
    private int companyId;
    private RequestQueue mQueue;


    private CompanyOfferAdapter companyOfferAdapter;
    private CompanyListOfferAdapter companyListOfferAdapter;

    private CompanyAdapter companyAdapter;


    private View view;
    private int currentCompanyId;


    private boolean homeSelectedGrid = false;
    private int homeLayoutStatus;

    private ArrayList<OffersItem> offersItemArrayList;
    final private GridLayoutManager mGridLayoutManager;
    final private LinearLayoutManager mLinearLayoutManager;
    private HomeScreenActivity homeScreenActivity;
    private boolean isScrollingForward = true;


    public int PAGE_COUNT = 1;
    public int PAGE_SIZE = 10;
    private boolean hit_api = true;
    private boolean noAPIHit;
    private boolean loading = true;



    com.accentrs.apilibrary.utils.Constants obj = new com.accentrs.apilibrary.utils.Constants();

    public HomeAdapter(HomeScreenActivity homeScreenActivity, Context mContext, HomeScreenModel homeScreenModel, int currentCompanyId,boolean homeSelectedGrid) {
        this.mContext = mContext;
        this.homeScreenModel = homeScreenModel;
        this.currentCompanyId = currentCompanyId;
        this.homeScreenActivity = homeScreenActivity;
        offersItemArrayList = new ArrayList<>(homeScreenModel.getOffers());
        mGridLayoutManager = new GridLayoutManager(mContext, 2);
        this.homeSelectedGrid = homeSelectedGrid;
//        mGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);


    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mQueue = Volley.newRequestQueue(mContext);

        if (viewType == HOME_OFFER_SLIDER_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_slider_layout, parent, false);
            return new HomeSliderHolder(view);
        } else
            if (viewType == HOME_CATEGORY_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_category_layout, parent, false);
            return new HomeCategoryViewHolder(view);
        } else if (viewType == HOME_COMPANY_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_company_layout, parent, false);
            return new HomeCompanyViewHolder(view);
        } else if (viewType == HOME_COMPANY_OFFER_VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.row_home_company_offer_layout, parent, false);
            return new HomeCompanyOfferHolder(view);
        } else {
            return null;
        }


    }



    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        switch (holder.getItemViewType()) {


            case HOME_OFFER_SLIDER_VIEW_TYPE:

                HomeSliderHolder homeSliderHolder = (HomeSliderHolder) holder;
                setHomeSliderRecyclerview(homeSliderHolder);

                break;

            case HOME_CATEGORY_VIEW_TYPE:

                HomeCategoryViewHolder categoryViewHolder = (HomeCategoryViewHolder) holder;
                setHomeCategoryRecyclerview(categoryViewHolder);

                break;

            case HOME_COMPANY_VIEW_TYPE:

                HomeCompanyViewHolder companyViewHolder = (HomeCompanyViewHolder) holder;
                setHomeCompanyRecyclerview(companyViewHolder);

                break;

            case HOME_COMPANY_OFFER_VIEW_TYPE:

                HomeCompanyOfferHolder companyOfferHolder = (HomeCompanyOfferHolder) holder;
                setHomeCompanyOfferRecyclerview(companyOfferHolder);

                break;


        }


    }

    @Override
    public int getItemCount() {

        return 3;


    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return HOME_OFFER_SLIDER_VIEW_TYPE;
        } else if (position == 1) {
            return HOME_COMPANY_VIEW_TYPE;
        } else {
            return HOME_COMPANY_OFFER_VIEW_TYPE;
        }

    }


//    public void jsonpars(){
//
//        String url = "https://dev.sayg.co/api/coupon_module";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            JSONArray jsonArray =response.getJSONArray("data");
//                            JSONObject data = jsonArray.getJSONObject(0);
//                            String coupon_module = data.getString("coupon_module");
//                            String my_coupon_module = data.getString("my_coupon_module");
//
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(mContext, "Error: "+error, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//        mQueue.add(request);
//
//    }


    private void setHomeSliderRecyclerview(HomeSliderHolder viewHolder) {


        {

            String url = "http://coupon.infoline.website/api/coupon_module";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray jsonArray =response.getJSONArray("data");
                                JSONObject data = jsonArray.getJSONObject(0);
                                String coupon_module = data.getString("coupon_module");
                                String my_coupon_module = data.getString("my_coupon_module");


                                if(coupon_module.equals("0")){
                                    Log.e("inJas",coupon_module);
                                    viewHolder.ivCoupon.setVisibility(View.GONE);
                                }
                                else {
                                    Log.e("inJas",coupon_module);
                                    viewHolder.ivCoupon.setVisibility(View.VISIBLE);
                                }


                                if(my_coupon_module.equals("0")){
                                    Log.e("inJas",coupon_module);
                                    viewHolder.ivMyCoupon.setVisibility(View.GONE);
                                }
                                else {
                                    Log.e("inJas",coupon_module);
                                    viewHolder.ivMyCoupon.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(mContext, "Error: "+error, Toast.LENGTH_SHORT).show();


                }
            });

            mQueue.add(request);

        }


        //Log.e("coupon_123 ", homeScreenActivity.coupon_module);


//        if(homeScreenActivity.coupon_module.equals("0")){
//
//            Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
//
//        }else {
//
//            Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
//        }
//





        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {

                    private static final float SPEED = 300f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        viewHolder.rvHomeSlider.setLayoutManager(mLayoutManager);

        if (homeScreenModel.getLatestOffers() != null && homeScreenModel.getLatestOffers().size() > 0) {
            viewHolder.llOfferParent.setVisibility(View.VISIBLE);
            HomeSliderAdapter companyAdapter = new HomeSliderAdapter(mContext, homeScreenModel.getLatestOffers());
            viewHolder.rvHomeSlider.setAdapter(companyAdapter);
        }



        new CountDownTimer(5000, 1000) {
            int count;

            @Override
            public void onTick(long millisUntilFinished) {
                transition(viewHolder);
                Log.d("sssss", "sssssss");
                count++;
            }

            @Override
            public void onFinish() {
                //Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                //animator.cancel();
            }
        }.start();

        viewHolder.ivDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiClickDissable dis = multiClickDissable.Singleton();
                Boolean x =  dis.disab();

                if (!x){

                    return;
                }

                if (Utils.isConnectedToInternet(mContext)) {
                    //Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(mContext, DeliveryActivity.class);
                    mContext.startActivity(mIntent);
                } else {
                    ((HomeScreenActivity) mContext).showToast(mContext.getString(R.string.error_no_internet_msg));
                }

            }
        });

        viewHolder.ivCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                multiClickDissable dis = multiClickDissable.Singleton();
                Boolean x =  dis.disab();

                if (!x){

                    return;
                }

                if (Utils.isConnectedToInternet(mContext)) {
                        Intent mIntent = new Intent(mContext, CategoryOfferActivity.class);
                        mContext.startActivity(mIntent);
                    } else {
                        ((HomeScreenActivity) mContext).showToast(mContext.getString(R.string.error_no_internet_msg));
                    }

            }
        });

        viewHolder.ivCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiClickDissable dis = multiClickDissable.Singleton();
                Boolean x =  dis.disab();

                if (!x){

                    return;
                }


                    if (Utils.isConnectedToInternet(mContext)) {

                        Intent mIntent = new Intent(mContext, CouponCategoryActivity.class);
                        mContext.startActivity(mIntent);
                       // ((HomeScreenActivity)mContext).finish();

                    } else {
                        ((HomeScreenActivity) mContext).showToast(mContext.getString(R.string.error_no_internet_msg));
                    }

//

            }
        });


        viewHolder.ivMyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiClickDissable dis = multiClickDissable.Singleton();
                Boolean x =  dis.disab();

                if (!x){

                    return;
                }

                    if (Utils.isConnectedToInternet(mContext)) {
                                                Intent mIntent = new Intent(mContext, MyCouponActivity.class);
                                                mContext.startActivity(mIntent);
                        //((HomeScreenActivity)mContext).finish();
                                            } else {
                                                ((HomeScreenActivity) mContext).showToast(mContext.getString(R.string.error_no_internet_msg));
                                            }
//


            }
        });

        setSlideRunnable(viewHolder);

    }

    private void setSlideRunnable(final HomeSliderHolder viewHolder) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int lastVisible = ((LinearLayoutManager) viewHolder.rvHomeSlider.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (isScrollingForward) {
                    if (lastVisible < homeScreenModel.getLatestOffers().size() - 1) {
                        lastVisible++;
                        viewHolder.rvHomeSlider.smoothScrollToPosition(lastVisible);
                    } else {
//
                        viewHolder.rvHomeSlider.smoothScrollToPosition(0);
                    }
                }
//
                if (lastVisible == homeScreenModel.getLatestOffers().size() - 1) {
                    handler.postDelayed(this, 10000);
                } else {
                    handler.postDelayed(this, SLIDER_TIME);
                }
            }
        };

        handler.postDelayed(runnable, SLIDER_TIME);
    }


    private void setHomeCategoryRecyclerview(HomeCategoryViewHolder viewHolder) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.rvHomeCategory.setLayoutManager(mLayoutManager);
    }

    private void setHomeCompanyRecyclerview(HomeCompanyViewHolder viewHolder) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.rvHomeCompany.setLayoutManager(mLayoutManager);


        if (companyAdapter == null) {
            companyAdapter = new CompanyAdapter(mContext,filterCompanyList(homeScreenModel.getCompanies()));
            viewHolder.rvHomeCompany.setAdapter(companyAdapter);

            viewHolder.rvHomeCompany.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {


                    if (homeScreenModel.getCompanies() != null) {


                        if (position != 0) {
                            if (homeScreenModel.getCompanies().get(position - 1).getId() != currentCompanyId) {

                                callCompanyOffer(homeScreenModel.getCompanies().get(position - 1).getId());

                            }


                        } else {

                            if (currentCompanyId != position) {
                                callCompanyOffer(0);
                            }

                        }

                        companyAdapter.setSelectedPosition(position);


                    }


                }

                @Override
                public void onItemLongPress(View childView, int position) {

                }
            }));
        }


    }


    private void callCompanyOffer(int companyId) {
        ((HomeScreenActivity) mContext).setCompanyOffers(companyId);
    }


    private void setHomeCompanyOfferRecyclerview(final HomeCompanyOfferHolder viewHolder) {


        if (companyListOfferAdapter == null || companyOfferAdapter == null || homeLayoutStatus == 123) {

            if (homeSelectedGrid) {

                viewHolder.rvHomeCompanyOfferList.setVisibility(View.GONE);
                viewHolder.rvHomeCompanyOffer.setVisibility(View.VISIBLE);
                viewHolder.rvHomeCompanyOffer.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                viewHolder.rvHomeCompanyOffer.setAdapter(null);
                companyOfferAdapter = new CompanyOfferAdapter(homeScreenActivity, mContext, offersItemArrayList, R.layout.row_company_offer_grid_layout, true);
                viewHolder.rvHomeCompanyOffer.setAdapter(companyOfferAdapter);
                companyOfferAdapter.notifyDataSetChanged();
            } else {

                viewHolder.rvHomeCompanyOffer.setVisibility(View.GONE);
                viewHolder.rvHomeCompanyOfferList.setVisibility(View.VISIBLE);
                viewHolder.rvHomeCompanyOfferList.setLayoutManager(new LinearLayoutManager(mContext));
                viewHolder.rvHomeCompanyOfferList.setAdapter(null);
                companyListOfferAdapter = new CompanyListOfferAdapter(homeScreenActivity, mContext, offersItemArrayList, R.layout.row_company_offer_layout, false);
                viewHolder.rvHomeCompanyOfferList.setAdapter(companyListOfferAdapter);
                companyListOfferAdapter.notifyDataSetChanged();
            }


        }

    }


    public void addAll(ArrayList<OffersItem> offerArrayList, boolean layoutStatus) {
        if (companyOfferAdapter != null || companyListOfferAdapter != null) {
            homeSelectedGrid = layoutStatus;

            if (homeSelectedGrid) {
                companyOfferAdapter.addAll(new ArrayList<OffersItem>(offerArrayList));
            } else {
                companyListOfferAdapter.addAll(new ArrayList<OffersItem>(offerArrayList));
            }

            offersItemArrayList.addAll(offerArrayList);

        }

    }

    public void setCompanyOfferAdapter(ArrayList<OffersItem> offerArrayList, int companyId, boolean layoutStatus) {
        if (companyOfferAdapter != null || companyListOfferAdapter != null) {
            homeSelectedGrid = layoutStatus;
            offersItemArrayList = offerArrayList;

            homeLayoutStatus = 123;
            currentCompanyId = companyId;

        }

    }

    public void changeHomeLayout(boolean layoutStatus) {
        homeSelectedGrid = layoutStatus;
        homeLayoutStatus = 123;
        notifyDataSetChanged();
    }

    public void refreshCompanyOffersListing() {
        if (companyOfferAdapter == null || homeLayoutStatus == 123) {
            if (homeSelectedGrid) {
                if (companyOfferAdapter != null) {
                    companyOfferAdapter.notifyDataSetChanged();
                }
            } else {
                if (companyListOfferAdapter != null) {
                    companyListOfferAdapter.notifyWishlistDataset();
                }
            }
        }
    }

    public  List<CompaniesItem> filterCompanyList(List<CompaniesItem> companies) {
        List<CompaniesItem> companiesItemList = new ArrayList<>();
        if(companies != null){
            for (int i = 0; i < companies.size(); i++) {
                if(companies.get(i) != null){
                    if(!companies.get(i).getNameEn().equalsIgnoreCase("Iofferbh")){
                        companiesItemList.add(companies.get(i));
                    }
                }
            }
        }
        return companiesItemList;
    }

    private void transition(HomeSliderHolder viewHolder){
        ValueAnimator translate = ValueAnimator.ofFloat(0.5f, 1f);
        translate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = Float.parseFloat(animation.getAnimatedValue().toString());
                viewHolder.ivDelivery.setScaleX(scale);
                viewHolder.ivDelivery.setScaleY(scale);
            }
        });
        translate.start();
    }

}
