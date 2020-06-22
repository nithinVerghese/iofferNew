package com.accentrs.iofferbh.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ravi on 15-10-2017.
 */

public class OverlapDecoration extends RecyclerView.ItemDecoration {

    private final static int vertOverlap = 0;

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.set(0, vertOverlap, 0, 0);

    }
}
