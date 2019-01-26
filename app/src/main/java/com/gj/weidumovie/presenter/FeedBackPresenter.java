package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:意见反馈请求层
 * Author:GJ
 * Date:2019/1/25 11:37
 */
public class FeedBackPresenter extends BasePresenter{

    public FeedBackPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.feedBack((int)args[0],(String) args[1],(String) args[2]);
    }
}
