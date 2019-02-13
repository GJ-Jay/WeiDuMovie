package com.gj.weidumovie;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackSuccessActivity extends WDActivity {

    @BindView(R.id.succes_img)
    ImageView succesImg;
    @BindView(R.id.success_text)
    TextView successText;
    @BindView(R.id.feedback_back)
    ImageView feedbackBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_success;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.feedback_back)
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_back://返回
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
