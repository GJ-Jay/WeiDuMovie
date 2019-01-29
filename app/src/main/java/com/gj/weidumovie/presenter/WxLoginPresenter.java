package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:微信登录请求
 * Author:GJ<br>
 * Date:2019/1/28 20:37
 */
public class WxLoginPresenter extends BasePresenter{

    public WxLoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.weChatBindingLogin((String) args[0]);
    }
}
