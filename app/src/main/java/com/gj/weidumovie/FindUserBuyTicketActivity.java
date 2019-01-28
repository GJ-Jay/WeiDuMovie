package com.gj.weidumovie;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.fragment.buyticket.Fragment_BuyTicket_Finish;
import com.gj.weidumovie.fragment.buyticket.Fragment_BuyTicket_Wait;
import com.gj.weidumovie.fragment.mylike.Fragment_Mylike_Cinema;
import com.gj.weidumovie.fragment.mylike.Fragment_Mylike_Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindUserBuyTicketActivity extends WDActivity {

    @BindView(R.id.buy_ticket_radio1)
    RadioButton buyTicketRadio1;
    @BindView(R.id.buy_ticket_radio2)
    RadioButton buyTicketRadio2;
    @BindView(R.id.buy_ticket_radio)
    RadioGroup buyTicketRadio;
    @BindView(R.id.lay)
    LinearLayout lay;
    @BindView(R.id.buy_ticket_frag)
    FrameLayout buyTicketFrag;
    @BindView(R.id.buy_ticket_back)
    ImageView buyTicketBack;
    private FragmentManager manager;
    private Fragment_BuyTicket_Wait fragmentBuyTicketWait;
    private Fragment_BuyTicket_Finish fragmentBuyTicketFinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_user_buy_ticket;
    }

    @Override
    protected void initView() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragmentBuyTicketWait = new Fragment_BuyTicket_Wait();
        fragmentBuyTicketFinish = new Fragment_BuyTicket_Finish();

        transaction.add(R.id.buy_ticket_frag, fragmentBuyTicketWait).show(fragmentBuyTicketWait);//页面切换
        transaction.add(R.id.buy_ticket_frag, fragmentBuyTicketFinish).hide(fragmentBuyTicketFinish);
        transaction.commit();

        buyTicketRadio.check(buyTicketRadio.getChildAt(0).getId());//默认第一个页面选中
        buyTicketRadio1.setBackgroundResource(R.drawable.my_like_btnshape);
        buyTicketRadio1.setTextColor(getResources().getColor(R.color.white));

        buyTicketRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case R.id.buy_ticket_radio1:
                        transaction1.show(fragmentBuyTicketWait).hide(fragmentBuyTicketFinish);
                        buyTicketRadio.check(buyTicketRadio.getChildAt(0).getId());
                        buyTicketRadio1.setBackgroundResource(R.drawable.my_like_btnshape);
                        buyTicketRadio2.setBackgroundResource(R.drawable.my_like_btn_falseshape);
                        buyTicketRadio1.setTextColor(getResources().getColor(R.color.white));
                        buyTicketRadio2.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    case R.id.buy_ticket_radio2:
                        transaction1.show(fragmentBuyTicketFinish).hide(fragmentBuyTicketWait);
                        buyTicketRadio.check(buyTicketRadio.getChildAt(1).getId());
                        buyTicketRadio2.setBackgroundResource(R.drawable.my_like_btnshape);
                        buyTicketRadio1.setBackgroundResource(R.drawable.my_like_btn_falseshape);
                        buyTicketRadio2.setTextColor(getResources().getColor(R.color.white));
                        buyTicketRadio1.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                }
                transaction1.commit();//提交事务
            }
        });
    }

    @OnClick(R.id.buy_ticket_back)
    public void setClick(){
        finish();
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
