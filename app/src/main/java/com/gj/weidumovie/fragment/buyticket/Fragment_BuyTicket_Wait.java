package com.gj.weidumovie.fragment.buyticket;

import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.bw.movie.R;
import com.gj.weidumovie.MovieBuyShowActivity;
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
import com.gj.weidumovie.presenter.PayPresenter;
import com.gj.weidumovie.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
    private View parent;
    private PopupWindow window;
    private String orderId;
    private double prices;
    private PayPresenter payPresenter;
    private View contentView;
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
        parent = View.inflate(getContext(), R.layout.fragment_buyticket_wait, null);
        sp = getActivity().getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        payPresenter = new PayPresenter(new MyPay());
        buyTicketPresenter = new BuyTicketPresenter(new myTicketWaitCall());
        myLikeWaitAdapter = new MyLikeWaitAdapter(getActivity()); //getActivity()
        buyticketXrlvWait.setLoadingListener(this);//加载更多 允许加载更多和刷新
        buyticketXrlvWait.setLoadingMoreEnabled(true);
        buyticketXrlvWait.setPullRefreshEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        buyticketXrlvWait.setLayoutManager(linearLayoutManager);//线性布局
        buyticketXrlvWait.setAdapter(myLikeWaitAdapter);

        showPopu();
        /*int status1= buyTicket.getStatus();*/
        //buyTicketPresenter.reqeust(userId, sessionId, false, 5, 1);
        myLikeWaitAdapter.setClickListener(new MyLikeWaitAdapter.ClickListener() {
            @Override
            public void click(String code,double price) {
                orderId=code;
                prices=price;
                Button popu_buy_ok = contentView.findViewById(R.id.popu_buy_ok);
                popu_buy_ok.setText("微信支付"+prices+"元");
                popu_buy_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        payPresenter.reqeust(userId,sessionId,1,orderId);
                        window.dismiss();
                    }
                });
                window.showAtLocation(parent,Gravity.BOTTOM,0,0);

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
        buyTicketPresenter.unBind();
        payPresenter.unBind();
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
    private void showPopu(){
        contentView = View.inflate(getContext(), R.layout.popu_buy_layout, null);
        window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //问题：不能操作窗口内部的控件
        window.setFocusable(true);//获取焦点
        window.setTouchable(true);//
        //点击窗口外部窗口不消失
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());

        getShow(contentView);

    }

    private void getShow(View contentView) {
        ImageView popu_buy_back=  contentView.findViewById(R.id.popu_buy_back);
        popu_buy_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        final RadioButton radio_buy_one=contentView.findViewById(R.id.radio_buy_one);
        final RadioButton radio_buy_two=contentView.findViewById(R.id.radio_buy_two);
        radio_buy_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    radio_buy_two.setChecked(false);
                }else {
                    radio_buy_two.setChecked(true);
                }
            }
        });
        radio_buy_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    radio_buy_one.setChecked(false);
                }else {
                    radio_buy_one.setChecked(true);
                }
            }
        });

        /*Button popu_buy_ok = contentView.findViewById(R.id.popu_buy_ok);
        popu_buy_ok.setText("微信支付"+prices+"元");
        popu_buy_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPresenter.reqeust(userId,sessionId,1,orderId);
                window.dismiss();
            }
        });*/
    }
    class MyPay implements DataCall<Result> {

        @Override
        public void success(Result result) {
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), "wxb3852e6a6b7d9516");
            msgApi.registerApp("wxb3852e6a6b7d9516");
            PayReq request = new PayReq();
            request.appId = result.getAppId();
            request.partnerId = result.getPartnerId();
            request.prepayId= result.getPrepayId();
            request.packageValue = result.getPackageValue();
            request.nonceStr= result.getNonceStr();
            request.timeStamp=result.getTimeStamp();
            request.sign= result.getSign();
            msgApi.sendReq(request);
//            finish();

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        buyTicketPresenter.reqeust(userId, sessionId, false, 5, 1);
    }
}
