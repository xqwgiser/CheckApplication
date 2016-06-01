package com.example.xqw.checkinapplication.login.model;

import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xqw on 2016/4/26.
 */
public interface LoginService {
    @GET("mobile")
    Observable<LoginRequestEntity<LoginResponseEntity>> getLoginAccount(@Query("service") String service,
                                                                        @Query("method") String method,
                                                                        @Query("userid") String userId,
                                                                        @Query("password") String password);
}