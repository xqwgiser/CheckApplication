package com.example.xqw.checkinapplication.login.model;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;

/**
 * Created by xqw on 2016/4/13.
 */
public interface ILoginModel {
    void setLoginRequestEntity(LoginRequestEntity loginRequestEntity );
    LoginRequestEntity getLoginRequestEntity();
    void saveAccount(LoginResponseEntity loginResponseEntity);
}
