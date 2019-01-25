package com.gj.weidumovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.LoginActivity;
import com.gj.weidumovie.MyMassageActivity;
import com.gj.weidumovie.core.WDFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/23 15:10
 */
public class Fragment_My_Three extends WDFragment {
    @BindView(R.id.mine_remind)
    ImageView mineRemind;
    @BindView(R.id.mine_head)
    SimpleDraweeView mineHead;
    @BindView(R.id.mine_login_reg)
    TextView mineLoginReg;
    @BindView(R.id.mine_my_info)
    ImageView mineMyInfo;
    @BindView(R.id.btn_msg_mine)
    LinearLayout btnMsgMine;
    @BindView(R.id.mine_my_attention)
    ImageView mineMyAttention;
    @BindView(R.id.btn_like_mine)
    LinearLayout btnLikeMine;
    @BindView(R.id.mine_my_buy_record)
    ImageView mineMyBuyRecord;
    @BindView(R.id.btn_buy_mine)
    LinearLayout btnBuyMine;
    @BindView(R.id.mine_my_idea)
    ImageView mineMyIdea;
    @BindView(R.id.btn_feedback_mine)
    LinearLayout btnFeedbackMine;
    @BindView(R.id.mine_my_new)
    ImageView mineMyNew;
    @BindView(R.id.btn_version_mine)
    LinearLayout btnVersionMine;
    Unbinder unbinder;

    @Override
    public String getPageName() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_three;
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.mine_remind, R.id.mine_head, R.id.mine_login_reg, R.id.btn_msg_mine, R.id.btn_like_mine, R.id.btn_buy_mine, R.id.btn_feedback_mine, R.id.btn_version_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_remind:

                break;
            case R.id.mine_head:

                break;
            case R.id.mine_login_reg://登录
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_msg_mine://我的信息页面跳转
                Intent intent_msg = new Intent(getContext(), MyMassageActivity.class);
                startActivity(intent_msg);
                break;
            case R.id.btn_like_mine:
                break;
            case R.id.btn_buy_mine:
                break;
            case R.id.btn_feedback_mine://意见反馈

                break;
            case R.id.btn_version_mine:
                break;
        }
    }
}
