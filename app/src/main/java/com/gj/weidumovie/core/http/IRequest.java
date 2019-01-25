package com.gj.weidumovie.core.http;

import com.gj.weidumovie.bean.CinemaBean;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserBean;

import java.io.File;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    /**
     * 查询热门电影列表
     */
    @GET("movieApi/movie/v1/findHotMovieList")
    Observable<Result<List<MoiveBean>>> findHotMovieList(@Header("userId") int userId,
                                                         @Header("sessionId")String sessionId,
                                                         @Query("page")int page,
                                                         @Query("count")int count);


    //修改头像
    @POST("movieApi/user/v1/verify/uploadHeadPic")
    @FormUrlEncoded
    Observable<Result> updateHead(@Header("userId")int userId,
                                  @Header("sessionId")String sessionId,
                                  @Field("image")String image);

    /**
     * 正在热映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<List<MoiveBean>>> findReleaseMovieList(@Header("userId") int userId,
                                                                 @Header("sessionId")String sessionId,
                                                                 @Query("page")int page,
                                                                 @Query("count")int count);

    /**
     * 即将上映
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<MoiveBean>>> findComingSoonMovieList(@Header("userId") int userId,
                                                                    @Header("sessionId")String sessionId,
                                                                    @Query("page")int page,
                                                                    @Query("count")int count);
    //推荐影院列表
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<CinemaBean>>> findRecommendCinemas(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);
    //附近影院列表
    @GET("movieApi/cinema/v1/findNearbyCinemas")
    Observable<Result<List<CinemaBean>>> findNearbyCinemas(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("longitude") String longitude,
                                                @Query("latitude") String latitude,
                                                @Query("page") int page,
                                                @Query("count") int count);
}
