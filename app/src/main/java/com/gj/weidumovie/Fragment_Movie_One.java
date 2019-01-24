package com.gj.weidumovie;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/23 15:10
 */
public class Fragment_Movie_One extends WDFragment {

    @BindView(R.id.one_image_address)
    TextView oneImageAddress;
    @BindView(R.id.one_banner_flow)
    RecyclerCoverFlow oneBannerFlow;
    @BindView(R.id.home_hot)
    TextView homeHot;
    @BindView(R.id.home_hot_rlv)
    RecyclerView homeHotRlv;
    @BindView(R.id.home_being)
    TextView homeBeing;
    @BindView(R.id.home_being_rlv)
    RecyclerView homeBeingRlv;
    @BindView(R.id.home_about_show)
    TextView homeAboutShow;
    @BindView(R.id.home_about_show_rlv)
    RecyclerView homeAboutShowRlv;
    Unbinder unbinder;

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
}
