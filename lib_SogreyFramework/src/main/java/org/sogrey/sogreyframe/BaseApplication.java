package org.sogrey.sogreyframe;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.socks.library.KLog;

import org.sogrey.sogreyframe.utils.CrashHandler;
import org.sogrey.sogreyframe.utils.FileUtil;

import java.io.File;

/**
 * Created by Sogrey on 2016/8/23.
 */
public abstract class BaseApplication extends Application {

    // 屏幕像素;
    public static int wpx=0;
    public static int hpx=0;

    private static BaseApplication sInstance;

    private static Context applicationContext;

    public static final String TAG="BaseApplication";

    public long dataFrist=0l;

    @Override
    public void onCreate() {
        //		ApiStoreSDK.init(this, Constant.api_key/*您的apikey*/);
        super.onCreate();
        DisplayMetrics mDisplayMetrics=new DisplayMetrics();
        WindowManager  mWm            =(WindowManager)getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(mDisplayMetrics);
        wpx=mDisplayMetrics.widthPixels;
        hpx=mDisplayMetrics.heightPixels;
        sInstance=this;
        applicationContext=this.getApplicationContext();
        FileUtil.initFileDir(sInstance);

        // 极光
        //		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        //		JPushInterface.init(this); // 初始化 JPush


        ImageLoader.getInstance().init(getSimpleConfig());

        if (isLog())
            CrashHandler.getInstance().init(applicationContext);// 初始化异常监控

        KLog.init(isDebugLog());
    }

    /**
     * 是否打印输出异常信息日志文件,正式发布时输出日志可设置为 !BuildConfig.DEBUG
     *
     * @return 是否输出日志文件
     */
    protected abstract boolean isLog();
    /**
     * 是否打印输出Logcat日志信息,正式发布时输出日志可设置为 !BuildConfig.DEBUG
     *
     * @return 是否输出日志信息
     */
    protected abstract boolean isDebugLog();

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static synchronized BaseApplication getInstance() {
        return sInstance;
    }


    /**
     * 比较常用的配置方案
     *
     * @return ImageLoader的配置方案
     */
    private ImageLoaderConfiguration getSimpleConfig() {
        // 设置缓存的路径
        File cacheDir=new File(FileUtil.getImageDownloadDir(sInstance));
        DisplayImageOptions defaultOptions=new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.pic_empty)
                .showImageOnFail(R.drawable.pic_err)
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisc(true).build();
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(
                getApplicationContext()).memoryCacheExtraOptions(480,800)
                                        // 即保存的每个缓存文件的最大长宽
                                        .threadPriority(Thread.NORM_PRIORITY-2)
                                        // 线程池中线程的个数
                                        .denyCacheImageMultipleSizesInMemory()
                                        // 禁止缓存多张图片
                                        .memoryCache(new LRULimitedMemoryCache(40*1024*1024))
                                        // 缓存策略
                                        .memoryCacheSize(16*1024*1024)
                                        // 设置内存缓存的大小
                                        .defaultDisplayImageOptions(defaultOptions)
                                        .discCacheFileNameGenerator(new Md5FileNameGenerator())//
                                        // 缓存文件名的保存方式
                                        .tasksProcessingOrder(QueueProcessingType.LIFO) // 工作队列
                                        .discCacheSize(16*1024*1024)//
                                        .discCacheFileCount(100)// 缓存一百张图片
                                        //                                        .discCache(new
                                        // UnlimitedDiscCache(cacheDir)) // 自定义缓存路径
                                        .writeDebugLogs() // Remove for release app
                                        .build();
        return config;
    }

    public static boolean isAppOnForeground=true;

    public boolean getIsAppOnForeground() {
        return isAppOnForeground;
    }

    public void setIsAppOnForeground(boolean isForeground) {
        isAppOnForeground=isForeground;
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
        isAppOnForeground=false;
    }
}
