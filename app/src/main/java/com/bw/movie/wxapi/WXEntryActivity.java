package com.bw.movie.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gj.weidumovie.HomeActivity;
import com.gj.weidumovie.bean.Result;
import com.gj.weidumovie.bean.WxLogin;
import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.exception.ApiException;
import com.gj.weidumovie.presenter.WxLoginPresenter;
import com.gj.weidumovie.util.UIUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
  private IWXAPI api;
  private WxLoginPresenter wxLoginPresenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 注册API
    api = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516");
    api.handleIntent(getIntent(), this);
  }
 
  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    api.handleIntent(intent, this);
  }
 
  @Override
  public void onReq(BaseReq baseReq) {
    finish();
  }
 
  @Override
  public void onResp(BaseResp resp) {
    switch (resp.errCode) {
      case BaseResp.ErrCode.ERR_OK:
        Log.e("flag", "-----code:ok");
        if (resp instanceof SendAuth.Resp) {
          SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;
          String code = sendAuthResp.code;
          Log.e("flag", "-----code:" + sendAuthResp.code);
          // 发起登录请求
          wxLoginPresenter = new WxLoginPresenter(new wxLoginCall());
          wxLoginPresenter.reqeust(code);

        }
        break;
      case BaseResp.ErrCode.ERR_USER_CANCEL:
        if (resp instanceof SendAuth.Resp) {}
        Log.e("flag", "-----授权取消:");
        Toast.makeText(this, "授权取消:", Toast.LENGTH_SHORT).show();
        finish();
        break;
      case BaseResp.ErrCode.ERR_AUTH_DENIED:
        if (resp instanceof SendAuth.Resp) {}
        Log.e("flag", "-----授权失败:");
        Toast.makeText(this, "授权5失败:", Toast.LENGTH_SHORT).show();
        finish();
        break;
      default:
        break;
    }
  }

  private class wxLoginCall implements DataCall<Result<WxLogin>> {
    @Override
    public void success(Result<WxLogin> data) {
      if(data.getStatus().equals("0000")){
        UIUtils.showToastSafe(data.getMessage());
        Log.e("ls",data.getResult().getSessionId()+" "+data.getResult().getUserId());
        SharedPreferences sharedPreferences = getSharedPreferences("Config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("userId",data.getResult().getUserId());
        edit.putString("sessionId",data.getResult().getSessionId());
        edit.commit();
        finish();
      }
    }

    @Override
    public void fail(ApiException e) {

    }
  }
}
