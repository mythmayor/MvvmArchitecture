package com.mythmayor.mvvmarchitecture.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.mvvmarchitecture.R;
import com.mythmayor.mvvmarchitecture.base.BaseDialog;
import com.mythmayor.mvvmarchitecture.base.MyBaseActivity;
import com.mythmayor.mvvmarchitecture.receiver.NetworkBroadcastReceiver;
import com.mythmayor.mvvmarchitecture.ui.dialog.LogoutDialog;
import com.mythmayor.mvvmarchitecture.utils.IntentUtil;
import com.mythmayor.mvvmarchitecture.utils.LogUtil;
import com.mythmayor.mvvmarchitecture.utils.PrefUtil;
import com.mythmayor.mvvmarchitecture.utils.ToastUtil;

/**
 * Created by mythmayor on 2020/6/30.
 * 主页面
 */
public class MainActivity extends MyBaseActivity implements NetworkBroadcastReceiver.NetworkListener {

    private TextView tvlogout;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;//网络连接状态监听
    private final long EXIT_APP_BACK_PRESSED_INTERVAL = 1500;
    private long mCurrBackPressTimeMillis;
    private long mPrevBackPressTimeMillis;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.color_white).fitsSystemWindows(true).init();
        tvlogout = (TextView) findViewById(R.id.tv_logout);
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
    }

    @Override
    protected void initEvent() {
        tvlogout.setOnClickListener(this);
    }

    @Override
    protected void initData(Intent intent) {
        PrefUtil.putBoolean(this, PrefUtil.IS_USER_LOGIN, true);
        //注册网络连状态监听
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction(NetworkBroadcastReceiver.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(mNetworkBroadcastReceiver, networkFilter);
        mNetworkBroadcastReceiver.addNetworkListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logout:
                showLogoutDialog();
                break;
            default:
                break;
        }
    }

    private void showLogoutDialog() {
        String content = "确定要退出登录么？";
        final LogoutDialog dialog = new LogoutDialog(this, content, "取消", "确定");
        dialog.setNoOnclickListener(new BaseDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(Object o) {
                dialog.dismiss();
            }
        });
        dialog.setYesOnclickListener(new BaseDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(Object o) {
                dialog.dismiss();
                PrefUtil.clear(MainActivity.this);
                IntentUtil.startActivityClearTask(MainActivity.this, LoginActivity.class);
            }
        });
        dialog.show();
        //ProjectUtil.setDialogWindowAttr(dialog);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void exitApp() {
        mCurrBackPressTimeMillis = System.currentTimeMillis();
        if (mCurrBackPressTimeMillis - mPrevBackPressTimeMillis < EXIT_APP_BACK_PRESSED_INTERVAL) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            finish();
        } else {
            mPrevBackPressTimeMillis = mCurrBackPressTimeMillis;
            ToastUtil.showToast(this, "再按一次退出软件");
        }
    }

    private void destroy() {
        mNetworkBroadcastReceiver.removeNetworkListener(this);
        if (mNetworkBroadcastReceiver != null) {
            unregisterReceiver(mNetworkBroadcastReceiver);
        }
    }

    @Override
    public void onNetworkListener(int status) {
        if (status == NetworkBroadcastReceiver.NETWORK_NONE) {//无网络连接
            LogUtil.d("无网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_MOBILE) {//移动网络连接
            LogUtil.d("移动网络连接");
        } else if (status == NetworkBroadcastReceiver.NETWORK_WIFI) {//无线网络连接
            LogUtil.d("无线网络连接");
        }
    }
}
