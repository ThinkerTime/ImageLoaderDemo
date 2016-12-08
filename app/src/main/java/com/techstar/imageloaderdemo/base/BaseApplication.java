package com.techstar.imageloaderdemo.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * author lrzg on 16/12/8.
 * 描述：
 */

public class BaseApplication extends Application {

    private static Context context;
    private static final String IMAGE_CACHE_PATH = "lanjiejie/Cache";
    private static final int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
    private static final int maxDiskCacheSize = 32 * 1024 * 1024;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initImageLoader(context);
    }

    private void initImageLoader(Context context) {
        //memoryCache(...)和memoryCacheSize(...)这两个参数会互相覆盖，所以在ImageLoaderConfiguration中使用一个就好了
        //diskCacheSize(...)、diskCache(...)和diskCacheFileCount(...)这三个参数会互相覆盖，只使用一个

        //缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, IMAGE_CACHE_PATH);
//        Log.d("data","cacheDir:"+cacheDir);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(2)////线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//加载图像线程的优先级，降低线程的优先级保证主UI线程不受太大影响
                .denyCacheImageMultipleSizesInMemory()// 缓存显示不同大小的同一张图片
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
//                .memoryCache(new UsingFreqLimitedMemoryCache(maxSize)) //你可以通过自己的内存缓存实现
                .memoryCacheSize(maxSize) // 内存缓存的最大值
                .diskCacheSize(maxDiskCacheSize)  // 50 Mb sd卡(本地)缓存的最大值
//                .diskCacheFileCount(100)//缓存在磁盘的最大文件数目
//                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .tasksProcessingOrder(QueueProcessingType.LIFO)//缓存策略，后进先出
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) //图片下载超时时间 connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs(); // Remove for release app 打印debug log  发布应用程序时删除日志
        // imageloader初始化配置
        ImageLoader.getInstance().init(builder.build());

    }
}
