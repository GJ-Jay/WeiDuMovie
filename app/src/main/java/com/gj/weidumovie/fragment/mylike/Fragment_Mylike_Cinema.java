package com.gj.weidumovie.fragment.mylike;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.MyLikeCinemaAdapter;
import com.gj.weidumovie.adapter.MyLikeMovieAdapter;
import com.gj.weidumovie.bean.LikeCinema;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.MyLikeCinemaPresenter;
import com.gj.weidumovie.presenter.MyLikeMoviePresenter;
import com.gj.weidumovie.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Description:我的关注 影院页面
 * Author:GJ<br>
 * Date:2019/1/26 18:58
 */
public class Fragment_Mylike_Cinema extends WDFragment implements XRecyclerView.LoadingListener {
    @BindView(R.id.mylike_xrlv_cinema)
    XRecyclerView mylikeXrlvCinema;
    Unbinder unbinder;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;
    private MyLikeCinemaAdapter myLikeCinemaAdapter;
    private MyLikeCinemaPresenter myLikeCinemaPresenter;

    @Override
    public String getPageName() {
        return "我的关注 影院";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mylike_cinema;
    }

    @Override
    protected void initView() {
        sp = getActivity().getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");

        myLikeCinemaPresenter = new MyLikeCinemaPresenter(new myLikeCinemaCall());
        myLikeCinemaAdapter = new MyLikeCinemaAdapter(getActivity());
        mylikeXrlvCinema.setLoadingListener(this);//加载更多 允许加载更多和刷新
        mylikeXrlvCinema.setLoadingMoreEnabled(true);
        mylikeXrlvCinema.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mylikeXrlvCinema.setLayoutManager(linearLayoutManager);//线性布局
        mylikeXrlvCinema.setAdapter(myLikeCinemaAdapter);

        myLikeCinemaPresenter.reqeust(userId,sessionId,false,5);
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
        mylikeXrlvCinema.refreshComplete();
        myLikeCinemaAdapter.remove();
        myLikeCinemaAdapter.notifyDataSetChanged();
        myLikeCinemaPresenter.reqeust(userId,sessionId,false,10);
    }

    @Override
    public void onLoadMore() {
        mylikeXrlvCinema.loadMoreComplete();
        myLikeCinemaPresenter.reqeust(userId,sessionId,true,10);
    }

    private class myLikeCinemaCall implements DataCall<Result<List<LikeCinema>>> {
        @Override
        public void success(Result<List<LikeCinema>> data) {
            if(data.getStatus().equals("0000")){
                List<LikeCinema> result = data.getResult();
                for (int i = 0; i <result.size() ; i++) {
                    LikeCinema likeCinema = result.get(i);
                    myLikeCinemaAdapter.addList(likeCinema);//添加到适配器中
                    myLikeCinemaAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("关注的影院"+e.getMessage());
        }
    }
}
