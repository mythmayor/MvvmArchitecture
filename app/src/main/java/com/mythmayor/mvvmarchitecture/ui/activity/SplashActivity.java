package com.mythmayor.mvvmarchitecture.ui.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mythmayor.mvvmarchitecture.R;
import com.mythmayor.mvvmarchitecture.base.LifecycleHandler;
import com.mythmayor.mvvmarchitecture.base.MyBaseActivity;
import com.mythmayor.mvvmarchitecture.utils.IntentUtil;
import com.mythmayor.mvvmarchitecture.utils.PrefUtil;

/**
 * Created by mythmayor on 2020/6/30.
 * 启动页面
 */
public class SplashActivity extends MyBaseActivity {

    // 用于判断是否从后台返回或者是否到后台
    private static boolean isAppWentToBg = false;
    private static boolean isWindowFocused = false;
    private static boolean isBackPressed = false;

    private LifecycleHandler mHandler;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Intent intent) {
        Lifecycle lifecycle = getLifecycle();
        mHandler = new LifecycleHandler(new LifecycleOwner() {
            @NonNull
            @Override
            public Lifecycle getLifecycle() {
                return lifecycle;
            }
        });
    }

    private void enterMyApp() {
        boolean isUserLogin = PrefUtil.getBoolean(mContext, PrefUtil.IS_USER_LOGIN, false);
        if (isUserLogin) {
            if (isAppWentToBg) {
                IntentUtil.startActivity(this, MainActivity.class);
                finish();
            } else {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        IntentUtil.startActivity(SplashActivity.this, MainActivity.class);
                        finish();
                        overridePendingTransition(R.anim.anim_static, R.anim.anim_static);
                    }
                }, 1500);
            }
        }else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    IntentUtil.startActivity(SplashActivity.this, LoginActivity.class);
                    finish();
                    overridePendingTransition(R.anim.anim_static, R.anim.anim_static);
                }
            }, 1500);
        }
        if (isAppWentToBg) {
            isAppWentToBg = false;
            // 从后台返回
            //ToastUtil.showToast(this, "从后台返回");
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //isBackPressed = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        enterMyApp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isWindowFocused) {
            isAppWentToBg = true;
            // 进入后台
            //ToastUtil.showToast(this, "进入后台");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
