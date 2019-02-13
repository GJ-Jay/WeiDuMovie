package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.gj.weidumovie.core.WDActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WXPayEntryActivity extends WDActivity implements IWXAPIEventHandler {
    @BindView(R.id.succes_pay_img)
    ImageView succesPayImg;
    @BindView(R.id.faile_pay_img)
    ImageView failePayImg;
    @BindView(R.id.pay_result)
    TextView payResult;
    private IWXAPI api;

    @Override
    protected int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.pay_back})
    public void setClick(View view) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //支付成功后的逻辑
                    result = "微信支付成功";
                    succesPayImg.setVisibility(View.VISIBLE);
                    payResult.setText("购票成功");
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    result = "微信支付失败";// + resp.errCode + "，" + resp.errStr;
                    succesPayImg.setVisibility(View.INVISIBLE);
                    failePayImg.setVisibility(View.VISIBLE);
                    payResult.setText("购票失败");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "微信支付取消";// + resp.errCode + "，" + resp.errStr;
                    succesPayImg.setVisibility(View.INVISIBLE);
                    failePayImg.setVisibility(View.VISIBLE);
                    payResult.setText("购票失败");
                    break;
                default:
                    result = "微信支付未知异常";// + resp.errCode + "，" + resp.errStr;
                    break;
            }
//            payResult.setText(result);
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
