package com.accentrs.iofferbh.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.interfaces.IFragmentBasicBehaviorProvider;
import com.accentrs.iofferbh.retrofit.ApiServices;
import com.accentrs.iofferbh.retrofit.ServiceGenerator;
import com.accentrs.iofferbh.utils.SharedPreferencesData;
import com.accentrs.iofferbh.utils.Utils;
import com.accentrs.iofferbh.volley.VolleySingleton;
import com.android.volley.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, IFragmentBasicBehaviorProvider {

    protected static final int REQUEST_CODE_SELECT_LOCATION = 1;
    private final int LOGIN_CODE = 1222;
    private static final String TAG = BaseActivity.class.getName();

    protected View mCustomAbView;
    protected SharedPreferencesData mSharedPreferences;
    protected Toolbar mToolbar;
    private VolleySingleton mVolleySingleton;
    private ProgressDialog mProgressDialog;
    private Map<IFragmentBasicBehaviorProvider, List<Request>> mRequestMap;
    public AlertDialog mAlertDialog;
    public TextView tv_custom_ab_title;
    public TextView tvCartCount;
    private ImageView iv_custom_ab_icon;
    private ImageView iv_custom_ab_right;
    private RelativeLayout rl_logo_iofferbh;
    private View ll_custom_ab_left;
    private View ll_custom_ab_right;
    private View ll_custom_wishlist;
    private ImageView iv_wishlist;

    private View signout;

    private View ll_custom_category;
    private ImageView iv_category;

    private CoordinatorLayout baseLayoutView;
    private LinearLayout ll_search_container;
    private ImageView iv_custom_search;
    private RelativeLayout rlCartIcon;
    private RelativeLayout rlCartCount;
    private ArrayList<String> options = new ArrayList<>();
    private View view;


    @Override
    public void setCurrentFragment(Fragment fragment, String tag) {
    }

    @Override
    public void showMessage(int messageId) {
        showCardToast(messageId);
    }

    @Override
    public void showMessage(String message) {
        showCardToast(message);
    }

    @Override
    public void showMessage(String message, int color) {
        showCardToast(message, color);
    }

    @Override
    public SharedPreferencesData getSharedPreferencesData() {
        if (mSharedPreferences == null)
            mSharedPreferences = new SharedPreferencesData(this);
        return mSharedPreferences;
    }

    @Override
    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(this);
        if (!TextUtils.isEmpty(title)) mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void showProgressDialog(String message) {
        if (mProgressDialog == null) mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void showCardToast(String message, int color) {

    }

    @Override
    public void dismissProgressDialog() {
        Utils.dismissDialog(mProgressDialog);
    }

    public VolleySingleton getVolleySingleton() {
        if (mVolleySingleton == null)
            mVolleySingleton = VolleySingleton.getInstance(this.getApplicationContext());
        return mVolleySingleton;
    }

    @Override
    public boolean addVolleyRequest(Request request, String tag, boolean requestAgain, IFragmentBasicBehaviorProvider IFragmentsBasicBehaviourProvider) {
        if (mVolleySingleton == null)
            mVolleySingleton = VolleySingleton.getInstance(this);


        if (Utils.isConnectedToInternet(this)) {
            addVolleyRequest(request, tag);
            return true;
        } else {
            request.setTag(tag);
            if (requestAgain) {
                if (mRequestMap == null) {
                    mRequestMap = new WeakHashMap<>();
                    mRequestMap = Collections.synchronizedMap(mRequestMap);
                }

                if (!mRequestMap.containsKey(IFragmentsBasicBehaviourProvider) || mRequestMap.get(IFragmentsBasicBehaviourProvider) == null) {
                    mRequestMap.put(IFragmentsBasicBehaviourProvider, new ArrayList<Request>());
                }

                mRequestMap.get(IFragmentsBasicBehaviourProvider).add(request);
                showDialogForNoInternetConnection();
            }


            return false;
        }

    }

    protected boolean useBaseActivityToolBar() {
        return true;
    }

    protected boolean inflateBaseActivityLayout() {
        return true;
    }

    @Override
    public void cancelAll(String tag) {
        if (mVolleySingleton != null)
            mVolleySingleton.cancelAll(tag);
    }

    @Override
    public void setContentView(int layoutResID) {


        if (inflateBaseActivityLayout()) {

            wrapWithBaseActivityLayout(layoutResID);

            if (useBaseActivityToolBar()) {
                setupToolbar();
            } else {
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                setContainersParamsToMatchParent();
            }

        } else {
            if (useBaseActivityToolBar()) {
                throw new UnsupportedOperationException("useBaseActivityToolBar() cannot be set to \"true\" if inflateBaseActivityLayout() is set to \"false\"");
            }
            super.setContentView(layoutResID);
        }

    }


    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_HOME_AS_UP);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        mToolbar.setContentInsetsRelative(0, 0);
        mToolbar.setContentInsetsAbsolute(0, 0);

//        Log.d(TAG, String.valueOf(useBaseActivityToolBar()));
        configureToolBar();
    }

    private void setContainersParamsToMatchParent() {
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        findViewById(R.id.fl_activity_container).setLayoutParams(layoutParams);
    }


    private void wrapWithBaseActivityLayout(int layoutResID) {
        CoordinatorLayout baseLayoutView = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) baseLayoutView.findViewById(R.id.fl_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(baseLayoutView);
    }


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mSharedPreferences = new SharedPreferencesData(this);
        InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        methodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    protected boolean useToolBar() {
        return true;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void configureToolBar() {

        // inflating custom action bar
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        mCustomAbView = layoutInflater.inflate(R.layout.actionbar_title_widget, null);

        // setting up the listeners
        ll_custom_ab_left = mCustomAbView.findViewById(R.id.ll_custom_ab_left);
        ll_search_container = (LinearLayout) mCustomAbView.findViewById(R.id.ll_search);
        rl_logo_iofferbh = mCustomAbView.findViewById(R.id.rl_logo_iofferbh);
        ll_search_container.setOnClickListener(this);
        ll_custom_ab_left.setOnClickListener(this);
        iv_custom_ab_icon = (ImageView) mCustomAbView.findViewById(R.id.iv_custom_ab_icon);
        iv_custom_ab_right = (ImageView) mCustomAbView.findViewById(R.id.iv_custom_ab_right);
        ll_custom_ab_right = mCustomAbView.findViewById(R.id.ll_custom_ab_right);
        ll_custom_ab_right.setOnClickListener(this);

        ll_custom_wishlist = mCustomAbView.findViewById(R.id.ll_wishlist);
        iv_wishlist = mCustomAbView.findViewById(R.id.iv_show_wishlist);
        ll_custom_wishlist.setOnClickListener(this);

        ll_custom_category = mCustomAbView.findViewById(R.id.ll_category);
        iv_category = mCustomAbView.findViewById(R.id.iv_show_category);
        ll_custom_category.setOnClickListener(this);

        tv_custom_ab_title = (TextView) mCustomAbView.findViewById(R.id.tv_custom_ab_title);
        iv_custom_search = (ImageView) mCustomAbView.findViewById(R.id.iv_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(mCustomAbView);
        }

    }

    protected void configureHomeToolBar() {

        // inflating custom action bar
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        mCustomAbView = layoutInflater.inflate(R.layout.actionbar_title_home_widget, null);

        // setting up the listeners
        ll_custom_ab_left = mCustomAbView.findViewById(R.id.ll_custom_ab_left);
        ll_search_container = (LinearLayout) mCustomAbView.findViewById(R.id.ll_search);
        rl_logo_iofferbh = mCustomAbView.findViewById(R.id.rl_logo_iofferbh);
        ll_search_container.setOnClickListener(this);
        ll_custom_ab_left.setOnClickListener(this);
        iv_custom_ab_icon = (ImageView) mCustomAbView.findViewById(R.id.iv_custom_ab_icon);
        iv_custom_ab_right = (ImageView) mCustomAbView.findViewById(R.id.iv_custom_ab_right);
        ll_custom_ab_right = mCustomAbView.findViewById(R.id.ll_custom_ab_right);
        ll_custom_ab_right.setOnClickListener(this);

        ll_custom_wishlist = mCustomAbView.findViewById(R.id.ll_wishlist);
        iv_wishlist = mCustomAbView.findViewById(R.id.iv_show_wishlist);
        ll_custom_wishlist.setOnClickListener(this);

        ll_custom_category = mCustomAbView.findViewById(R.id.ll_category);
        iv_category = mCustomAbView.findViewById(R.id.iv_show_category);
        ll_custom_category.setOnClickListener(this);

        tv_custom_ab_title = (TextView) mCustomAbView.findViewById(R.id.tv_custom_ab_title);
        iv_custom_search = (ImageView) mCustomAbView.findViewById(R.id.iv_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(mCustomAbView);
        }

    }


    public void setTitleTextView(String titleText) {
        tv_custom_ab_title.setText(titleText);
    }

    public void setLeftIconDrawable(Drawable drawable) {
        iv_custom_ab_icon.setBackgroundDrawable(drawable);
    }

    public void setLeftIconDrawableVisibility(boolean isVisible) {
        if (isVisible) {
            ll_custom_ab_left.setVisibility(View.VISIBLE);
        } else {
            ll_custom_ab_left.setVisibility(View.GONE);
        }
    }


    public void setIofferBhLogo() {
        rl_logo_iofferbh.setVisibility(View.VISIBLE);
    }

    public void setRightIconDrawableVisibility(boolean isVisible) {
        if (isVisible) {
            ll_custom_ab_right.setVisibility(View.VISIBLE);
        } else {
            ll_custom_ab_right.setVisibility(View.GONE);
        }
    }

    public void setRightIconDrawable(Drawable drawable) {
        iv_custom_ab_right.setBackgroundDrawable(drawable);
    }

    public void setSearchIconVisibility(boolean isVisible) {
        if (isVisible) {
            ll_search_container.setVisibility(View.VISIBLE);
            ll_search_container.setClickable(true);
        } else {
            ll_search_container.setVisibility(View.GONE);
            ll_search_container.setClickable(false);
        }
    }

    public void setSearchIconDrawable(Drawable drawable) {
        iv_custom_search.setBackgroundDrawable(drawable);
    }

    public void setWishlistIconDrawable(Drawable drawable) {
        iv_wishlist.setBackgroundDrawable(drawable);
    }


    public void setCategoryIconDrawable(Drawable drawable) {
        iv_category.setBackgroundDrawable(drawable);
    }


    public void setCategoryIconVisibility(boolean isVisible) {
        if (isVisible) {
            ll_custom_category.setVisibility(View.VISIBLE);
            ll_custom_category.setClickable(true);
        } else {
            ll_custom_category.setVisibility(View.GONE);
            ll_custom_category.setClickable(false);
        }
    }


    public void setWishlistIconVisibility(boolean isVisible) {
        if (isVisible) {
            ll_custom_wishlist.setVisibility(View.VISIBLE);
            ll_custom_wishlist.setClickable(true);
        } else {
            ll_custom_wishlist.setVisibility(View.GONE);
            ll_custom_wishlist.setClickable(false);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_custom_ab_left:
                finish();
                break;

            case R.id.ll_custom_ab_right:
                break;

        }
    }


    private void goToHomeScreenActivity() {
        Intent mHomeScreenActivity = new Intent(this, MainActivity.class);
        mHomeScreenActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mHomeScreenActivity);
//        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void showCardToast(String message) {
    }

    private void showCardToast(int messageId) {

    }

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showDialogForNoInternetConnection() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Internet Connection Error");
        builder.setMessage(getResources().getString(R.string.error_no_internet_msg));
        builder.setPositiveButton("Retry", null);
        builder.setCancelable(false);
        mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (Utils.isConnectedToInternet(BaseActivity.this)) {
                            mAlertDialog.dismiss();
                            addRequestMapToVolleyRequest();
                        }
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    private void addRequestMapToVolleyRequest() {

        if (mRequestMap == null) return;

        Iterator it = mRequestMap.entrySet().iterator();
        IFragmentBasicBehaviorProvider key;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            key = (IFragmentBasicBehaviorProvider) pair.getKey();
            if (mRequestMap.get(key) != null && mRequestMap.get(key).size() != 0) {
                int size = mRequestMap.get(key).size();
                for (int i = 0; i < size; i++) {
                    if (Utils.isConnectedToInternet(this)) {
                        String tag = (String) mRequestMap.get(key).get(i).getTag();
                        addVolleyRequest(mRequestMap.get(key).get(i), tag);
                        key.retryProgressDialog();

                        mRequestMap.get(key).remove(i);
                        if (mRequestMap.get(key).size() == 0) mRequestMap.remove(key);

                    }
                }

            }
        }
    }

    private void addVolleyRequest(Request request, String tag) {
        mVolleySingleton.addRequestToQueue(request, tag);
    }

    public void retryProgressDialog() {
        showProgressDialog("", "Please wait...");
    }

    public void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public void showSnackBar(CoordinatorLayout container, String message, String buttonText) {
        final Snackbar snackbar = Snackbar.make(container, message, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.setAction(buttonText, view -> snackbar.dismiss());
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlertDialog(String title, String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener, boolean isCancellable) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);
        if (!title.isEmpty()) {
            alertDialogBuilder.setTitle(title);
        }
        if (message != null)
            alertDialogBuilder.setMessage(message);
        if (clickButtonText != null && onClickListener != null)
            alertDialogBuilder.setPositiveButton(clickButtonText, onClickListener);
        if (cancelButtonText != null && onCancelListener != null)
            alertDialogBuilder.setNegativeButton(cancelButtonText, onCancelListener);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(isCancellable);
        alertDialog.show();
    }

    @Override
    public void showAlertDialog(String message, String clickButtonText, String cancelButtonText, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onCancelListener, boolean isCancellable) {
        showAlertDialog("", message, clickButtonText, cancelButtonText, onClickListener, onCancelListener, isCancellable);
    }

    public ApiServices getApiIneterface(/*String endPoint*/) {
        return ServiceGenerator.createService(/*endPoint*/).create(ApiServices.class);
    }

    public ApiServices getApiIneterfaceDe(/*String endPoint*/) {
        return ServiceGenerator.createServiceDe(/*endPoint*/).create(ApiServices.class);
    }
}
