package com.accentrs.iofferbh.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.accentrs.apilibrary.utils.Constants;

/**
 * Created by khan on 9/21/2017.
 */

public class OpenSansExtraBoldButton extends android.support.v7.widget.AppCompatButton {
    public OpenSansExtraBoldButton(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_EXTRA_BOLD));
    }

    public OpenSansExtraBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_EXTRA_BOLD));
    }

    public OpenSansExtraBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_EXTRA_BOLD));
    }
}
