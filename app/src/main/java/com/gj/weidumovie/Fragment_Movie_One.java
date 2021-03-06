package com.gj.weidumovie;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.gj.weidumovie.util.CacheManager;
import com.gj.weidumovie.util.FileUtils;
import com.gj.weidumovie.util.UIUtils;
import com.gj.weidumovie.view.MySearchLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.show_address)
    TextView showAddress;
    @BindView(R.id.one_sou)
    EditText oneSou;

    @BindView(R.id.one_r)
    ImageView oneR;
    @BindView(R.id.one_z)
    ImageView oneZ;
    @BindView(R.id.one_j)
    ImageView oneJ;
    @BindView(R.id.one_my_search)
    MySearchLayout oneMySearch;
    private MovieFlowAdapter movieFlowAdapter;
    private PopularAdapter popularAdapter;
    private BeingAdapter beingAdapter;
    private SoonAdapter soonAdapter;
    private LocationClient mLocationClient;
    private MyLocationListener myListener;
    private HotMoviePresenter popularMoviePresenter;
    private ReleaseMoviePresenter beingMoviePresenter;
    private ComingSoonMoviePresenter soonMoviePresenter;
    private CacheManager cacheManager;
    //private File files;

    @Override
    public String getPageName() {
        return "影片页面";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_one;
    }

    @Override
    protected void initView() {
        /*File file = new File(movieFile);
        if (!file.exists()){
            file.mkdir();
        }else {
            files = new File(movieFile + File.separator + "movie.text");
        }*/
        cacheManager = new CacheManager();
        myListener = new MyLocationListener();


        final EditText editText= oneMySearch.findViewById(R.id.one_sou);
       TextView textView= oneMySearch.findViewById(R.id.one_sou_ok);
       textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String s = editText.getText().toString();
               Intent intent = new Intent(getContext(),ShowActivity.class);
               intent.putExtra("sou",s);
               startActivity(intent);
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
        popularMoviePresenter.unBind();
        beingMoviePresenter.unBind();
        soonMoviePresenter.unBind();
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
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
        movieFlowAdapter.setMovieFlowAdapter(new MovieFlowAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                Intent intent = new Intent(getContext(),MovieDetailsShow.class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
        popularMoviePresenter = new HotMoviePresenter(new PopularCall());
        beingMoviePresenter = new ReleaseMoviePresenter(new BeingCall());
        soonMoviePresenter = new ComingSoonMoviePresenter(new SoonCall());


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
                Intent intent = new Intent(getContext(),MovieDetailsShow.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        beingAdapter.setClickListener(new BeingAdapter.ClickListener() {
            @Override
            public void click(int id) {
                Intent intent = new Intent(getContext(),MovieDetailsShow.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        soonAdapter.setClickListener(new SoonAdapter.ClickListener() {
            @Override
            public void click(int id) {
                Intent intent = new Intent(getContext(),MovieDetailsShow.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        initData();

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

    @OnClick({ R.id.one_r, R.id.one_z, R.id.one_j})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.one_r:
                    Intent intent = new Intent(getContext(),MovieShowActivity.class);
                    intent.putExtra("select","1");
                    startActivity(intent);
                break;
            case R.id.one_z:
                Intent intentz = new Intent(getContext(),MovieShowActivity.class);
                intentz.putExtra("select","2");
                startActivity(intentz);
                break;
            case R.id.one_j:
                Intent intentj = new Intent(getContext(),MovieShowActivity.class);
                intentj.putExtra("select","3");
                startActivity(intentj);
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            String addr = location.getCity();    //获取详细地址信息
            if(TextUtils.isEmpty(addr)){
                showAddress.setText("定位中...");
                initData();
            }else {
                showAddress.setText(addr);
                mLocationClient.stop();
            }

            //showAddress.setText(addr);
        }
    }

    //热门电影
    class PopularCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();

                    cacheManager.saveDataToFile(getContext(),new Gson().toJson(moiveBeans),"hotMovie");


                movieFlowAdapter.addItem(moiveBeans);

                popularAdapter.addItem(moiveBeans);
                popularAdapter.notifyDataSetChanged();
                movieFlowAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "hotMovie");
            Type type = new TypeToken<List<MoiveBean>>() {}.getType();
            List<MoiveBean>  moiveBeans = new Gson().fromJson(s, type);
            movieFlowAdapter.addItem(moiveBeans);

            popularAdapter.addItem(moiveBeans);
            popularAdapter.notifyDataSetChanged();
            movieFlowAdapter.notifyDataSetChanged();
        }
    }

    //正在上映
    class BeingCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                cacheManager.saveDataToFile(getContext(),new Gson().toJson(moiveBeans),"newMovie");
                soonAdapter.addItem(moiveBeans);
                soonAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "newMovie");
            Type type = new TypeToken<List<MoiveBean>>() {}.getType();
            List<MoiveBean>  moiveBeans = new Gson().fromJson(s, type);
            soonAdapter.addItem(moiveBeans);
            soonAdapter.notifyDataSetChanged();
        }
    }

    //即将上映
    class SoonCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                List<MoiveBean> moiveBeans = (List<MoiveBean>) result.getResult();
                cacheManager.saveDataToFile(getContext(),new Gson().toJson(moiveBeans),"nextMovie");
                beingAdapter.addItem(moiveBeans);
                beingAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            String s = cacheManager.loadDataFromFile(getContext(), "nextMovie");
            Type type = new TypeToken<List<MoiveBean>>() {}.getType();
            List<MoiveBean>  moiveBeans = new Gson().fromJson(s, type);
            beingAdapter.addItem(moiveBeans);
            beingAdapter.notifyDataSetChanged();

        }
    }
}
