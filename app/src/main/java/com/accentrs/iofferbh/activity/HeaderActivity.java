package com.accentrs.iofferbh.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.accentrs.iofferbh.R;


public abstract class HeaderActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG="HeaderActivity";
    private RelativeLayout loading_relativeLayout;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_header);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_drawer_activity_container);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        mToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    protected boolean useBaseActivityToolBar() {
        return false;
    }


}
