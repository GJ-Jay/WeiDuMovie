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
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserBean;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.LoginPresenter;
import com.gj.weidumovie.util.UIUtils;

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
    private LoginPresenter loginPresenter;
    private SharedPreferences.Editor edit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(new LoginCall());
        handler.sendEmptyMessageDelayed(0,1000);
        sp = getSharedPreferences("Config", Context.MODE_PRIVATE);
        edit = sp.edit();
        boolean flag=  sp.getBoolean("flag",false);
        String p = sp.getString("pwd", "");
        String ph = sp.getString("phone", "");
        if (flag){
            loginPresenter.reqeust(ph, p);
        }


    }

    @Override
    protected void destoryData() {
        loginPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    class LoginCall implements DataCall<Result<UserBean>> {

        @Override
        public void success(Result<UserBean> data) {
            if (data.getStatus().equals("0000")) {
                edit.putInt("userId", data.getResult().getUserId());
                edit.putString("sessionId", data.getResult().getSessionId());
                edit.commit();

            }

            UIUtils.showToastSafe(data.getMessage());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
