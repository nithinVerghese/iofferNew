package com.accentrs.iofferbh.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.accentrs.iofferbh.R;


public abstract class DrawerBaseActivity extends BaseActivity implements View.OnClickListener  {

    public static final String TAG="DrawerBaseActivity";


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_activity_drawer);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setContentInsetsRelative(32, 0);
        mToolbar.setContentInsetsAbsolute(32, 0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        configureToolBar();
    }

    @Override
    protected boolean useBaseActivityToolBar() {
        return false;
    }


}
