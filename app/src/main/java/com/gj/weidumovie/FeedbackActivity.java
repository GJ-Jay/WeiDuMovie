package com.gj.weidumovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.FeedBackPresenter;
import com.gj.weidumovie.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends WDActivity {
    @BindView(R.id.feedback_edit)
    EditText feedbackEdit;
    private FeedBackPresenter feedBackPresenter;
    private SharedPreferences sp;
    private int userId;
    private String sessionId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        feedBackPresenter = new FeedBackPresenter(new feedbackCall());
        sp = getSharedPreferences("Config", MODE_PRIVATE);
        userId = sp.getInt("userId", 0);
        sessionId = sp.getString("sessionId", "");
    }

    @OnClick({R.id.feedback_commit,R.id.feedback_back})
    public void setClick(View view){
        switch (view.getId()){
            case R.id.feedback_back://返回
                finish();
                break;
            case R.id.feedback_commit://点击提交
                if(userId==0){//判断是否登录
                    UIUtils.showToastSafe("请登录");
                    return;
                }
                String message = feedbackEdit.getText().toString();
                if(message.equals("")){//判空
                    UIUtils.showToastSafe("请输入内容");
                    return;
                }else{
                    feedBackPresenter.reqeust(userId,sessionId,message);
                    startActivity(new Intent(FeedbackActivity.this,FeedbackSuccessActivity.class));
                    finish();
                }
                break;
        }
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

    private class feedbackCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if(data.getStatus().equals("0000")){
                UIUtils.showToastSafe("意见提交成功"+data.getMessage());
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe("意见提交失败"+e.getMessage());
        }
    }
}
