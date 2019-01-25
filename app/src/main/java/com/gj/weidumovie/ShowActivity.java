package com.gj.weidumovie;

import android.content.Intent;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.util.UIUtils;

public class ShowActivity extends WDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sreach_layout;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String sou = intent.getStringExtra("sou");
        UIUtils.showToastSafe(sou);

    }

    @Override
    protected void destoryData() {

    }


}
