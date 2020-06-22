package com.accentrs.iofferbh.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.accentrs.apilibrary.utils.Constants;

/**
 * Created by Ravi on 21-09-2017.
 */

public class OpenSansLigthEditText extends android.support.v7.widget.AppCompatEditText {

    public OpenSansLigthEditText(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));

    }

    public OpenSansLigthEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));

    }

    public OpenSansLigthEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));

    }
}
