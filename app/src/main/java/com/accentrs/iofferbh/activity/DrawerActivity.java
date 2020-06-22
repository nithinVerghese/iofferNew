package com.accentrs.iofferbh.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.accentrs.iofferbh.R;


public abstract class DrawerActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG="DrawerActivity";


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_drawer);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);
        configureHomeToolBar();
    }

    @Override
    protected boolean useBaseActivityToolBar() {
        return false;
    }

}
