package com.gj.weidumovie.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.CinemaAdapter;
import com.gj.weidumovie.bean.CinemaBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindNearbyCinemasPresenter;
import com.gj.weidumovie.presenter.FindRecommendCinemasPresenter;
import com.gj.weidumovie.view.MySearchLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/23 15:10
 */
public class Fragment_Cinema_two extends WDFragment {
    @BindView(R.id.cinemasdv)
    SimpleDraweeView cinemasdv;
    @BindView(R.id.cimema_text)
    TextView cimemaText;
    @BindView(R.id.cinema_relative)
    RelativeLayout cinemaRelative;
    @BindView(R.id.recommend)
    Button recommend;
    @BindView(R.id.nearby)
    Button nearby;
    @BindView(R.id.cinemarecycleview)
    RecyclerView cinemarecycleview;
    Unbinder unbinder;
    @BindView(R.id.two_my_search)
    MySearchLayout twoMySearch;
    private CinemaAdapter cinemaAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FindRecommendCinemasPresenter cinemaPresenter;
    private FindNearbyCinemasPresenter nearbyMoivePresenter;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cinema_two;
    }

    @Override
    protected void initView() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        cinemarecycleview.setLayoutManager(linearLayoutManager);
        cinemaPresenter = new FindRecommendCinemasPresenter(new CinemaCall());
        nearbyMoivePresenter = new FindNearbyCinemasPresenter(new CinemaCall());

        //默认推荐影院
        cinemaAdapter = new CinemaAdapter(getActivity());
        cinemarecycleview.setAdapter(cinemaAdapter);
        cinemaPresenter.reqeust(0, "", false, 10);
        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recommend.setTextColor(Color.WHITE);
        nearby.setBackgroundResource(R.drawable.myborder);
        nearby.setTextColor(Color.BLACK);

        initData();

        cinemaAdapter.setClickListener(new CinemaAdapter.ClickListener() {
            @Override
            public void clickOk(int id) {

            }

            @Override
            public void clickNo(int id) {

            }
        });
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

    @OnClick({R.id.recommend, R.id.nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend:
                recommend.setBackgroundResource(R.drawable.btn_gradient);
                recommend.setTextColor(Color.WHITE);
                nearby.setBackgroundResource(R.drawable.myborder);
                nearby.setTextColor(Color.BLACK);
                cinemaAdapter.remove();
                cinemaAdapter = new CinemaAdapter(getActivity());
                cinemarecycleview.setAdapter(cinemaAdapter);
                cinemaPresenter.reqeust(0, "", false, 10);
                break;
            case R.id.nearby:
                nearby.setBackgroundResource(R.drawable.btn_gradient);
                nearby.setTextColor(Color.WHITE);
                recommend.setBackgroundResource(R.drawable.myborder);
                recommend.setTextColor(Color.BLACK);
                cinemaAdapter.remove();
                cinemaAdapter = new CinemaAdapter(getActivity());
                cinemarecycleview.setAdapter(cinemaAdapter);
                nearbyMoivePresenter.reqeust(0, "", "116.30551391385724", "40.04571807462411", false, 10);
                break;
        }
    }

    class CinemaCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<CinemaBean> cinemaBeans = (List<CinemaBean>) result.getResult();
                cinemaAdapter.addItem(cinemaBeans);
                cinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private void initData() {
        mLocationClient = new LocationClient(getActivity());
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
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();


    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getAddrStr();    //获取详细地址信息
            cimemaText.setText(locationDescribe + addr);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
