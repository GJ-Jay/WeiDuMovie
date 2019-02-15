package com.gj.weidumovie.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.FeedbackActivity;
import com.gj.weidumovie.FindUserBuyTicketActivity;
import com.gj.weidumovie.HomeActivity;
import com.gj.weidumovie.LoginActivity;
import com.gj.weidumovie.MineRemindActivity;
import com.gj.weidumovie.MyLikeActivity;
import com.gj.weidumovie.MyMassageActivity;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserInfo;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDFragment;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FindNewVersionPresenter;
import com.gj.weidumovie.presenter.QueryUserInfoPresenter;
import com.gj.weidumovie.presenter.UserSiginPresenter;
import com.gj.weidumovie.util.DateUtils;
import com.gj.weidumovie.util.UIUtils;

import java.util.Date;

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

    Unbinder unbinder;
    @BindView(R.id.mine_remind)
    ImageView mineRemind;
    @BindView(R.id.mine_head)
    SimpleDraweeView mineHead;
    @BindView(R.id.mine_login_reg)
    TextView mineLoginReg;
    @BindView(R.id.sigin_in)
    TextView siginIn;
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
    @BindView(R.id.mine_my_back)
    ImageView mineMyBack;
    @BindView(R.id.btn_back_mine)
    LinearLayout btnBackMine;
    private int userId;
    private SharedPreferences sp;
    private QueryUserInfoPresenter queryUserInfoPresenter;
    private String nickName;
    private String headPic;
    private String sessionId;
    private UserSiginPresenter userSiginPresenter;

    @Override
    public String getPageName() {
        return "我的页面";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_three;
    }

    @Override
    protected void initView() {
        sp = getContext().getSharedPreferences("Config", Context.MODE_PRIVATE);

        queryUserInfoPresenter = new QueryUserInfoPresenter(new queryCall());
        userSiginPresenter = new UserSiginPresenter(new userSiginCall());
        if (userId != 0) {
            //根据id查询用户

            queryUserInfoPresenter.reqeust(userId, sessionId);
            siginIn.setVisibility(View.VISIBLE);//签到
        }else {
            mineLoginReg.setText("未登录");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        if (userId != 0) {
            //根据id查询用户
            queryUserInfoPresenter.reqeust(userId, sessionId);
            siginIn.setVisibility(View.VISIBLE);//签到
        }else {
            mineLoginReg.setText("未登录");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_remind, R.id.mine_head, R.id.mine_login_reg, R.id.btn_msg_mine, R.id.btn_like_mine, R.id.btn_buy_mine, R.id.btn_feedback_mine, R.id.btn_version_mine, R.id.btn_back_mine,R.id.sigin_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_remind://点击跳转消息
                if (userId == 0) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                Intent intent_message = new Intent(getContext(), MineRemindActivity.class);
                startActivity(intent_message);
                break;
            case R.id.mine_head:

                break;
            case R.id.mine_login_reg://登录
                if(userId!=0){
                    return;
                }
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.sigin_in://签到
                if(userId==0){
                    return;
                }
                userSiginPresenter.reqeust(userId,sessionId);
                siginIn.setText("已签到");
                /*siginIn.setFocusableInTouchMode(true);
                siginIn.setFocusable(true);
                siginIn.requestFocus();*/
                break;
            case R.id.btn_msg_mine://我的信息页面跳转
                if (userId == 0) {
                    queryUserInfoPresenter.reqeust(userId, sessionId);
                    return;
                }
                Intent intent_msg = new Intent(getContext(), MyMassageActivity.class);
                startActivity(intent_msg);
                break;
            case R.id.btn_like_mine://我的关注
                if (userId == 0) {
                    queryUserInfoPresenter.reqeust(userId, sessionId);
                    return;
                }
                startActivity(new Intent(getContext(), MyLikeActivity.class));
                break;
            case R.id.btn_buy_mine://购票记录
                if (userId == 0) {
                    queryUserInfoPresenter.reqeust(userId, sessionId);
                    return;
                }
                startActivity(new Intent(getContext(), FindUserBuyTicketActivity.class));
                break;
            case R.id.btn_feedback_mine://意见反馈
                if (userId == 0) {
                    queryUserInfoPresenter.reqeust(userId, sessionId);
                    return;
                }
                Intent intent_feedback = new Intent(getContext(), FeedbackActivity.class);
                startActivity(intent_feedback);
                break;
            case R.id.btn_version_mine://最新版本
                if (userId == 0) {

                    return;
                }
                FindNewVersionPresenter findNewVersionPresenter = new FindNewVersionPresenter(new findNewVerdionCall());
                findNewVersionPresenter.reqeust(userId,sessionId, "0110010010000");
                break;
            case R.id.btn_back_mine://退出登录
                if (userId == 0) {
                    UIUtils.showToastSafe("未登录");
                    return;
                }
                sp.edit().clear().commit();
                sp.edit().putBoolean("isFirst", false).commit();
                Intent backLogin = new Intent(getContext(), HomeActivity.class);
                backLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//重点
                startActivity(backLogin);//重点
                break;
        }
    }

    //根据ID获取用户信息
    private class queryCall implements DataCall<Result<UserInfo>> {
        @Override
        public void success(Result<UserInfo> data) {
            if(data.getStatus().equals("0000")){
                UserInfo userInfo = data.getResult();
                nickName = userInfo.getNickName();
                headPic = userInfo.getHeadPic();
                mineHead.setImageURI(headPic);
                mineLoginReg.setText(nickName);
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getCode()+e.getMessage());
        }
    }

    private class userSiginCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                siginIn.setText("已签到");
                siginIn.setFocusable(false);
                UIUtils.showToastSafe(data.getMessage());
            }
           /* if (data.getStatus().equals("1001")){
                siginIn.setText("已签到");
                siginIn.setFocusable(false);
            }
            UIUtils.showToastSafe(data.getMessage());*/
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getMessage());
        }
    }

    private class findNewVerdionCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if(data.getStatus().equals("0000")){
                if(data.getFlag()==2){
                    UIUtils.showToastSafe(data.getMessage()+"没新版本，不需要更新");
                }else{
                    UIUtils.showToastSafe(data.getMessage()+"有新版本，需要更新");
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
