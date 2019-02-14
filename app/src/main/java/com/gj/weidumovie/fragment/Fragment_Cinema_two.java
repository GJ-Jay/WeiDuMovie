package com.gj.weidumovie.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.gj.weidumovie.LoginActivity;
import com.gj.weidumovie.MovieCinemaActivity;
import com.gj.weidumovie.adapter.CinemaAdapter;
import com.gj.weidumovie.bean.CinemaBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.CancelFollowCinemaPresenter;
import com.gj.weidumovie.presenter.FindNearbyCinemasPresenter;
import com.gj.weidumovie.presenter.FindRecommendCinemasPresenter;
import com.gj.weidumovie.presenter.FollowCinemaPresenter;
import com.gj.weidumovie.util.UIUtils;
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
    private FollowCinemaPresenter followCinemaPresenter;
    private int userId;
    private String sessionId;
    private CancelFollowCinemaPresenter cancelFollowCinemaPresenter;

    @Override
    public String getPageName() {
        return "影院页面";
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
        followCinemaPresenter = new FollowCinemaPresenter(new followCinemaCall());
        cancelFollowCinemaPresenter = new CancelFollowCinemaPresenter(new cancelFollowCinema());

        //默认推荐影院

        cinemaPresenter.reqeust(userId, sessionId, false, 10);
        recommend.setBackgroundResource(R.drawable.btn_gradient);
        recommend.setTextColor(Color.WHITE);
        nearby.setBackgroundResource(R.drawable.myborder);
        nearby.setTextColor(Color.BLACK);

        initData();


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
        followCinemaPresenter.unBind();
        cancelFollowCinemaPresenter.unBind();
        nearbyMoivePresenter.unBind();
        cinemaPresenter.unBind();
    }

    @OnClick({R.id.recommend, R.id.nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend:
                recommend.setBackgroundResource(R.drawable.btn_gradient);
                recommend.setTextColor(Color.WHITE);
                nearby.setBackgroundResource(R.drawable.myborder);
                nearby.setTextColor(Color.BLACK);
                //cinemaAdapter.remove();
               // cinemaAdapter = new CinemaAdapter(getActivity());
                //cinemarecycleview.setAdapter(cinemaAdapter);
                cinemaPresenter.reqeust(userId, sessionId, false, 10);
                break;
            case R.id.nearby:
                nearby.setBackgroundResource(R.drawable.btn_gradient);
                nearby.setTextColor(Color.WHITE);
                recommend.setBackgroundResource(R.drawable.myborder);
                recommend.setTextColor(Color.BLACK);
                //cinemaAdapter.remove();
                //cinemaAdapter = new CinemaAdapter(getActivity());
                //cinemarecycleview.setAdapter(cinemaAdapter);
                nearbyMoivePresenter.reqeust(userId, sessionId, "116.30551391385724", "40.04571807462411", false, 10);
                break;
        }
    }

    class CinemaCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<CinemaBean> cinemaBeans = (List<CinemaBean>) result.getResult();
                cinemaAdapter = new CinemaAdapter(getActivity());
                cinemaAdapter.addItem(cinemaBeans);
                cinemarecycleview.setAdapter(cinemaAdapter);
                //cinemaAdapter.notifyDataSetChanged();
                cinemaAdapter.setClickListener(new CinemaAdapter.ClickListener() {
                    @Override
                    public void clickOk(int id) {//点击关注

                        followCinemaPresenter.reqeust(userId,sessionId,id);
                    }

                    @Override
                    public void clickNo(int id) {
                        cancelFollowCinemaPresenter.reqeust(userId,sessionId,id);
                    }

                    @Override
                    public void itemClick(int id,String name,String address,String image) {
                        Intent intent =  new Intent(getContext(), MovieCinemaActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
                        intent.putExtra("address",address);
                        intent.putExtra("image",image);

                        startActivity(intent);
                    }
                });
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
            if(TextUtils.isEmpty(locationDescribe)&&TextUtils.isEmpty(addr)){
                cimemaText.setText("北京市");
            }else {
                cimemaText.setText(locationDescribe + addr);
            }
           // cimemaText.setText(locationDescribe + addr);
            //cimemaText.setText(addr);

        }
    }
    private class followCinemaCall implements DataCall<Result> {
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

    private class cancelFollowCinema implements DataCall<Result> {
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
    @Override
    public void onResume() {
        super.onResume();
        initData();
        SharedPreferences sp = getActivity().getSharedPreferences("Config", Context.MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        cinemaPresenter.reqeust(userId, sessionId, false, 10);
        nearbyMoivePresenter.reqeust(userId, sessionId, "116.30551391385724", "40.04571807462411", false, 10);
    }
}
