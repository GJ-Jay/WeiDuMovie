package com.gj.weidumovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.UserBean;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.LoginPresenter;
import com.gj.weidumovie.util.EncryptUtil;
import com.gj.weidumovie.util.UIUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends WDActivity {
    @BindView(R.id.edit_login_phone)
    EditText editLoginPhone;
    @BindView(R.id.edit_login_password)
    EditText editLoginPassword;
    @BindView(R.id.login_eye)
    ImageView loginEye;
    @BindView(R.id.save_pwd)
    CheckBox savePwd;
    @BindView(R.id.zd_login)
    CheckBox zdLogin;
    @BindView(R.id.login_reg)
    TextView loginReg;
    @BindView(R.id.member_activity_login_layout)
    LinearLayout memberActivityLoginLayout;
    @BindView(R.id.login_ok)
    Button loginOk;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    private String phone;
    private String pwd;
    private LoginPresenter loginPresenter;
    private SharedPreferences.Editor edit;
    private IWXAPI api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        SharedPreferences sharedPreferences = getSharedPreferences("Config", Context.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean("flag",false);
        boolean rememberPassword = sharedPreferences.getBoolean("rememberPassword",false);
        edit = sharedPreferences.edit();
        String p = sharedPreferences.getString("pwd", "");
        String ph = sharedPreferences.getString("phone", "");
        String d = EncryptUtil.decrypt(p);
        Log.i("abc", "initView: "+p+ph);
        zdLogin.setChecked(flag);
        savePwd.setChecked(rememberPassword);

        //登录接口
        loginPresenter = new LoginPresenter(new LoginCall());

        if (flag){
            if (rememberPassword){
                //Log.i("abc", "initView: "+p+ph);
                editLoginPhone.setText(ph);
                editLoginPassword.setText(d);

            }else {
                editLoginPhone.setText("");
                editLoginPassword.setText("");
            }
            loginPresenter.reqeust(phone, pwd);
        }else {
            if (rememberPassword){
                Log.i("abc", "initView: "+p+ph);
                editLoginPhone.setText(ph);
                editLoginPassword.setText(d);

            }else {
                editLoginPhone.setText("");
                editLoginPassword.setText("");
            }
        }
    }

    @Override
    protected void destoryData() {
        loginPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this,"wxb3852e6a6b7d9516",true);
        //将应用的appid注册到微信
        api.registerApp("wxb3852e6a6b7d9516");
    }

    @OnClick({R.id.login_eye, R.id.login_reg, R.id.login_ok,R.id.login_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_eye:
                if(editLoginPassword.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    editLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
                else {
                    editLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                }
                break;
            case R.id.login_reg:
                    intent(RegisterActivity.class);
                break;
            case R.id.login_ok:
                phone = editLoginPhone.getText().toString();
                String pwds = editLoginPassword.getText().toString();
                pwd = EncryptUtil.encrypt(pwds);
                Log.i("abc", "onViewClicked: "+pwd);
                loginPresenter.reqeust(phone,pwd);
                break;
            case R.id.login_weixin://微信第三方登录
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_微信登录"; // 自行填写
                api.sendReq(req);
                finish();
                break;
        }

    }

    class LoginCall implements DataCall<Result<UserBean>>{

        @Override
        public void success(Result<UserBean> data) {
            if (data.getStatus().equals("0000")){
                Log.e("abcd","userid:"+data.getResult().getUserId()+" sessionid:"+data.getResult().getSessionId());
                //获取是否记住密码
                boolean rememberPassword = savePwd.isChecked();
                //获取是否自动登录
                boolean flag = zdLogin.isChecked();

                edit.putBoolean("flag",flag);
                edit.putBoolean("rememberPassword",rememberPassword);
                edit.putInt("userId",data.getResult().getUserId());
                edit.putString("sessionId",data.getResult().getSessionId());
                edit.putString("phone",phone);
                edit.putString("pwd",pwd);
                edit.commit();
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
            UIUtils.showToastSafe(data.getMessage());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
