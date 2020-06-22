package com.accentrs.iofferbh.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerMultiTouchFix extends android.support.v4.view.ViewPager {

    public ViewPagerMultiTouchFix(Context context) {
        super(context);
    }

    public ViewPagerMultiTouchFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }
}

