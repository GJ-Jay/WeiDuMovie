package com.gj.weidumovie;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidumovie.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMassageActivity extends WDActivity {

    @BindView(R.id.my_info_head)
    SimpleDraweeView myInfoHead;
    @BindView(R.id.my_info_username)
    TextView myInfoUsername;
    @BindView(R.id.my_info_sex)
    TextView myInfoSex;
    @BindView(R.id.my_info_birth_date)
    TextView myInfoBirthDate;
    @BindView(R.id.my_info_telephone)
    TextView myInfoTelephone;
    @BindView(R.id.my_info_email)
    TextView myInfoEmail;
    @BindView(R.id.my_info_reset_pwd)
    ImageView myInfoResetPwd;
    @BindView(R.id.my_info_exit_login)
    Button myInfoExitLogin;
    @BindView(R.id.my_info_back)
    ImageView myInfoBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.my_info_head, R.id.my_info_username, R.id.my_info_sex, R.id.my_info_birth_date, R.id.my_info_telephone, R.id.my_info_email, R.id.my_info_reset_pwd, R.id.my_info_exit_login, R.id.my_info_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_info_head:
                break;
            case R.id.my_info_username:
                break;
            case R.id.my_info_sex:
                break;
            case R.id.my_info_birth_date:
                break;
            case R.id.my_info_telephone:
                break;
            case R.id.my_info_email:
                break;
            case R.id.my_info_reset_pwd:
                break;
            case R.id.my_info_exit_login:
                break;
            case R.id.my_info_back:
                break;
        }
    }
}
