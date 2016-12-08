package com.techstar.imageloaderdemo.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.techstar.imageloaderdemo.R;

/**
 * author lrzg on 16/12/8.
 * 描述：ImageLoader配置
 */

public class ImageLoaderUtil {

    private static ImageLoader sImageLoader;

    public static ImageLoader getImageLoaderInstance(){
        return sImageLoader = ImageLoader.getInstance();
    }

    public static void displayImage(ImageView imageView, String imageUrl) {
//    public static void displayImage(ImageView imageView, String imageUrl, int loadingImg, int emptyImg, int failImg) {
//        sImageLoader = ImageLoader.getInstance();
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20)) //// default  还可以设置圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565)// default 设置图片的解码类型
                .imageScaleType(ImageScaleType.EXACTLY)// default 设置图片以如何的编码方式显示
                .build(); // 构建完成


//                .showImageOnLoading(loadingImg)
//                .showImageForEmptyUri(emptyImg)
//                .showImageOnFail(failImg)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .build();
        sImageLoader.displayImage(imageUrl, imageView, options);

    }


}

