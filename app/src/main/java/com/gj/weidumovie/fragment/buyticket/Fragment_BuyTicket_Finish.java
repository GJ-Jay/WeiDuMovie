package com.gj.weidumovie.fragment.buyticket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.BuyTicketFinishAdapter;
import com.gj.weidumovie.adapter.MyLikeMovieAdapter;
import com.gj.weidumovie.bean.BuyTicket;
import com.gj.weidumovie.bean.LikeMovie;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.BuyTicketPresenter;
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
public class Fragment_BuyTicket_Finish extends WDFragment implements XRecyclerView.LoadingListener {

    Unbinder unbinder;
    @BindView(R.id.buyticket_xrlv_finish)
    XRecyclerView buyticketXrlvCinema;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;
    private BuyTicketPresenter buyTicketPresenter;
    private BuyTicketFinishAdapter buyTicketFinishAdapter;
    /*private BuyTicket buyTicket;
    private int status;*/

    @Override
    public String getPageName() {
        return "已完成";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_butticket_finish;
    }

    @Override
    protected void initView() {
        sp = getActivity().getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");

        buyTicketPresenter = new BuyTicketPresenter(new buyTicketCall());
        buyTicketFinishAdapter = new BuyTicketFinishAdapter(getContext());
        buyticketXrlvCinema.setLoadingListener(this);//加载更多 允许加载更多和刷新
        buyticketXrlvCinema.setLoadingMoreEnabled(true);
        buyticketXrlvCinema.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        buyticketXrlvCinema.setLayoutManager(linearLayoutManager);//线性布局
        buyticketXrlvCinema.setAdapter(buyTicketFinishAdapter);
        /*int status1= buyTicket.getStatus();*/
        buyTicketPresenter.reqeust(userId, sessionId, false, 5, 2);
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
        buyticketXrlvCinema.refreshComplete();
        buyTicketFinishAdapter.remove();
        buyTicketFinishAdapter.notifyDataSetChanged();
        buyTicketPresenter.reqeust(userId, sessionId, false, 5,2);
    }

    @Override
    public void onLoadMore() {
        buyticketXrlvCinema.loadMoreComplete();
        buyTicketPresenter.reqeust(userId, sessionId, true, 5,2);
    }

    private class buyTicketCall implements DataCall<Result<List<BuyTicket>>> {

        private BuyTicket buyTicket;

        @Override
        public void success(Result<List<BuyTicket>> data) {
            if (data.getStatus().equals("0000")) {
                List<BuyTicket> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    buyTicket = result.get(i);
                    buyTicketFinishAdapter.addList(buyTicket);//添加到适配器中
                    buyTicketFinishAdapter.notifyDataSetChanged();
                }
                /*status = buyTicket.getStatus();
                if(status==2){*/
                /*}
                buyTicketPresenter.reqeust(userId, sessionId, false, 5,2);//已开始要先加载*/
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("已完成" + e.getMessage());
        }
    }
}
