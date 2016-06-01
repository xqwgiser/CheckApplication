package com.example.xqw.checkinapplication.utils.httpUtils;

import com.example.xqw.checkinapplication.bean.ItemEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xqw on 2016/5/6.
 */
public interface Service {
    @GET("execute")
    Observable<List<Map>> getType(@Query("service") String service, @Query("method") String method);
    @FormUrlEncoded
    @POST("execute")
    Observable<ItemEntity<String>> postInfo(@Field("service") String service,
                                            @Field("method") String method,
                                            @Field("rows") String rows);
    @GET("execute")
    Observable<ItemEntity<List<Map>>> getList(@Query("service") String service,
                                              @Query("method") String method,
                                              @Query("page") String page,
                                              @Query("pageSize") String pageSize);
}
