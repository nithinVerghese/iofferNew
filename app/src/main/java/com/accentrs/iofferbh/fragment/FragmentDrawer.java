package com.accentrs.iofferbh.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accentrs.iofferbh.R;
import com.accentrs.iofferbh.activity.AboutUsActivity;
import com.accentrs.iofferbh.activity.ChangeAppLanguage;
import com.accentrs.iofferbh.activity.ContactUsActivity;
import com.accentrs.iofferbh.activity.DrawerActivity;
import com.accentrs.iofferbh.activity.NotificationActivity;
import com.accentrs.iofferbh.adapter.drawer.NavigationDrawerAdapter;


public class FragmentDrawer extends Fragment implements View.OnClickListener {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    public ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;

    private DrawerActivity mDrawerActivity;
    private DrawerEventsCallback mDrawerEventsCallback;
    private Toolbar mToolbar;
    private TextView tvUsername;


    private LinearLayout llChangeVideolanaguage;
    private LinearLayout llAboutUs;
    private LinearLayout llNotification;
    private LinearLayout llContact;

    public static String INSTAGRAM_URL = "https://www.instagram.com/iofferbh";

    public static String FACEBOOK_URL = "https://www.facebook.com/iofferbh.infoline";
    public static String FACEBOOK_PAGE_ID = "100008042260532";


    private ImageView ivFacebook;
    private ImageView ivInstagram;
    private View view;


    public FragmentDrawer() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDrawerActivity = (DrawerActivity) activity;
        try {
            mDrawerEventsCallback = (DrawerEventsCallback) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(activity.getClass().getSimpleName() + " must implement DrawerEventsCallback");
        }
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_drawer_navigation, container, false);
            initializeViews();
            setOnClickListener();
        }
        return view;
    }


    private void initializeViews() {
        ivFacebook = view.findViewById(R.id.iv_facebook);
        ivInstagram = view.findViewById(R.id.iv_instagram);
        llChangeVideolanaguage = view.findViewById(R.id.ll_nav_menu_change_language);
        llAboutUs = view.findViewById(R.id.ll_nav_menu_about_us);
        llNotification = view.findViewById(R.id.ll_nav_menu_notification);
        llContact = view.findViewById(R.id.ll_nav_menu_contact_us);
    }


    private void setOnClickListener() {
        ivFacebook.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llChangeVideolanaguage.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llContact.setOnClickListener(this);
    }


    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();

        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }


    public static Intent newInstagramProfileIntent(Context context) {

        String url = INSTAGRAM_URL;

        PackageManager packageManager = context.getPackageManager();

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (packageManager.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mToolbar = mDrawerActivity.getToolbar();
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
//        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(containerView);
    }

    public void open() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    public void close() {
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_facebook:
                callFacebbok();
                break;

            case R.id.iv_instagram:
                callInstagram();
                break;

            case R.id.ll_nav_menu_change_language:
                startActivity(new Intent(getActivity(), ChangeAppLanguage.class));
                close();
                break;

            case R.id.ll_nav_menu_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                close();
                break;


            case R.id.ll_nav_menu_notification:

                startActivity(new Intent(getActivity(), NotificationActivity.class));
                close();

                break;


            case R.id.ll_nav_menu_contact_us:

                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                close();
                break;

        }
    }


    private void callInstagram() {
        startActivity(newInstagramProfileIntent(getActivity()));
    }


    private void callFacebbok() {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(getActivity());
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    public interface DrawerEventsCallback {
        void onOpen();

        void onClose();
    }
}
