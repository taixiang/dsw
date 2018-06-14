package com.dingshuwang.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.dingshuwang.R;

import java.io.File;

/**
 * Created by tx on 2017/6/6.
 */

public class GlideImgManager {


    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 APIsignature(new StringSignature(Math.random()+"")).

        Glide.with(context).load(url). crossFade().error(R.mipmap.ic_default).into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.ic_default).into(iv);
    }


    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).error(R.mipmap.ic_default).transform(new GlideCircleTransform(context)).into(iv);
    }

    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).error(R.mipmap.ic_default).transform(new GlideRoundTransform(context,10)).into(iv);
    }


    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);


    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }




}
