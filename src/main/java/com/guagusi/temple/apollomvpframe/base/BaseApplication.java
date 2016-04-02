package com.guagusi.temple.apollomvpframe.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by maozi on 2015/5/25.
 */
public abstract class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    private Context mContext;

    private Point mResolution;

    private String mPackageName;

    private static BaseApplication mAppInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        //Thread.setDefaultUncaughtExceptionHandler(new BaseUncaughtExceptionHandler());
        mAppInstance = this;
        mContext = getApplicationContext();
        mPackageName = getPackageName();
        initApplication(mContext);
        initBackground(mContext);

    }

    public static BaseApplication getInstance() {
        return mAppInstance;
    }

    /**
     * 执行初始化
     * @param context
     */
    protected abstract void initApplication(Context context);

    /**
     * 执行耗时的初始化
     * @param context
     */
    protected abstract void initBackground(Context context);

    /**
     * app 退出
     */
    protected abstract void onExit();

    /**
     * 获取包名
     * @return
     */
    public String getOmegaPackageName() {
        return mPackageName;
    }

    /**
     * 获取分辨率
     * @return (w,h)
     */
    public Point getResolution() {
        if(mResolution == null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            mResolution = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        return mResolution;
    }
}
