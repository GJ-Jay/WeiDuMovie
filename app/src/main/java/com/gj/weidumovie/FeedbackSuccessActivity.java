package com.gj.weidumovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackSuccessActivity extends AppCompatActivity {

    @BindView(R.id.feedback_back)
    ImageView feedbackBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_success);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.feedback_back)
    public void setClick(View view){
        switch (view.getId()){
            case R.id.feedback_back://返回
                finish();
                break;
        }
    }
}
