package com.gj.weidumovie.fragment.buyticket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.MyLikeCinemaAdapter;
import com.gj.weidumovie.adapter.MyLikeWaitAdapter;
import com.gj.weidumovie.bean.BuyTicket;
import com.gj.weidumovie.bean.LikeCinema;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.BuyTicketPresenter;
import com.gj.weidumovie.presenter.MyLikeCinemaPresenter;
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
public class Fragment_BuyTicket_Wait extends WDFragment implements XRecyclerView.LoadingListener {

    Unbinder unbinder;
    @BindView(R.id.buyticket_xrlv_wait)
    XRecyclerView buyticketXrlvWait;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;
    private MyLikeWaitAdapter myLikeWaitAdapter;
    private BuyTicketPresenter buyTicketPresenter;
    /*private BuyTicket buyTicket;*/

    @Override
    public String getPageName() {
        return "待付款";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buyticket_wait;
    }

    @Override
    protected void initView() {
        sp = getActivity().getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");

        buyTicketPresenter = new BuyTicketPresenter(new myTicketWaitCall());
        myLikeWaitAdapter = new MyLikeWaitAdapter(getActivity()); //getActivity()
        buyticketXrlvWait.setLoadingListener(this);//加载更多 允许加载更多和刷新
        buyticketXrlvWait.setLoadingMoreEnabled(true);
        buyticketXrlvWait.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        buyticketXrlvWait.setLayoutManager(linearLayoutManager);//线性布局
        buyticketXrlvWait.setAdapter(myLikeWaitAdapter);

        /*int status1= buyTicket.getStatus();*/
        buyTicketPresenter.reqeust(userId, sessionId, false, 5, 1);
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
        buyticketXrlvWait.refreshComplete();
        myLikeWaitAdapter.remove();
        myLikeWaitAdapter.notifyDataSetChanged();
        buyTicketPresenter.reqeust(userId, sessionId, false, 5,1);
    }

    @Override
    public void onLoadMore() {
        buyticketXrlvWait.loadMoreComplete();
        buyTicketPresenter.reqeust(userId, sessionId, true, 5,1);
    }

    private class myTicketWaitCall implements DataCall<Result<List<BuyTicket>>> {

        private BuyTicket buyTicket;

        @Override
        public void success(Result<List<BuyTicket>> data) {
            if (data.getStatus().equals("0000")) {
                List<BuyTicket> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    buyTicket = result.get(i);
                    myLikeWaitAdapter.addList(buyTicket);//添加到适配器中
                    myLikeWaitAdapter.notifyDataSetChanged();
                }
                /*status = buyTicket.getStatus();*/
                /*if(Fragment_BuyTicket_Wait.this.status ==1){*/
                /*}*/
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("关注的影院" + e.getMessage());
        }
    }
}
