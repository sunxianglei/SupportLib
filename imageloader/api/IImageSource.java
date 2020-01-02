package com.hexin.znkflib.support.imageloader.api;

import android.content.Context;
import android.widget.ImageView;

/**
 * desc: 真正的图片加载需要在此接口的方法内实现
 * @author sunxianglei@myhexin.com
 * @date 2019/8/6.
 */

public interface IImageSource {

    /**
     * 加载图片
     * @param context
     * @param info
     * @param imageView
     */
    void loadImage(Context context, ImageInfo info, ImageView imageView);

}
