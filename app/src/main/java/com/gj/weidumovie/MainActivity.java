package com.gj.weidumovie;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gj.weidumovie.core.WDActivity;

public class MainActivity extends WDActivity {

    int count = 3;
    /*倒计时*/
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int arg1 = msg.arg1;
            if(count>0){
                count--;
                handler.sendEmptyMessageDelayed(0,1000);
            }else{
                startActivity(new Intent(MainActivity.this,GuidePageActivity.class));
                finish();
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        handler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    protected void destoryData() {

    }
}
