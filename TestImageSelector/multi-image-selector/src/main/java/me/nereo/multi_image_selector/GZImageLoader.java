package me.nereo.multi_image_selector;

import android.content.Context;
import android.graphics.drawable.Drawable;

import me.nereo.multi_image_selector.widget.zoomview.GFImageView;

/**
 * GZImageLoader抽象类，为了兼容不同的图片加载库(ImageLoader,Picasso,Fresco,Xutils,Glide...),
 * 外部需要实现这个类去加载图片
 * Created by Haoxunwang on 2016/1/7.
 */
public interface GZImageLoader{
    void displayImage(Context context, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height);
    void clearMemoryCache();
}
