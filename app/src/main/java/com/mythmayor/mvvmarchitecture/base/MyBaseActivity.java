package com.mythmayor.mvvmarchitecture.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.mythmayor.mvvmarchitecture.utils.ActivityCollector;

/**
 * Created by mythmayor on 2020/6/30.
 * Activity基类
 */
public abstract class MyBaseActivity extends BaseActivity {

    protected final String TAG = MyBaseActivity.class.getSimpleName();
    public static MyBaseActivity mActivity;
    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕常亮
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityCollector.addActivity(this);
        if (0 != getLayoutResId()) {
            setContentView(getLayoutResId());
        }
        mActivity = this;
        mContext = this;
        //初始化控件
        initView();
        //初始化事件
        initEvent();
        //初始化数据
        initData(getIntent());
    }

    /**
     * 获取布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData(Intent intent);

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mContext = null;
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            if (null != getCurrentFocus() && null != getCurrentFocus().getWindowToken()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
