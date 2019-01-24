package com.gj.weidumovie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bw.movie.R;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.WDActivity;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.RegPresenter;
import com.gj.weidumovie.util.EncryptUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends WDActivity {
    @BindView(R.id.register_nickName)
    EditText registerNickName;
    @BindView(R.id.register_sex)
    EditText registerSex;
    @BindView(R.id.register_date)
    EditText registerDate;
    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_mail)
    EditText registerMail;
    @BindView(R.id.register_pwd)
    EditText registerPwd;
    @BindView(R.id.member_activity_login_layout)
    LinearLayout memberActivityLoginLayout;
    @BindView(R.id.register_ok)
    Button registerOk;
    private RegPresenter regPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {
        regPresenter.unBind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        regPresenter = new RegPresenter(new RegCall());

    }

    /**
     * 日期转换
     *
     * @param date
     * @return
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    @OnClick({R.id.register_date, R.id.register_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_date:
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        registerDate.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        //.isDialog(true)//是否显示为对话框样式
                        .build();

                pvTime.show();
                break;
            case R.id.register_ok:
                int sexInt=1;
                String name = registerNickName.getText().toString();
                String date = registerDate.getText().toString();
                String mail = registerMail.getText().toString();
                String phone = registerPhone.getText().toString();
                String pwd = registerPwd.getText().toString();
                String sex = registerSex.getText().toString();
                 String pwds = EncryptUtil.encrypt(pwd);
                if (sex.equals("男")||sex.equals("女")){
                    if (sex.equals("男")){
                        sexInt=1;
                    }else {
                        sexInt=2;
                    }
                    Log.i("abc", "onViewClicked: "+date+"/"+pwds);
                    regPresenter.reqeust(name, phone, pwds, pwds,
                            sexInt, date, "123456", "小米", "5.0", "android", mail);
                }else {
                    Toast.makeText(RegisterActivity.this, "请输入男或女", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }

    class RegCall implements DataCall<Result>{

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                finish();
            }
            Toast.makeText(RegisterActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
