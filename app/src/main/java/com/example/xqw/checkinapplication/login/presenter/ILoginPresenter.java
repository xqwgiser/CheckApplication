package com.example.xqw.checkinapplication.login.presenter;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;

/**
 * Created by xqw on 2016/4/14.
 */
public interface ILoginPresenter{
    void onViewAttached();
    void onViewDetached();
    void onDestroyed();
    void login();
    void saveLoginRequestEntity(LoginRequestEntity loginRequestEntity);
}
