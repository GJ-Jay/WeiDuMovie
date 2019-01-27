package com.gj.weidumovie.fragment.mylike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.MyLikeMovieAdapter;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.MyLikeMoviePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:我的关注 影片页面
 * Author:GJ<br>
 * Date:2019/1/26 18:58
 */
public class Fragment_Mylike_Movie extends WDFragment {
    @BindView(R.id.mylike_xrlv_movie)
    XRecyclerView mylikeXrlvMovie;
    Unbinder unbinder;

    @Override
    public String getPageName() {
        return "我的关注 影片";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mylike_movie;
    }

    @Override
    protected void initView() {
        MyLikeMoviePresenter myLikeMoviePresenter = new MyLikeMoviePresenter(new myLikeMovieCall());
        MyLikeMovieAdapter myLikeMovieAdapter = new MyLikeMovieAdapter();
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

    private class myLikeMovieCall implements DataCall<Result<List<LikeMovie>>> {

        private LikeMovie likeMovie;

        @Override
        public void success(Result<List<LikeMovie>> data) {
            if(data.getStatus().equals("0000")){
                List<LikeMovie> result = data.getResult();
                for (int i = 0; i <result.size() ; i++) {
                    likeMovie = result.get(i);
                    //添加到适配器中
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
