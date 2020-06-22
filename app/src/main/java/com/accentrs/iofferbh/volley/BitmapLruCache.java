package com.accentrs.iofferbh.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;


public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {

    public BitmapLruCache(Context context) {
        this(getCacheSzie(context));
    }

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    // cache size equals to screen size * 3
    static int getCacheSzie(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        // 1 pixel = 4 bytes
        final int screenBytes = screenHeight * screenWidth * 4;
        // six screens
        return screenBytes * 6;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}