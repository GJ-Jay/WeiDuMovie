package com.gj.weidumovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.MovieScheduleListAdapter;
import com.gj.weidumovie.bean.MovieDetailsBean;
import com.gj.weidumovie.bean.MovieScheduleBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindMovieScheduleListPresenter;
import com.gj.weidumovie.util.SpaceItemDecoration;
import com.gj.weidumovie.util.UIUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date:2019/1/26 20:07
 * author:陈国星(陈国星)
 * function:
 */
public class MovieBuyActivity extends WDActivity {
    @BindView(R.id.movie_buy_cinema_name)
    TextView movieBuyCinemaName;
    @BindView(R.id.movie_buy_cinema_address)
    TextView movieBuyCinemaAddress;
    @BindView(R.id.movie_buy_cinema_share)
    ImageView movieBuyCinemaShare;
    @BindView(R.id.buy_film_show_simple)
    SimpleDraweeView buyFilmShowSimple;
    @BindView(R.id.buy_film_show_name)
    TextView buyFilmShowName;
    @BindView(R.id.buy_film_show_movieTypes)
    TextView buyFilmShowMovieTypes;
    @BindView(R.id.buy_film_show_director)
    TextView buyFilmShowDirector;
    @BindView(R.id.buy_film_show_duration)
    TextView buyFilmShowDuration;
    @BindView(R.id.buy_film_show_placeOrigin)
    TextView buyFilmShowPlaceOrigin;
    @BindView(R.id.movie_buy_recycler)
    RecyclerView movieBuyRecycler;
    @BindView(R.id.movie_buy_back)
    ImageView movieBuyBack;
    private FindMovieScheduleListPresenter findMovieScheduleListPresenter;
    private MovieScheduleListAdapter movieScheduleListAdapter;
    private MovieDetailsBean movie;
    private String name;
    private String address;
    private List<MovieScheduleBean> result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_buy;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        movie = (MovieDetailsBean) intent.getSerializableExtra("movie");
        int id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        movieBuyCinemaName.setText(name);
        movieBuyCinemaAddress.setText(address);
        buyFilmShowSimple.setImageURI(movie.getImageUrl());
        buyFilmShowName.setText(movie.getName());
        buyFilmShowMovieTypes.setText("类型："+ movie.getMovieTypes());
        buyFilmShowDirector.setText("导演："+ movie.getDirector());
        buyFilmShowDuration.setText("时长："+ movie.getDuration());
        buyFilmShowPlaceOrigin.setText("产地："+ movie.getPlaceOrigin());
        findMovieScheduleListPresenter = new FindMovieScheduleListPresenter(new FindMovieScheduleList());
        findMovieScheduleListPresenter.reqeust(id, movie.getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieBuyRecycler.setLayoutManager(linearLayoutManager);
        movieBuyRecycler.addItemDecoration(new SpaceItemDecoration(10));
        movieScheduleListAdapter = new MovieScheduleListAdapter(this);
        movieBuyRecycler.setAdapter(movieScheduleListAdapter);
        movieScheduleListAdapter.setMovieFlowAdapter(new MovieScheduleListAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                Intent intent1 = new Intent(MovieBuyActivity.this,MovieBuyShowActivity.class);
                intent1.putExtra("movieName", movie.getName());
                MovieScheduleBean movieScheduleBean = result.get(position);
                intent1.putExtra("movieScheduleBean",movieScheduleBean);
                intent1.putExtra("name", name);
                intent1.putExtra("address", address);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void destoryData() {
        findMovieScheduleListPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.movie_buy_cinema_share, R.id.movie_buy_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie_buy_cinema_share:

                break;
            case R.id.movie_buy_back:
                finish();
                break;
        }
    }
    class FindMovieScheduleList implements DataCall<Result<List<MovieScheduleBean>>>{

        @Override
        public void success(Result<List<MovieScheduleBean>> data) {
            if (data.getStatus().equals("0000")){
               // UIUtils.showToastSafe(new Gson().toJson(data.getResult()));
                result = data.getResult();

                movieScheduleListAdapter.addItem(result);
                movieScheduleListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
