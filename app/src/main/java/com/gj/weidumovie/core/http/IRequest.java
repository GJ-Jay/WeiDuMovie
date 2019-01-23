package com.gj.weidumovie.core.http;

import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IRequest {

    //登录
    @POST("movieApi/user/v1/login")
    @FormUrlEncoded
    Observable<Result<UserBean>> login(@Field("phone")String phone, @Field("pwd")String pwd);
    //注册
    @POST("movieApi/user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> register(@Field("phone")String phone,@Field("pwd")String pwd);

}
