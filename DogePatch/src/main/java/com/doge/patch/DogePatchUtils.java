package com.doge.patch;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class DogePatchUtils {
    private static HashMap<Context, RequestQueue> mRequestQueues = new HashMap<Context, RequestQueue>();

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueues.get(context) == null) {
            mRequestQueues.put(context, Volley.newRequestQueue(context));
        }
        return mRequestQueues.get(context);
    }
}
