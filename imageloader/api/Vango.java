package com.hexin.znkflib.support.imageloader.api;

import android.content.Context;
import android.widget.ImageView;

import com.hexin.znkflib.support.imageloader.impl.ImageLoaderSource;

/**
 * desc: Vango是图片加载二次封装框架
 * 生产VangoBuilder对象来添加加载图片的信息。
 * 支持自定义IImageSource源，可替换底层真正的图片加载框架。
 *
 * <p>使用示例：<pre>{@code
 *      Vango.with(getContext())
 *         .load(item.getIcon())
 *         .into(holder.iconView);
 * }</pre>
 *
 * @author sunxianglei@myhexin.com
 * @date 2019/8/6.
 */

public class Vango {

    private IImageSource source;

    private static final class Holder{
        private static final Vango instance = new Vango();
    }

    private Vango(){
        this.source = new ImageLoaderSource();
    }

    public static Vango get(){
        return Holder.instance;
    }

    public static VangoBuilder with(Context context){
        return Vango.get().getVangoBuilder(context);
    }

    private VangoBuilder getVangoBuilder(Context context){
        return new VangoBuilder(source,context);
    }

    public void register(IImageSource source){
        this.source = source;
    }

    public static class VangoBuilder {
        private IImageSource source;
        private Context context;
        private ImageView targetView;
        private String url;
        private int width;
        private int height;
        private ImageInfo info;

        public VangoBuilder(IImageSource source, Context context){
            this.source = source;
            this.context = context;
            info = new ImageInfo();
        }

        public VangoBuilder load(String url){
            this.url = url;
            return this;
        }

        public VangoBuilder size(int width, int height){
            this.width = width;
            this.height = height;
            return this;
        }

        public void into(ImageView view){
            this.targetView = view;
            info.url = url;
            info.height = height;
            info.width = width;
            loadImageUrl();
        }

        private void loadImageUrl(){
            this.source.loadImage(context, info, targetView);
        }
    }

}
