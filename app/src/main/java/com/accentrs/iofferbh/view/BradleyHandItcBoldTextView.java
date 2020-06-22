package com.accentrs.iofferbh.view;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.accentrs.apilibrary.utils.Constants;


/**
 * Created by khan on 9/18/2017.
 */

public class BradleyHandItcBoldTextView extends AppCompatTextView {
    public BradleyHandItcBoldTextView(Context context) {
        super(context);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.BRADLEY_HAND_ITC_TT_BOLD));
    }

    public BradleyHandItcBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.BRADLEY_HAND_ITC_TT_BOLD));
    }

    public BradleyHandItcBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Constants.BRADLEY_HAND_ITC_TT_BOLD));
    }
}
