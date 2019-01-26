package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:根据id查询用户信息
 * Author:GJ<br>
 * Date:2019/1/25 14:11
 */
public class QueryUserInfoPresenter extends BasePresenter{

    public QueryUserInfoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.queryUserInfo((int)args[0],(String) args[1]);
    }
}
