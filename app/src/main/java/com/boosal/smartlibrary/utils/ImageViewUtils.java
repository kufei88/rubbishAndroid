package com.boosal.smartlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.boosal.smartlibrary.R;


import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ImageViewUtils {

    //ui渲染图片
    public static void loadImage(Context context, String imageUrl, ImageView imageView, int errorRes){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(errorRes)
                .placeholder(R.color.color_f6)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }
    
    public static void loadGoodDetailImage(Context context, String imageUrl, int width, int height, ImageView imageView, int errorRes){
        RequestOptions options = new RequestOptions()
                .override(width,height)
                .error(errorRes)
                .placeholder(R.color.color_f6)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
    }
}
