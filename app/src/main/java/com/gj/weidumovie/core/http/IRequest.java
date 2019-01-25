package com.gj.weidumovie.core.http;

import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserBean;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
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
    @FormUrlEncoded
    @POST("movieApi/user/v1/registerUser")
    Observable<Result> register(@Field("nickName") String nickName,
                                @Field("phone") String phone,
                                @Field("pwd") String pwd,
                                @Field("pwd2") String pwd2,
                                @Field("sex") int sex,
                                @Field("birthday") String birthday,
                                @Field("imei") String imei,
                                @Field("ua") String ua,
                                @Field("screenSize") String screenSize,
                                @Field("os") String os,
                                @Field("email") String email);

    //修改头像
    @POST("movieApi/user/v1/verify/uploadHeadPic")
    @FormUrlEncoded
    Observable<Result> updateHead(@Header("userId")int userId,
                                  @Header("sessionId")String sessionId,
                                  @Field("image")String image);

}
