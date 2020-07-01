package com.mythmayor.mvvmarchitecture;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.mythmayor.mvvmarchitecture.base.MyBaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Created by mythmayor on 2020/6/30.
 * 全局Application
 */
public class MyApplication extends Application {

    //全局Context对象
    private static Context mContext;
    //全局Application对象
    private static MyApplication mApplication;
    //Activity集合，用来管理所有的Activity
    private static List<MyBaseActivity> mActivities;
    //要销毁的Activity的集合
    private static Map<String, Activity> mDestoryMap;
    //主线程ID
    private static int mMainTid;

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initNetwork();
        //TODO CrashHandler.getInstance().init(this);
    }

    private void initData() {
        mContext = this;
        mApplication = this;
        mActivities = new LinkedList<>();
        mDestoryMap = new HashMap<>();
        mMainTid = Process.myTid();
    }

    private void initNetwork() {
        OkHttpClient mOkHttpCLient = new OkHttpClient.Builder()
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60000L, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .build();
        OkHttpUtils.initClient(mOkHttpCLient);
    }

    /**
     * 获取全局的Context
     *
     * @return 全局的Context对象
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取全局的Application
     *
     * @return 全局的Application对象
     */
    public static MyApplication getMyApplication() {
        return mApplication;
    }

    /**
     * 获取主线程ID
     *
     * @return 主线程ID
     */
    public int getMainTid() {
        return mMainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity 要添加的Activity
     */
    public void addActivity(MyBaseActivity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除一个Activity
     *
     * @param activity 要移除的Activity
     */
    public void removeActivity(MyBaseActivity activity) {
        mActivities.remove(activity);
    }

    /**
     * 移除当前所有Activity
     */
    public void clearAllActivity() {
        ListIterator<MyBaseActivity> iterator = mActivities.listIterator();
        MyBaseActivity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (null != activity) {
                activity.finish();
            }
        }
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public void addDestoryActivity(Activity activity, String activityName) {
        mDestoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     *
     * @param activityName 要销毁的Activity
     */
    public void destoryActivity(String activityName) {
        Set<String> keySet = mDestoryMap.keySet();
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    mDestoryMap.get(key).finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void quitApplication() {
        clearAllActivity();
        System.exit(0);
    }
}
