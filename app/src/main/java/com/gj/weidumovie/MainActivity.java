package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;

public class MainActivity extends WDActivity {

    private boolean isFirst;
    private SharedPreferences sp;
    int count = 2;
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

                sp = getSharedPreferences("Config", Context.MODE_PRIVATE);
                isFirst = sp.getBoolean("isFirst", true);
                if(isFirst){
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isFirst",false);
                    edit.commit();
                    startActivity(new Intent(MainActivity.this,GuidePageActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
