package com.example.xqw.checkinapplication.login.model;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;

import rx.Subscriber;

/**
 * Created by xqw on 2016/4/14.
 */
public class LoginModel implements ILoginModel {
    Subscriber<LoginResponseEntity> subscriber;
    boolean isPermission=false;
    LoginRequestEntity loginRequestEntity;
    @Override
    public void setLoginRequestEntity(LoginRequestEntity loginRequestEntity) {
        this.loginRequestEntity=loginRequestEntity;
    }

    @Override
    public LoginRequestEntity getLoginRequestEntity() {
        return loginRequestEntity;
    }

    @Override
    public void saveAccount(LoginResponseEntity loginResponseEntity) {
        SharedPreferencesUtils.writeString("userId",loginResponseEntity.getUserid());
        SharedPreferencesUtils.writeString("password",loginResponseEntity.getPassword());
    }
}
