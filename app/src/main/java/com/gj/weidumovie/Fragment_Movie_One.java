package com.gj.weidumovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.BeingAdapter;
import com.gj.weidumovie.adapter.MovieFlowAdapter;
import com.gj.weidumovie.adapter.PopularAdapter;
import com.gj.weidumovie.adapter.SoonAdapter;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.ComingSoonMoviePresenter;
import com.gj.weidumovie.presenter.HotMoviePresenter;
import com.gj.weidumovie.presenter.ReleaseMoviePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;


public class Fragment_Movie_One extends WDFragment {

    @BindView(R.id.moivesdv)
    SimpleDraweeView moivesdv;
    @BindView(R.id.movieflow)
    RecyclerCoverFlow movieflow;
    @BindView(R.id.filmone)
    TextView filmone;
    @BindView(R.id.popular)
    RecyclerView popular;
    @BindView(R.id.filmtwo)
    TextView filmtwo;
    @BindView(R.id.being)
    RecyclerView being;
    @BindView(R.id.filmthree)
    TextView filmthree;
    @BindView(R.id.soon)
    RecyclerView soon;
    Unbinder unbinder;
    @BindView(R.id.home_radio_group)
    RadioGroup homeRadioGroup;
    private MovieFlowAdapter movieFlowAdapter;
    private PopularAdapter popularAdapter;
    private BeingAdapter beingAdapter;
    private SoonAdapter soonAdapter;


    @Override
    public String getPageName() {
        return "影片fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_one;
    }

    @Override
    protected void initView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mList.setFlatFlow(true); //平面滚动
//        mList.setGreyItem(true); //设置灰度渐变
//        mList.setAlphaItem(true); //设置半透渐变

        movieFlowAdapter = new MovieFlowAdapter(getActivity());
        movieflow.setAdapter(movieFlowAdapter);
        movieflow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                homeRadioGroup.check(homeRadioGroup.getChildAt(position).getId());
                //Toast.makeText(getActivity(), "" + (position + 1) + "/" + movieflow.getLayoutManager().getItemCount(), Toast.LENGTH_SHORT).show();
            }
        });

        HotMoviePresenter popularMoviePresenter = new HotMoviePresenter(new PopularCall());
        ReleaseMoviePresenter beingMoviePresenter = new ReleaseMoviePresenter(new BeingCall());
        ComingSoonMoviePresenter soonMoviePresenter = new ComingSoonMoviePresenter(new SoonCall());


        //热门电影列表数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popular.setLayoutManager(linearLayoutManager);
        popularAdapter = new PopularAdapter(getActivity());
        popular.setAdapter(popularAdapter);
        //正在上映
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        being.setLayoutManager(linearLayoutManager2);
        beingAdapter = new BeingAdapter(getActivity());
        being.setAdapter(beingAdapter);
        //即将上映
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        soon.setLayoutManager(linearLayoutManager3);
        soonAdapter = new SoonAdapter(getActivity());
        soon.setAdapter(soonAdapter);

        popularMoviePresenter.reqeust(0, "", false, 10);
        beingMoviePresenter.reqeust(0, "", false, 10);
        soonMoviePresenter.reqeust(0, "", false, 10);

        //自定义接口回调
        popularAdapter.setClickListener(new PopularAdapter.ClickListener() {
            @Override
            public void click(int id) {
                //Intent intent = new Intent(getContext(),);
            }
        });
    }

    //热门电影
    class PopularCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                movieFlowAdapter.addItem(moiveBeans);

                popularAdapter.addItem(moiveBeans);
                popularAdapter.notifyDataSetChanged();
                movieFlowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //正在上映
    class BeingCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                soonAdapter.addItem(moiveBeans);
                soonAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    //即将上映
    class SoonCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                beingAdapter.addItem(moiveBeans);
                beingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
