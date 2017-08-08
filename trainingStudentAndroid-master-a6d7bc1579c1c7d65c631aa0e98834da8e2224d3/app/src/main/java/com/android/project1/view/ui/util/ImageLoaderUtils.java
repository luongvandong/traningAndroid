package com.android.project1.view.ui.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by ka on 03/11/2016.
 */

public class ImageLoaderUtils {

    public static void show(Context context, String uri, ImageView iv) {
        Glide.with(context)
                .load(uri)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }
}
