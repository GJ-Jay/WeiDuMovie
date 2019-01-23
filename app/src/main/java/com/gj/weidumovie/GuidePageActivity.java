package com.gj.weidumovie;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gj.weidumovie.core.WDActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidePageActivity extends WDActivity {

    @BindView(R.id.welcome_viewpager)
    ViewPager mWelcomeViewpager;
    @BindView(R.id.welcome_jump)
    Button mWelcomeJump;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initView() {
        ArrayList<View> mList = new ArrayList<>();

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
