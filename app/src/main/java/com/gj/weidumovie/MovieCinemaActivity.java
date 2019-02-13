package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.CinemaComListAdapter;
import com.gj.weidumovie.adapter.MovieFlowAdapter;
import com.gj.weidumovie.adapter.MovieScheduleListAdapter;
import com.gj.weidumovie.bean.CineamComListBean;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.MovieScheduleBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.CinemaCommentGreatPresenter;
import com.gj.weidumovie.presenter.CinemaCommentPresenter;
import com.gj.weidumovie.presenter.FindAllCinemaCommentPresenter;
import com.gj.weidumovie.presenter.FindMovieListByCinemaIdPresenter;
import com.gj.weidumovie.presenter.FindMovieScheduleListPresenter;
import com.gj.weidumovie.util.SpaceItemDecoration;
import com.gj.weidumovie.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * date:2019/1/28 11:42
 * author:陈国星(陈国星)
 * function:
 */
public class MovieCinemaActivity extends WDActivity {

    @BindView(R.id.movie_cinema_flow)
    RecyclerCoverFlow movieCinemaFlow;
    @BindView(R.id.movie_cinema_recycle)
    RecyclerView movieCinemaRecycle;
    @BindView(R.id.movie_cinema_group)
    RadioGroup movieCinemaGroup;
    @BindView(R.id.movie_cinema_simple)
    SimpleDraweeView movieCinemaSimple;
    @BindView(R.id.movie_cinema_name)
    TextView movieCinemaName;
    @BindView(R.id.movie_cinema_address)
    TextView movieCinemaAddress;
    @BindView(R.id.movie_cinema_share)
    ImageView movieCinemaShare;
    @BindView(R.id.movie_cinema_back)
    ImageView movieCinemaBack;
    private MovieFlowAdapter movieFlowAdapter;
    private FindMovieListByCinemaIdPresenter findMovieListByCinemaIdPresenter;
    private FindMovieScheduleListPresenter findMovieScheduleListPresenter;
    private List<MoiveBean> moiveBeans;
    private MovieScheduleListAdapter movieScheduleListAdapter;
    private List<MovieScheduleBean> result;
    private MoiveBean moiveBean;
    private int id;
    private PopupWindow window;
    private View parent;
    private CinemaComListAdapter cinemaComListAdapter;
    private FindAllCinemaCommentPresenter findAllCinemaCommentPresenter;
    private int userId;
    private String sessionId;
    private CinemaCommentGreatPresenter cinemaCommentGreatPresenter;
    private CinemaCommentPresenter cinemaCommentPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_cinema;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String image = intent.getStringExtra("image");
        SharedPreferences sp = getSharedPreferences("Config", Context.MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        movieCinemaName.setText(name);
        movieCinemaAddress.setText(address);
        movieCinemaSimple.setImageURI(image);
        findMovieScheduleListPresenter = new FindMovieScheduleListPresenter(new FindMovieScheduleList());
        parent = View.inflate(MovieCinemaActivity.this, R.layout.activity_movie_cinema, null);

        movieFlowAdapter = new MovieFlowAdapter(this);
        movieCinemaFlow.setAdapter(movieFlowAdapter);
        movieCinemaFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                // movieCinemaGroup.check(movieCinemaGroup.getChildAt(position).getId());
                moiveBean = moiveBeans.get(position);
                findMovieScheduleListPresenter.reqeust(id, moiveBean.getId());
                //Toast.makeText(getActivity(), "" + (position + 1) + "/" + movieflow.getLayoutManager().getItemCount(), Toast.LENGTH_SHORT).show();

            }
        });
        movieFlowAdapter.setMovieFlowAdapter(new MovieFlowAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                Intent intent = new Intent(MovieCinemaActivity.this, MovieDetailsShow.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
        findMovieListByCinemaIdPresenter = new FindMovieListByCinemaIdPresenter(new FindMovieListByCinemaId());
        findMovieListByCinemaIdPresenter.reqeust(id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        movieCinemaRecycle.setLayoutManager(linearLayoutManager);
        movieCinemaRecycle.addItemDecoration(new SpaceItemDecoration(10));
        movieScheduleListAdapter = new MovieScheduleListAdapter(this);
        movieCinemaRecycle.setAdapter(movieScheduleListAdapter);

        movieScheduleListAdapter.setMovieFlowAdapter(new MovieScheduleListAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                Intent intent1 = new Intent(MovieCinemaActivity.this, MovieBuyShowActivity.class);
                intent1.putExtra("movieName", moiveBean.getName());
                MovieScheduleBean movieScheduleBean = result.get(position);
                intent1.putExtra("movieScheduleBean", movieScheduleBean);
                startActivity(intent1);

            }
        });
        getView();
        movieCinemaSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.showAtLocation(parent, Gravity.BOTTOM,0,0);
            }
        });
    }

    private void getView() {
        View contentView_details = View.inflate(MovieCinemaActivity.this, R.layout.popu_cinema_details, null);
        window = new PopupWindow(contentView_details, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //问题：不能操作窗口内部的控件
        window.setFocusable(true);//获取焦点
        window.setTouchable(true);//
        //点击窗口外部窗口不消失
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        getShowStills(contentView_details);
    }

    private void getShowStills(View contentView_details) {
        findAllCinemaCommentPresenter = new FindAllCinemaCommentPresenter(new FindAllCinemaComment());
        cinemaCommentGreatPresenter = new CinemaCommentGreatPresenter(new CinemaComment());
        cinemaCommentPresenter = new CinemaCommentPresenter(new CinemaComment());
       ImageView popupwindow_detalis_sdvtwo = contentView_details.findViewById(R.id.popupwindow_detalis_sdvtwo);
       TextView tv_dialog_monitor_xq = contentView_details.findViewById(R.id.tv_dialog_monitor_xq);
       TextView tv_dialog_monitor_ping = contentView_details.findViewById(R.id.tv_dialog_monitor_ping);
       ImageView popu_yy_pl = contentView_details.findViewById(R.id.popu_yy_pl);
       final EditText popu_yy_xpl_x = contentView_details.findViewById(R.id.popu_yy_xpl_x);
       TextView popu_yy_xpl_f = contentView_details.findViewById(R.id.popu_yy_xpl_f);
       final LinearLayout popu_yy_xpl = contentView_details.findViewById(R.id.popu_yy_xpl);
       final View view_dialog_monitor_xiang = contentView_details.findViewById(R.id.view_dialog_monitor_xiang);
       final View view_dialog_monitor_ping = contentView_details.findViewById(R.id.view_dialog_monitor_ping);
       final RelativeLayout rec_2 = contentView_details.findViewById(R.id.rec_2);
       final RelativeLayout xiangqing = contentView_details.findViewById(R.id.xiangqing);
       RecyclerView popu_cinema_details_recycle= contentView_details.findViewById(R.id.popu_cinema_details_recycle);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieCinemaActivity.this);
       linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
       popu_cinema_details_recycle.setLayoutManager(linearLayoutManager);
        cinemaComListAdapter = new CinemaComListAdapter();
        popu_cinema_details_recycle.setAdapter(cinemaComListAdapter);

        tv_dialog_monitor_xq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_dialog_monitor_ping.setVisibility(View.GONE);
                rec_2.setVisibility(View.GONE);
                view_dialog_monitor_xiang.setVisibility(View.VISIBLE);
                xiangqing.setVisibility(View.VISIBLE);
            }
        });
        tv_dialog_monitor_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_dialog_monitor_xiang.setVisibility(View.GONE);
                xiangqing.setVisibility(View.GONE);
                view_dialog_monitor_ping.setVisibility(View.VISIBLE);
                rec_2.setVisibility(View.VISIBLE);
                findAllCinemaCommentPresenter.reqeust(userId,sessionId,id,false,10);
            }
        });
       popupwindow_detalis_sdvtwo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               window.dismiss();
           }
       });
       cinemaComListAdapter.setClickListener(new CinemaComListAdapter.ClickListener() {
           @Override
           public void clickOk(int id) {
               cinemaCommentGreatPresenter.reqeust(userId,sessionId,id);
           }
       });
        popu_yy_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popu_yy_xpl.setVisibility(View.VISIBLE);
            }
        });
        popu_yy_xpl_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = popu_yy_xpl_x.getText().toString();
                if (TextUtils.isEmpty(s)){
                    UIUtils.showToastSafe("评论不能为空");
                    return;
                }
                cinemaCommentPresenter.reqeust(userId,sessionId,id,s);
                popu_yy_xpl.setVisibility(View.GONE);
                popu_yy_xpl_x.setText("");
            }
        });
    }

    @Override
    protected void destoryData() {
        findMovieListByCinemaIdPresenter.unBind();
        findAllCinemaCommentPresenter.unBind();
        cinemaCommentGreatPresenter.unBind();
        cinemaCommentPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.movie_cinema_share, R.id.movie_cinema_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie_cinema_share:

                break;
            case R.id.movie_cinema_back:
                finish();
                break;
        }
    }

    //热门电影
    class FindMovieListByCinemaId implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                moiveBeans = (List<MoiveBean>) result.getResult();
                moiveBean = moiveBeans.get(0);
                movieFlowAdapter.addItem(moiveBeans);

                movieFlowAdapter.notifyDataSetChanged();
                findMovieScheduleListPresenter.reqeust(id, moiveBeans.get(0).getId());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    class FindMovieScheduleList implements DataCall<Result<List<MovieScheduleBean>>> {

        @Override
        public void success(Result<List<MovieScheduleBean>> data) {
            if (data.getStatus().equals("0000")) {
                // UIUtils.showToastSafe(new Gson().toJson(data.getResult()));
                result = data.getResult();

                movieScheduleListAdapter.addItem(result);
                movieScheduleListAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class FindAllCinemaComment implements DataCall<Result<List<CineamComListBean>>>{

        @Override
        public void success(Result<List<CineamComListBean>> data) {
            if (data.getStatus().equals("0000")){
                cinemaComListAdapter.RemoveAll();
                cinemaComListAdapter.setList(data.getResult());
                cinemaComListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    class CinemaComment implements DataCall<Result>{

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                UIUtils.showToastSafe(data.getMessage());
                findAllCinemaCommentPresenter.reqeust(userId,sessionId,id,false,10);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
