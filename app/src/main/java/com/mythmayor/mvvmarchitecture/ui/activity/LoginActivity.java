package com.mythmayor.mvvmarchitecture.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.gyf.immersionbar.ImmersionBar;
import com.mythmayor.mvvmarchitecture.R;
import com.mythmayor.mvvmarchitecture.base.BaseMvvmActivity;
import com.mythmayor.mvvmarchitecture.databinding.ActivityLoginBinding;
import com.mythmayor.mvvmarchitecture.receiver.NetworkBroadcastReceiver;
import com.mythmayor.mvvmarchitecture.request.LoginRequest;
import com.mythmayor.mvvmarchitecture.response.BaseResponse;
import com.mythmayor.mvvmarchitecture.response.LoginResponse;
import com.mythmayor.mvvmarchitecture.utils.IntentUtil;
import com.mythmayor.mvvmarchitecture.utils.LogUtil;
import com.mythmayor.mvvmarchitecture.utils.ProgressDlgUtil;
import com.mythmayor.mvvmarchitecture.utils.ToastUtil;
import com.mythmayor.mvvmarchitecture.viewmodel.LoginViewModel;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录页面
 */
public class LoginActivity extends BaseMvvmActivity<LoginViewModel, ActivityLoginBinding> implements NetworkBroadcastReceiver.NetworkListener {

    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;//网络连接状态监听
    private LoginRequest mLoginRequest;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEvent() {
        mViewDataBinding.setLoginActivity(this);
    }

    @Override
    protected void initData(Intent intent) {
        mLoginRequest = new LoginRequest();
        mViewDataBinding.setLoginRequest(mLoginRequest);
        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(R.id.view_blank).init();
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
        //注册网络连状态监听
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction(NetworkBroadcastReceiver.ACTION_CONNECTIVITY_CHANGE);
        registerReceiver(mNetworkBroadcastReceiver, networkFilter);
        mNetworkBroadcastReceiver.addNetworkListener(this);
        //注册Lifecycle
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                LogUtil.d(event.name());
            }
        });
    }

    public void login(View v) {
        String username = mLoginRequest.getUsername();
        String password = mLoginRequest.getPassword();
        LogUtil.i("username=" + username + ", password=" + password);
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "请输入账号和密码");
            return;
        }
        mViewModel.login(this, mLoginRequest);
    }

    @Override
    public void onSuccess(BaseResponse baseResp) {
        LoginResponse resp = (LoginResponse) baseResp;
        if (resp.getErrorCode() == 0) {//登录成功
            ToastUtil.showToast(this, "登录成功: " + resp.getData().getUsername());
            IntentUtil.startActivity(this, MainActivity.class);
            finish();
        } else {//登录失败
            ToastUtil.showToast(this, resp.getErrorMsg());
        }
    }

    @Override
    public void showLoading() {
        ProgressDlgUtil.show(this, "正在登录，请稍后...");
    }

    @Override
    public void hideLoading() {
        ProgressDlgUtil.dismiss();
    }

    @Override
    public void onError(String errMessage) {
        ToastUtil.showToast(this, errMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkBroadcastReceiver.removeNetworkListener(this);
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
