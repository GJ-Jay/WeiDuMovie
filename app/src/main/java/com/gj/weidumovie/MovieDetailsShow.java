package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.ReviewAdapter;
import com.gj.weidumovie.adapter.StillsAdapter;
import com.gj.weidumovie.adapter.TrailerAdapter;
import com.gj.weidumovie.bean.FilmReviewBean;
import com.gj.weidumovie.bean.MovieDetailsBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindAllMovieCommentPresenter;
import com.gj.weidumovie.presenter.FindMoviesDetailPresenter;
import com.gj.weidumovie.util.SpaceItemDecoration;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsShow extends WDActivity {


    @BindView(R.id.detalishomepagesdvone)
    ImageView detalishomepagesdvone;
    @BindView(R.id.movies_show_name)
    TextView moviesShowName;
    @BindView(R.id.movies_show_simple)
    SimpleDraweeView moviesShowSimple;
    @BindView(R.id.movies_show_xq)
    Button moviesShowXq;
    @BindView(R.id.movies_show_yg)
    Button moviesShowYg;
    @BindView(R.id.movies_show_jz)
    Button moviesShowJz;
    @BindView(R.id.movies_show_yp)
    Button moviesShowYp;
    @BindView(R.id.detalisgroup)
    LinearLayout detalisgroup;
    @BindView(R.id.movies_show_back)
    ImageView moviesShowBack;
    @BindView(R.id.movies_show_buy)
    Button moviesShowBuy;
    @BindView(R.id.movies_show_like)
    CheckBox moviesShowLike;
    private FindMoviesDetailPresenter findMoviesDetailPresenter;
    private MovieDetailsBean result;
    private PopupWindow detailWindow;
    private PopupWindow trailerWindow;
    private PopupWindow stillsWindow;
    private PopupWindow reviewWindow;
    private View parent;
    private FindAllMovieCommentPresenter findAllMovieCommentPresenter;
    private int userId;
    private String sessionId;
    private int id;
    private ReviewAdapter reviewAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details_show;

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        Log.d("abc", "initView: " + id);
        SharedPreferences sharedPreferences = getSharedPreferences("Config", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        sessionId = sharedPreferences.getString("sessionId", "");
        findMoviesDetailPresenter = new FindMoviesDetailPresenter(new FindMoviesDetail());
        findMoviesDetailPresenter.reqeust(userId, sessionId, id);

        parent = View.inflate(MovieDetailsShow.this, R.layout.activity_movie_details_show, null);

    }

    private void getShow() {
        View contentView = View.inflate(MovieDetailsShow.this, R.layout.pupo_detail, null);
        detailWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        detailWindow.setFocusable(true);//获取焦点
        detailWindow.setTouchable(true);//
        //点击窗口外部窗口不消失
        detailWindow.setOutsideTouchable(true);
        detailWindow.setBackgroundDrawable(new BitmapDrawable());
        getShowDetail(contentView);

        View contentView_trailer = View.inflate(MovieDetailsShow.this, R.layout.pup_film_review, null);
        trailerWindow = new PopupWindow(contentView_trailer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        trailerWindow.setFocusable(true);//获取焦点
        trailerWindow.setTouchable(true);//
        //点击窗口外部窗口不消失
        trailerWindow.setOutsideTouchable(true);
        trailerWindow.setBackgroundDrawable(new BitmapDrawable());
        getShowTrailer(contentView_trailer);
        View contentView_stills = View.inflate(MovieDetailsShow.this, R.layout.pup_stills, null);
        stillsWindow = new PopupWindow(contentView_stills, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        stillsWindow.setFocusable(true);//获取焦点
        stillsWindow.setTouchable(true);//
        //点击窗口外部窗口不消失
        stillsWindow.setOutsideTouchable(true);
        stillsWindow.setBackgroundDrawable(new BitmapDrawable());
        getShowStills(contentView_stills);
        View contentView_review = View.inflate(MovieDetailsShow.this, R.layout.pup_review, null);
        reviewWindow = new PopupWindow(contentView_review, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        reviewWindow.setFocusable(true);//获取焦点
        reviewWindow.setTouchable(true);//
        //点击窗口外部窗口不消失
        reviewWindow.setOutsideTouchable(true);
        reviewWindow.setBackgroundDrawable(new BitmapDrawable());
        getShowReview(contentView_review);


    }

    private void getShowReview(View contentView_review) {
        findAllMovieCommentPresenter = new FindAllMovieCommentPresenter(new FindAllMovieComment());
        ImageView pupo_yp_back = contentView_review.findViewById(R.id.pupo_yp_back);
        pupo_yp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewWindow.dismiss();
            }
        });
        RecyclerView review_movie_review= contentView_review.findViewById(R.id.review_movie_review);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        review_movie_review.addItemDecoration(new SpaceItemDecoration(10));
        review_movie_review.setLayoutManager(linearLayoutManager);
        findAllMovieCommentPresenter.reqeust(userId,sessionId,id,false,10);
        reviewAdapter = new ReviewAdapter();
        review_movie_review.setAdapter(reviewAdapter);

    }

    private void getShowStills(View contentView_stills) {
        ImageView pupo_jz_back = contentView_stills.findViewById(R.id.pupo_jz_back);
        pupo_jz_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stillsWindow.dismiss();
            }
        });
        RecyclerView stills_movie_review= contentView_stills.findViewById(R.id.stills_movie_review);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        stills_movie_review.setLayoutManager(staggeredGridLayoutManager);
        stills_movie_review.addItemDecoration(new SpaceItemDecoration(10));
        List<String> posterList = result.getPosterList();
        StillsAdapter stillsAdapter = new StillsAdapter();
        stillsAdapter.setList(posterList);
        stills_movie_review.setAdapter(stillsAdapter);
    }

    private void getShowTrailer(View contentView_trailer) {
        RecyclerView recycler_movie_review= contentView_trailer.findViewById(R.id.recycler_movie_review);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_movie_review.setLayoutManager(linearLayoutManager);
        recycler_movie_review.addItemDecoration(new SpaceItemDecoration(20));
        TrailerAdapter trailerAdapter = new TrailerAdapter();
        trailerAdapter.setList(result.getShortFilmList());
        trailerAdapter.setContext(this);
        recycler_movie_review.setAdapter(trailerAdapter);
        ImageView pupo_yg_back = contentView_trailer.findViewById(R.id.pupo_yg_back);
        pupo_yg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trailerWindow.dismiss();
            }
        });
    }

    private void getShowDetail(View contentView) {
        TextView tv_pop_title = contentView.findViewById(R.id.tv_pop_title);
        TextView tv_type = contentView.findViewById(R.id.tv_type);
        TextView tv_director = contentView.findViewById(R.id.tv_director);
        TextView tv_length = contentView.findViewById(R.id.tv_length);
        TextView tv_jianjie = contentView.findViewById(R.id.tv_jianjie);
        TextView tv_placeoforigin = contentView.findViewById(R.id.tv_placeoforigin);
        TextView tv_performer01 = contentView.findViewById(R.id.tv_performer01);
        TextView tv_performer = contentView.findViewById(R.id.tv_performer);
        TextView tv_role01 = contentView.findViewById(R.id.tv_role01);
        TextView tv_role02 = contentView.findViewById(R.id.tv_role02);
        ImageView pupo_xq_back = contentView.findViewById(R.id.pupo_xq_back);
        tv_pop_title.setText(result.getName());
        SimpleDraweeView  simp_pop_movie=contentView.findViewById(R.id.simp_pop_movie);
        simp_pop_movie.setImageURI(result.getImageUrl());
        tv_type.setText(result.getMovieTypes());
        tv_director.setText(result.getDirector());
        tv_length.setText(result.getDuration());
        tv_placeoforigin.setText(result.getPlaceOrigin());
        tv_jianjie.setText(result.getSummary());
        pupo_xq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailWindow.dismiss();
            }
        });
        String[] split = result.getStarring().split(",");
        tv_performer01.setText(split[0]);
        tv_role01.setText(split[1]);
        tv_performer.setText(split[2]);
        tv_role02.setText(split[3]);
    }


    @Override
    protected void destoryData() {
        findMoviesDetailPresenter.unBind();
        findAllMovieCommentPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.movies_show_xq, R.id.movies_show_yg, R.id.movies_show_jz, R.id.movies_show_yp, R.id.movies_show_back, R.id.movies_show_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movies_show_xq:
                detailWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);


                break;
            case R.id.movies_show_yg:
                trailerWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.movies_show_jz:
                stillsWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.movies_show_yp:
                reviewWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.movies_show_back:
                finish();
                break;
            case R.id.movies_show_buy:
                    Intent intent = new Intent(MovieDetailsShow.this,MyCanBuyMovieActivity.class);
                    //intent.putExtra("movie",result);
                    intent.putExtra("movie",result);
                    startActivity(intent);
                break;
        }
    }
    class FindAllMovieComment implements DataCall<Result<List<FilmReviewBean>>>{

        @Override
        public void success(Result<List<FilmReviewBean>> data) {
            if (data.getStatus().equals("0000")){
                reviewAdapter.setList(data.getResult());

                reviewAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class FindMoviesDetail implements DataCall<Result<MovieDetailsBean>> {

        @Override
        public void success(Result<MovieDetailsBean> data) {
            if (data.getStatus().equals("0000")) {
                //UIUtils.showToastSafe(new Gson().toJson(data.getResult()));
                result = data.getResult();

                moviesShowName.setText(result.getName());
                moviesShowSimple.setImageURI(result.getImageUrl());
                getShow();
                if (result.getFollowMovie() == 1) {
                    moviesShowLike.setChecked(true);
                }else {
                    moviesShowLike.setChecked(false);
                }

            }
        }

        @Override
        public void fail(ApiException e) {

        }

    }
}
