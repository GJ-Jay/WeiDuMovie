package com.gj.weidumovie.fragment.mylike;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.gj.weidumovie.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Description:我的关注 影片页面
 * Author:GJ<br>
 * Date:2019/1/26 18:58
 */
public class Fragment_Mylike_Movie extends WDFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.mylike_xrlv_movie)
    XRecyclerView mylikeXrlvMovie;
    Unbinder unbinder;
    private MyLikeMovieAdapter myLikeMovieAdapter;
    private MyLikeMoviePresenter myLikeMoviePresenter;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;

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
        sp = getActivity().getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        myLikeMoviePresenter = new MyLikeMoviePresenter(new myLikeMovieCall());
        myLikeMovieAdapter = new MyLikeMovieAdapter();
        mylikeXrlvMovie.setLoadingListener(this);//加载更多 允许加载更多和刷新
        mylikeXrlvMovie.setLoadingMoreEnabled(true);
        mylikeXrlvMovie.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mylikeXrlvMovie.setLayoutManager(linearLayoutManager);//线性布局
        mylikeXrlvMovie.setAdapter(myLikeMovieAdapter);

        myLikeMoviePresenter.reqeust(userId,sessionId,false,5);//已开始要先加载
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
    public void onRefresh() {
        mylikeXrlvMovie.refreshComplete();
        myLikeMovieAdapter.remove();
        myLikeMovieAdapter.notifyDataSetChanged();
        myLikeMoviePresenter.reqeust(userId,sessionId,false,5);
    }

    @Override
    public void onLoadMore() {
        mylikeXrlvMovie.loadMoreComplete();
        myLikeMoviePresenter.reqeust(userId,sessionId,true,5);
    }

    private class myLikeMovieCall implements DataCall<Result<List<LikeMovie>>> {

        private LikeMovie likeMovie;

        @Override
        public void success(Result<List<LikeMovie>> data) {
            if(data.getStatus().equals("0000")){
                List<LikeMovie> result = data.getResult();
                for (int i = 0; i <result.size() ; i++) {
                    likeMovie = result.get(i);
                    myLikeMovieAdapter.addList(likeMovie);//添加到适配器中
                    myLikeMovieAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("关注的影片"+e.getMessage());
        }
    }
}
