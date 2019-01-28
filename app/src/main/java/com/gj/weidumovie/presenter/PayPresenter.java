package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * date:2019/1/28 10:18
 * author:陈国星(陈国星)
 * function:
 */
public class PayPresenter extends BasePresenter {
    public PayPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.buyMovieresult((int)args[0],(String)args[1],(int)args[2],(String) args[3]);

    }
}
