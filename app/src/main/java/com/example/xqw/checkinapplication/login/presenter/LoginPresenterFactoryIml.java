package com.example.xqw.checkinapplication.login.presenter;

import android.util.Log;

import com.example.xqw.checkinapplication.login.model.ILoginModel;
import com.example.xqw.checkinapplication.login.model.LoginModel;
import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;
import com.example.xqw.checkinapplication.login.view.ILoginView;
import com.example.xqw.checkinapplication.utils.httpUtils.HttpMethods;

import rx.Subscriber;

/**
 * Created by xqw on 2016/4/14.
 */
public class LoginPresenterFactoryIml implements ILoginPresenterFactory<ILoginPresenter> {
    private ILoginModel loginModel;

    public LoginPresenterFactoryIml(ILoginView loginView) {
        this.loginView = loginView;
    }

    private ILoginView loginView;
    @Override
    public ILoginPresenter create() {
        loginModel = new LoginModel();
        return new ILoginPresenter() {


            @Override
            public void onViewAttached() {
                loginModel = new LoginModel();
            }

            @Override
            public void onViewDetached() {
                loginModel = null;
            }

            @Override
            public void onDestroyed() {
                loginModel = null;
            }

            @Override
            public void login() {
                loginView.showProgressDialog();
                Subscriber<LoginRequestEntity<LoginResponseEntity>> subscriber=new Subscriber<LoginRequestEntity<LoginResponseEntity>>() {
                    @Override
                    public void onCompleted() {
                        loginView.hideProgressDialog();
                        loginView.goToMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //网络错误
                        loginView.hideProgressDialog();
                        loginView.showNetWorkError();
                    }

                    @Override
                    public void onNext(LoginRequestEntity<LoginResponseEntity> loginAccountEntityHttpResult) {
                        loginView.showUserInfo(loginAccountEntityHttpResult.getData());
                    }

                };
                HttpMethods.BASE_URL=loginView.getBaseUrl()+"/nykh/mvc/";
                Log.e("url",loginView.getBaseUrl()+"/nykh/mvc/");
                HttpMethods.getInstance().getLoginInfo(subscriber,loginModel.getLoginRequestEntity().getUserid(),
                        loginModel.getLoginRequestEntity().getPassword());
            }

            @Override
            public void saveLoginRequestEntity(LoginRequestEntity loginRequestEntity) {
                loginModel.setLoginRequestEntity(loginRequestEntity);
            }
        };

    }
}
