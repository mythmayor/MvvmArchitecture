package com.mythmayor.mvvmarchitecture.request;

import androidx.databinding.Bindable;

import com.mythmayor.mvvmarchitecture.BR;

/**
 * Created by mythmayor on 2020/6/30.
 * 登录接口请求实体类
 */
public class LoginRequest extends BaseRequest {

    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
