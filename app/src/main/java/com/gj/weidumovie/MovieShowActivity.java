package com.gj.weidumovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.gj.weidumovie.adapter.FilmShowAdapter;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.CancelFollowMoviePresenter;
import com.gj.weidumovie.presenter.ComingSoonMoviePresenter;
import com.gj.weidumovie.presenter.FollowMoviePresenter;
import com.gj.weidumovie.presenter.HotMoviePresenter;
import com.gj.weidumovie.presenter.ReleaseMoviePresenter;
import com.gj.weidumovie.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieShowActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.movie_show_back)
    ImageView movieShowBack;
    private boolean hotcheck = true;
    private boolean releasecheck = false;
    private boolean comingSooncheck = false;
    @BindView(R.id.cinemasdv)
    ImageView cinemasdv;
    @BindView(R.id.cimema_text)
    TextView cimemaText;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.hot_show)
    Button hotShow;
    @BindView(R.id.release_show)
    Button releaseShow;
    @BindView(R.id.comingSoon_show)
    Button comingSoonShow;
    @BindView(R.id.movie_show_recycler)
    XRecyclerView movieShowRecycler;
    private HotMoviePresenter hotMoviePresenter;
    private ReleaseMoviePresenter releaseMoviePresenter;
    private ComingSoonMoviePresenter comingSoonMoviePresenter;
    private String sessionId;
    private int userId;
    private FilmShowAdapter filmShowAdapter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private FollowMoviePresenter followMoviePresenter;
    private CancelFollowMoviePresenter cancelFollowMoviePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_show;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        String select = intent.getStringExtra("select");
        hotMoviePresenter = new HotMoviePresenter(new ComingSoon());
        releaseMoviePresenter = new ReleaseMoviePresenter(new ComingSoon());
        comingSoonMoviePresenter = new ComingSoonMoviePresenter(new ComingSoon());
        followMoviePresenter = new FollowMoviePresenter(new FollowMovieCall());
        cancelFollowMoviePresenter = new CancelFollowMoviePresenter(new FollowMovieCall());
        movieShowRecycler.setLoadingListener(this);
        movieShowRecycler.setLoadingMoreEnabled(true);
        movieShowRecycler.setPullRefreshEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        filmShowAdapter = new FilmShowAdapter(this);
        movieShowRecycler.setLayoutManager(manager);
        movieShowRecycler.setAdapter(filmShowAdapter);

        if (select.equals("1")) {
            hotShow.setBackgroundResource(R.drawable.btn_gradient);
            releaseShow.setBackgroundResource(R.drawable.btn_false);
            comingSoonShow.setBackgroundResource(R.drawable.btn_false);
            hotShow.setTextColor(Color.WHITE);
            releaseShow.setTextColor(Color.BLACK);
            comingSoonShow.setTextColor(Color.BLACK);
            filmShowAdapter.remove();
            hotMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else if (select.equals("2")) {
            releaseShow.setBackgroundResource(R.drawable.btn_gradient);
            hotShow.setBackgroundResource(R.drawable.btn_false);
            comingSoonShow.setBackgroundResource(R.drawable.btn_false);
            hotShow.setTextColor(Color.BLACK);
            releaseShow.setTextColor(Color.WHITE);
            comingSoonShow.setTextColor(Color.BLACK);
            filmShowAdapter.remove();
            releaseMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else {
            comingSoonShow.setBackgroundResource(R.drawable.btn_gradient);
            hotShow.setBackgroundResource(R.drawable.btn_false);
            releaseShow.setBackgroundResource(R.drawable.btn_false);
            hotShow.setTextColor(Color.BLACK);
            releaseShow.setTextColor(Color.BLACK);
            comingSoonShow.setTextColor(Color.WHITE);
            filmShowAdapter.remove();
            comingSoonMoviePresenter.reqeust(userId, sessionId, false, 5);
        }
        filmShowAdapter.setClickListener(new FilmShowAdapter.ClickListener() {
            @Override
            public void click(int id) {
                Intent intent = new Intent(MovieShowActivity.this, MovieDetailsShow.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void clickOk(int id) {
                followMoviePresenter.reqeust(userId,sessionId,id);

            }

            @Override
            public void clickNo(int id) {
                cancelFollowMoviePresenter.reqeust(userId,sessionId,id);

            }
        });
    }

    @Override
    protected void destoryData() {
        hotMoviePresenter.unBind();
        releaseMoviePresenter.unBind();
        comingSoonMoviePresenter.unBind();
        followMoviePresenter.unBind();
        cancelFollowMoviePresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.hot_show, R.id.release_show, R.id.comingSoon_show,R.id.movie_show_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_show:
                hotcheck = true;
                if (hotcheck) {
                    hotShow.setBackgroundResource(R.drawable.btn_gradient);
                    releasecheck = false;
                    comingSooncheck = false;
                    releaseShow.setBackgroundResource(R.drawable.btn_false);
                    comingSoonShow.setBackgroundResource(R.drawable.btn_false);
                    hotShow.setTextColor(Color.WHITE);
                    releaseShow.setTextColor(Color.BLACK);
                    comingSoonShow.setTextColor(Color.BLACK);
                    filmShowAdapter.remove();
                    hotMoviePresenter.reqeust(userId, sessionId, false, 5);
                }
                break;
            case R.id.release_show:
                releasecheck = true;
                if (releasecheck) {
                    releaseShow.setBackgroundResource(R.drawable.btn_gradient);
                    hotcheck = false;
                    comingSooncheck = false;
                    hotShow.setBackgroundResource(R.drawable.btn_false);
                    comingSoonShow.setBackgroundResource(R.drawable.btn_false);
                    hotShow.setTextColor(Color.BLACK);
                    releaseShow.setTextColor(Color.WHITE);
                    comingSoonShow.setTextColor(Color.BLACK);
                    filmShowAdapter.remove();
                    releaseMoviePresenter.reqeust(userId, sessionId, false, 5);
                }
                break;
            case R.id.comingSoon_show:
                comingSooncheck = true;
                if (comingSooncheck) {
                    comingSoonShow.setBackgroundResource(R.drawable.btn_gradient);
                    releasecheck = false;
                    hotcheck = false;
                    hotShow.setBackgroundResource(R.drawable.btn_false);
                    releaseShow.setBackgroundResource(R.drawable.btn_false);
                    hotShow.setTextColor(Color.BLACK);
                    releaseShow.setTextColor(Color.BLACK);
                    comingSoonShow.setTextColor(Color.WHITE);
                    filmShowAdapter.remove();
                    comingSoonMoviePresenter.reqeust(userId, sessionId, false, 5);
                }
                break;
            case R.id.movie_show_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        filmShowAdapter.remove();
        if (hotcheck) {
            hotMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else if (releasecheck) {
            releaseMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else {
            comingSoonMoviePresenter.reqeust(userId, sessionId, false, 5);
        }

    }

    @Override
    public void onLoadMore() {
        if (hotcheck) {
            hotMoviePresenter.reqeust(userId, sessionId, true, 5);
        } else if (releasecheck) {
            releaseMoviePresenter.reqeust(userId, sessionId, true, 5);
        } else {
            comingSoonMoviePresenter.reqeust(userId, sessionId, true, 5);
        }

    }

    /*private class Hot implements DataCall<Result> {

        @Override
        public void success(Result data) {
            List<MoiveBean> result = (List<MoiveBean>) data.getResult();
            filmShowAdapter.addItem(result);
            filmShowAdapter.notifyDataSetChanged();
            movieShowRecycler.loadMoreComplete();
            movieShowRecycler.refreshComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class Release implements DataCall<Result> {

        @Override
        public void success(Result data) {
            List<MoiveBean> result = (List<MoiveBean>) data.getResult();
            filmShowAdapter.addItem(result);
            filmShowAdapter.notifyDataSetChanged();
            movieShowRecycler.loadMoreComplete();
            movieShowRecycler.refreshComplete();
        }

        @Override
        public void fail(ApiException e) {

        }
    }*/

    private class ComingSoon implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                List<MoiveBean> result = (List<MoiveBean>) data.getResult();
                filmShowAdapter.addItem(result);
                filmShowAdapter.notifyDataSetChanged();
                movieShowRecycler.loadMoreComplete();
                movieShowRecycler.refreshComplete();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMoviePresenter.unBind();
        hotMoviePresenter.unBind();
        comingSoonMoviePresenter.unBind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        filmShowAdapter.remove();
        SharedPreferences sharedPreferences = getSharedPreferences("Config", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);
        sessionId = sharedPreferences.getString("sessionId", "");
        if (hotcheck) {
            hotMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else if (releasecheck) {
            releaseMoviePresenter.reqeust(userId, sessionId, false, 5);
        } else {
            comingSoonMoviePresenter.reqeust(userId, sessionId, false, 5);
        }
    }
    public void initData(){
        //百度定位
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }
    //定位
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//            double latitude = location.getLatitude();    //获取纬度信息
//            double longitude = location.getLongitude();    //获取经度信息
            if (!location.equals("")) {
                mLocationClient.stop();
            }
//            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if(TextUtils.isEmpty(addr)){
                cimemaText.setText("定位中...");
                initData();
            }else {
                cimemaText.setText(addr);
                mLocationClient.stop();
            }
            //cimemaText.setText(addr);
        }
    }
    private class FollowMovieCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if(data.getStatus().equals("0000")){
                UIUtils.showToastSafe(data.getMessage());
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getMessage());
        }
    }
}
