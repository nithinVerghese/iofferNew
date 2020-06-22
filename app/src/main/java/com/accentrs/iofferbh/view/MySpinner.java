package com.accentrs.iofferbh.view;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by Accentrs.
 */
public class MySpinner extends android.support.v7.widget.AppCompatSpinner {

    OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position)
    {
        super.setSelection(position);

        if (position == getSelectedItemPosition())
        {
            listener.onItemSelected(null, null, position, 0);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener)
    {
        this.listener = listener;
    }
}

