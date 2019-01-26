package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

public class FindMoviesDetailPresenter extends BasePresenter {

    public FindMoviesDetailPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... objects) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.findMoviesDetail((int) objects[0],(String) objects[1],(int) objects[2]);
    }
}
