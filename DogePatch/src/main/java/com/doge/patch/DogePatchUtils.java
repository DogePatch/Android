package com.doge.patch;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class DogePatchUtils {
    private static HashMap<Context, RequestQueue> mRequestQueues = new HashMap<Context, RequestQueue>();
    private static HashMap<Context, ImageLoader> mImageLoaders = new HashMap<Context, ImageLoader>();

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueues.get(context) == null) {
            mRequestQueues.put(context, Volley.newRequestQueue(context));
        }
        return mRequestQueues.get(context);
    }

    public static ImageLoader getImageLoader(Context context) {
        if (mImageLoaders.get(context) == null) {
            mImageLoaders.put(context, new ImageLoader(getRequestQueue(context), BitmapLruCache.getInstance()));
        }
        return mImageLoaders.get(context);
    }
}
