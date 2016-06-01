package com.example.xqw.checkinapplication.login.view;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;

/**
 * Created by xqw on 2016/4/13.
 */
public interface ILoginView {
    void showProgressDialog();
    void hideProgressDialog();
    void goToMainActivity();
    LoginRequestEntity getUserInfo();
    void showUserInfo(LoginResponseEntity loginResponseEntity);
    void showNetWorkError();
    String getBaseUrl();
}
