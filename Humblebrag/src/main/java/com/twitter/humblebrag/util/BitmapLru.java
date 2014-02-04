package com.twitter.humblebrag.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * Created by vipulsomani on 1/27/14.
 */
public class BitmapLru extends LruCache<String, Bitmap> implements ImageCache {

    public BitmapLru(int maxSize) {
        super(maxSize);
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