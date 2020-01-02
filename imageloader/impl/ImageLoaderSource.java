package com.hexin.znkflib.support.imageloader.impl;

import android.content.Context;
import android.widget.ImageView;

import com.hexin.znkflib.support.imageloader.api.IImageSource;
import com.hexin.znkflib.support.imageloader.api.ImageInfo;

/**
 * desc: 采用ImageLoader框架实现图片加载
 * @author sunxianglei@myhexin.com
 * @date 2019/8/6.
 */

public class ImageLoaderSource implements IImageSource {

    @Override
    public void loadImage(Context context, ImageInfo info, ImageView imageView) {
        ImageLoader.build(context).bindBitmap(info.url, imageView, info.width, info.height);
    }
}
