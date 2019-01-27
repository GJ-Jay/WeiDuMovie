package com.gj.weidumovie.fragment.mylike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:我的关注 影院页面
 * Author:GJ<br>
 * Date:2019/1/26 18:58
 */
public class Fragment_Mylike_Cinema extends WDFragment {
    @BindView(R.id.mylike_xrlv_cinema)
    XRecyclerView mylikeXrlvCinema;
    Unbinder unbinder;

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
}
