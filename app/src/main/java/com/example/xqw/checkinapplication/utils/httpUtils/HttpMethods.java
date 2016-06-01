package com.example.xqw.checkinapplication.utils.httpUtils;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;
import com.example.xqw.checkinapplication.login.model.LoginService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xqw on 2016/4/26.
 */
public class HttpMethods {

    public static  String BASE_URL = "http://192.168.173.1:8080/nykh/mvc/";

    private static final int DEFAULT_TIMEOUT = 5;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Retrofit retrofit;
    private LoginService loginService;//登录请求

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        loginService = retrofit.create(LoginService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取数据
     * @param subscriber 由调用者传过来的观察者对象
     */
    public void getLoginInfo(Subscriber<LoginRequestEntity<LoginResponseEntity>> subscriber, String userId, String password){
        loginService.getLoginAccount("mobile.AppService","login",userId,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
