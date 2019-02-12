package com.gj.weidumovie.core.http;

import com.gj.weidumovie.bean.BuyTicket;
import com.gj.weidumovie.bean.CinemaBean;
import com.gj.weidumovie.bean.FilmReviewBean;
import com.gj.weidumovie.bean.FindMessageList;
import com.gj.weidumovie.bean.LikeCinema;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.MovieDetailsBean;
import com.gj.weidumovie.bean.MovieScheduleBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UpdateUser;
import com.gj.weidumovie.bean.UserBean;
import com.gj.weidumovie.bean.UserInfo;
import com.gj.weidumovie.bean.WxLogin;

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

    //意见反馈
    @POST("movieApi/tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result> feedBack(@Header("userId")int userId,
                                  @Header("sessionId")String sessionId,
                                  @Field("content")String content);

    //根据用户ID查询用户信息
    @GET("movieApi/user/v1/verify/getUserInfoByUserId")
    Observable<Result<UserInfo>> queryUserInfo(@Header("userId")int userId,
                                               @Header("sessionId")String sessionId);

    //修改用户信息
    @POST("movieApi/user/v1/verify/modifyUserInfo")
    @FormUrlEncoded
    Observable<Result<UpdateUser>> updateUser(@Header("userId")int userId,
                                              @Header("sessionId")String sessionId,
                                              @Field("nickName")String nickName,
                                              @Field("sex")int sex,
                                              @Field("email")String email);

    //修改密码
    @POST("movieApi/user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> updatePwd(@Header("userId")int userId,
                                 @Header("sessionId")String sessionId,
                                 @Field("oldPwd")String oldPwd,
                                 @Field("newPwd")String newPwd,
                                 @Field("newPwd2")String newPwd2);

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

    //通过电影Id查看电影详情
    @GET("movieApi/movie/v1/findMoviesDetail")
    Observable<Result<MovieDetailsBean>> findMoviesDetail(@Header("userId")int userId,
                                                          @Header("sessionId")String sessionId,@Query("movieId")int movieId);

    //我关注的影片
    @GET("movieApi/movie/v1/verify/findMoviePageList")
    Observable<Result<List<LikeMovie>>> myLikeMovie(@Header("userId")int userId,
                                                    @Header("sessionId")String sessionId,
                                                    @Query("page")int page,
                                                    @Query("count")int count);

    //用户关注的影院
    @GET("movieApi/cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<LikeCinema>>> myLikeCinema(@Header("userId")int userId,
                                                      @Header("sessionId")String sessionId,
                                                      @Query("page")int page,
                                                      @Query("count")int count);

    //用户购票记录查询列表
    @GET("movieApi/user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<BuyTicket>>> findUserBuyTicketRecordList(@Header("userId")int userId,
                                                                    @Header("sessionId")String sessionId,
                                                                    @Query("page")int page,
                                                                    @Query("count")int count,
                                                                    @Query("status")int status);

    //关注影院
    @GET("movieApi/cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId")int userId,
                                    @Header("sessionId")String sessionId,
                                    @Query("cinemaId")int cinemaId);

    //取消关注影院
    @GET("movieApi/cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId")int userId,
                                          @Header("sessionId")String sessionId,
                                          @Query("cinemaId")int cinemaId);
    //关注影片
    @GET("movieApi/movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId")int userId,
                                   @Header("sessionId")String sessionId,
                                   @Query("movieId")int cinemaId);
    //取消关注影片
    @GET("movieApi/movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId")int userId,
                                          @Header("sessionId")String sessionId,
                                          @Query("movieId")int cinemaId);

    //查询电影影评
    @GET("movieApi/movie/v1/findAllMovieComment")
    Observable<Result<List<FilmReviewBean>>> findAllMovieComment(@Header("userId")int userId,
                                                                 @Header("sessionId")String sessionId,
                                                                 @Query("movieId") int movieId,
                                                                 @Query("page") int page,
                                                                 @Query("count") int count);
    //根据电影ID查询当前排片该电影的影院列表
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<CinemaBean>>> findCinemasListByMovieId(@Query("movieId") int movieId);
    //根据电影ID和影院ID查询电影排期列表
    @GET("movieApi/movie/v1/findMovieScheduleList")
    Observable<Result<List<MovieScheduleBean>>> findMovieScheduleList(@Query("cinemasId") int cinemasId,@Query("movieId") int movieId);

    @POST("movieApi/movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> buyMovieTicket(@Header("userId")int userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("scheduleId") int scheduleId,
                                      @Field("amount")int amount,
                                      @Field("sign")String sign);
    @POST("movieApi/movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<Result> buyMovieresult(@Header("userId")int userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("payType") int payType,
                                      @Field("orderId") String orderId);
    //根据影院ID查询该影院当前排期的电影列表
    @GET("movieApi/movie/v1/findMovieListByCinemaId")
    Observable<Result<List<MoiveBean>>> findMovieListByCinemaId(@Query("cinemaId") int cinemaId);

    //微信登录
    @POST("movieApi/user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<WxLogin>> weChatBindingLogin(@Field("code")String code);

    //用户签到
    @GET("movieApi/user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId")int userId,
                                  @Header("sessionId")String sessionId);

    //查询新版本
    @GET("movieApi/tool/v1/findNewVersion")
    Observable<Result> findNewVersion(@Header("userId")int userId,
                                      @Header("sessionId")String sessionId,
                                      @Header("ak")String ak);


    //查询系统消息列表
    @GET("movieApi/tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<FindMessageList>>> findAllSysMsgList(@Header("userId")int userId,
                                                                @Header("sessionId")String sessionId,
                                                                @Query("page")int page,
                                                                @Query("count")int count);
}
