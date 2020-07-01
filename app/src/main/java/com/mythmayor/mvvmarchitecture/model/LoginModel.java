package com.mythmayor.mvvmarchitecture.model;

import com.mythmayor.mvvmarchitecture.request.LoginRequest;
import com.mythmayor.mvvmarchitecture.response.LoginResponse;
import com.mythmayor.mvvmarchitecture.utils.net.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录Model
 */
public class LoginModel  {

    public Observable<LoginResponse> login(LoginRequest request) {
        return RetrofitClient.getInstance().getApi().login(request.getUsername(), request.getPassword());
    }
}
