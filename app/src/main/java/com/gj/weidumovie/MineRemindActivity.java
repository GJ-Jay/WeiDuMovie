package com.gj.weidumovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.gj.weidumovie.adapter.MineRemindMessageListAdapter;
import com.gj.weidumovie.bean.FindMessageList;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindMessageListPresenter;
import com.gj.weidumovie.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineRemindActivity extends WDActivity implements XRecyclerView.LoadingListener {


    @BindView(R.id.mine_message_back)
    ImageView mineMessageBack;
    @BindView(R.id.mine_message_rlv)
    XRecyclerView mineMessageRlv;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;
    private MineRemindMessageListAdapter mineRemindMessageListAdapter;
    private FindMessageListPresenter findMessageListPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_remind;
    }

    @Override
    protected void initView() {
        sp = getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");

        findMessageListPresenter = new FindMessageListPresenter(new findMessageListCall());
        mineRemindMessageListAdapter = new MineRemindMessageListAdapter();
        mineMessageRlv.setLoadingListener(this);
        mineMessageRlv.setLoadingMoreEnabled(true);//加载更多 允许加载更多和刷新
        mineMessageRlv.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mineMessageRlv.setLayoutManager(linearLayoutManager);//线性布局
        mineMessageRlv.setAdapter(mineRemindMessageListAdapter);//添加适配器

        findMessageListPresenter.reqeust(userId,sessionId,false,5);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.mine_message_back)
    public void setClick(View view){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {
        mineMessageRlv.refreshComplete();
        mineRemindMessageListAdapter.remove();
        mineRemindMessageListAdapter.notifyDataSetChanged();
        findMessageListPresenter.reqeust(userId,sessionId,false,5);
    }

    @Override
    public void onLoadMore() {
        mineMessageRlv.loadMoreComplete();
        findMessageListPresenter.reqeust(userId,sessionId,true,5);
    }

    private class findMessageListCall implements DataCall<Result<List<FindMessageList>>> {
        @Override
        public void success(Result<List<FindMessageList>> data) {
            if (data.getStatus().equals("0000")) {
                Log.e("gj",data.getResult().get(0).toString());
                List<FindMessageList> result = data.getResult();
                mineRemindMessageListAdapter.addList(result);
                mineRemindMessageListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("系统消息"+e.getMessage());
        }
    }
}
