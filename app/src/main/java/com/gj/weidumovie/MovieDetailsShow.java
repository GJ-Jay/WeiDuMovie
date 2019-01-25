package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.gj.weidumovie.bean.MovieDetailsBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindMoviesDetailPresenter;

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
    ImageView moviesShowBuy;
    @BindView(R.id.movies_show_like)
    CheckBox moviesShowLike;
    private FindMoviesDetailPresenter findMoviesDetailPresenter;
    private MovieDetailsBean result;
    private PopupWindow detailWindow;
    private View parent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_details_show;

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        Log.d("abc", "initView: " + id);
        SharedPreferences sharedPreferences = getSharedPreferences("Config", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        String sessionId = sharedPreferences.getString("sessionId", "");
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
    }

    private void getShowDetail(View contentView) {
        TextView tv_pop_title = contentView.findViewById(R.id.tv_pop_title);

        tv_pop_title.setText(result.getName());

    }


    @Override
    protected void destoryData() {
        findMoviesDetailPresenter.unBind();
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

                break;
            case R.id.movies_show_jz:

                break;
            case R.id.movies_show_yp:

                break;
            case R.id.movies_show_back:
                finish();
                break;
            case R.id.movies_show_buy:

                break;
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
