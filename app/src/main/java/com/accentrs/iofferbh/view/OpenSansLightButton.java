package com.accentrs.iofferbh.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.accentrs.apilibrary.utils.Constants;


/**
 * Created by khan on 9/18/2017.
 */

public class OpenSansLightButton extends androidx.appcompat.widget.AppCompatButton {
    public OpenSansLightButton(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));
    }

    public OpenSansLightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));
    }

    public OpenSansLightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.OPEN_SANS_LIGHT));
    }
}
