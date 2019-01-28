package com.gj.weidumovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.adapter.MovieFlowAdapter;
import com.gj.weidumovie.adapter.MovieScheduleListAdapter;
import com.gj.weidumovie.bean.MoiveBean;
import com.gj.weidumovie.bean.MovieScheduleBean;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindMovieListByCinemaIdPresenter;
import com.gj.weidumovie.presenter.FindMovieScheduleListPresenter;
import com.gj.weidumovie.util.SpaceItemDecoration;

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
        movieCinemaName.setText(name);
        movieCinemaAddress.setText(address);
        movieCinemaSimple.setImageURI(image);
        findMovieScheduleListPresenter = new FindMovieScheduleListPresenter(new FindMovieScheduleList());

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
    }

    @Override
    protected void destoryData() {
        findMovieListByCinemaIdPresenter.unBind();
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
}
