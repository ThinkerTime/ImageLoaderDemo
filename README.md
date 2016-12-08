# ImageLoaderDemo
universalimageloader的使用
1、在项目的build.gradle添加jar引用
  compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
2、在Application初始化ImageLoader
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
3、创建ImageLoaderUtil工具类
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
4、在代码中使用
 ImageLoaderUtil.displayImageView,imageUrls);
5、ListView、GridView快速滑动是禁止加载图片
5.1）
 mImage_lv.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getImageLoaderInstance(), true, true)); // 设置滚动时不加载图片
5.2）在ViewHolder通过imageView的tag判断是否显示图片
    viewHolder.image.setTag(imageUrls[position]);

            if(viewHolder.image.getTag().equals(imageUrls[position])){
                /**
                 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件
                 */
                ImageLoaderUtil.displayImage(viewHolder.image,imageUrls[position]);
                viewHolder.text.setText("Item " + (position + 1)); // TextView设置文本
            }
