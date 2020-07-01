package com.mythmayor.mvvmarchitecture.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mythmayor.mvvmarchitecture.itype.NetCallback;
import com.mythmayor.mvvmarchitecture.model.LoginModel;
import com.mythmayor.mvvmarchitecture.request.LoginRequest;
import com.mythmayor.mvvmarchitecture.response.LoginResponse;
import com.mythmayor.mvvmarchitecture.ui.activity.LoginActivity;
import com.mythmayor.mvvmarchitecture.utils.LogUtil;
import com.mythmayor.mvvmarchitecture.utils.net.NetUtil;
import com.mythmayor.mvvmarchitecture.utils.net.RxScheduler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.Call;

/**
 * Created by mythmayor on 2020/6/30.
 */
public class LoginViewModel extends ViewModel {

    private LoginModel mLoginModel;
    private LoginActivity mView;

    public LoginViewModel() {
        mLoginModel = new LoginModel();
    }

    public void login(LoginActivity view, LoginRequest request) {
        mView = view;
        useRetrofit(request);
        //useOkHttpUtils(request);
    }

    private void useRetrofit(LoginRequest request) {
        mLoginModel.login(request)
                .compose(RxScheduler.Obs_io_main())
                .to(mView.bindAutoDispose())//解决内存泄漏
                .subscribe(new Observer<LoginResponse>() {//这里需要在build.gradle中指定jdk版本，否则会报错
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull LoginResponse bean) {
                        mView.onSuccess(bean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(e.getMessage());
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    private void useOkHttpUtils(LoginRequest request) {
        mView.showLoading();
        NetUtil.login2(request, new NetCallback() {
            @Override
            public void onSuccess(String response, int id) {
                mView.hideLoading();
                LogUtil.i(response);
                LoginResponse resp = NetUtil.mGson.fromJson(response, LoginResponse.class);
                mView.onSuccess(resp);
            }

            @Override
            public void onFailed(Call call, Exception e, int id) {
                mView.hideLoading();
                mView.onError(e.getMessage());
            }
        });
    }
}
