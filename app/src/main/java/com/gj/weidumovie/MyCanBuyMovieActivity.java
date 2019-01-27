package com.gj.weidumovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.CinemaBuyAdapter;
import com.gj.weidumovie.bean.CinemaBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindCinemasListByMovieIdPresenter;
import com.gj.weidumovie.util.SpaceItemDecoration;
import com.gj.weidumovie.util.UIUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * date:2019/1/26 18:59
 * author:陈国星(陈国星)
 * function:
 */
public class MyCanBuyMovieActivity extends WDActivity {

    @BindView(R.id.my_can_name)
    TextView myCanName;
    @BindView(R.id.my_can_one)
    TextView myCanOne;
    @BindView(R.id.my_can_back)
    ImageView myCanBack;
    @BindView(R.id.my_can_recycler)
    RecyclerView myCanRecycler;
    private FindCinemasListByMovieIdPresenter findCinemasListByMovieIdPresenter;
    private CinemaBuyAdapter cinemaBuyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_can_movie;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("id", 0);
        myCanName.setText(name);
        findCinemasListByMovieIdPresenter = new FindCinemasListByMovieIdPresenter(new FindCinemasListByMovieId());
        findCinemasListByMovieIdPresenter.reqeust(id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myCanRecycler.setLayoutManager(linearLayoutManager);
        cinemaBuyAdapter = new CinemaBuyAdapter(this);
        myCanRecycler.setAdapter(cinemaBuyAdapter);
        cinemaBuyAdapter.setClickListener(new CinemaBuyAdapter.ClickListener() {
            @Override
            public void clickNo(int id) {

            }
        });

    }

    @Override
    protected void destoryData() {
        findCinemasListByMovieIdPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_can_back)
    public void onViewClicked() {
        finish();
    }

    class FindCinemasListByMovieId implements DataCall<Result<List<CinemaBean>>> {

        @Override
        public void success(Result<List<CinemaBean>> data) {
        if (data.getStatus().equals("0000")){
            cinemaBuyAdapter.addItem(data.getResult());
            cinemaBuyAdapter.notifyDataSetChanged();
        }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
