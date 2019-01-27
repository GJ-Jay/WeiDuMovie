package com.gj.weidumovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.UpdatePwdPresenter;
import com.gj.weidumovie.util.EncryptUtil;
import com.gj.weidumovie.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePwdActivity extends WDActivity {


    @BindView(R.id.textpwd_now)
    EditText textpwdNow;
    @BindView(R.id.textpwd_reset)
    EditText textpwdReset;
    @BindView(R.id.textpwd_algin)
    EditText textpwdAlgin;
    private UpdatePwdPresenter updatePwdPresenter;
    private String s;
    private int userId;
    private String sessionId;
    private String pwd;
    private String pwd_reset;
    private String pwd_algin;
    private SharedPreferences sp;
    private String pwd_now;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        sp = getSharedPreferences("Config", MODE_PRIVATE);
        pwd = sp.getString("pwd", "");
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
        updatePwdPresenter = new UpdatePwdPresenter(new updatePwdCall());
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.textpwd_reset, R.id.textpwd_algin, R.id.update_pwd_commit, R.id.update_pwd_back})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.update_pwd_commit:
                String textPwdNow = textpwdNow.getText().toString().trim();
                pwd_now = EncryptUtil.encrypt(textPwdNow);
                s = textpwdReset.getText().toString().trim();
                pwd_reset = EncryptUtil.encrypt(s);
                String s1 = textpwdAlgin.getText().toString().trim();
                if (!s1.equals(s)) {
                    UIUtils.showToastSafe("两次密码输入请保持一致");
                    return;
                }
                pwd_algin = EncryptUtil.encrypt(s1);
                updatePwdPresenter.reqeust(userId, sessionId, pwd_now, pwd_reset, pwd_algin);
                break;
            case R.id.update_pwd_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private class updatePwdCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                /*Intent intent = new Intent(UpdatePwdActivity.this, LoginActivity.class);
                // Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
                //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//重点
                startActivity(intent);//重点*/
                UIUtils.showToastSafe(data.getMessage());
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("修改密码请求失败" + e.getMessage());
        }
    }
}
