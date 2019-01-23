package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

public class RegPresenter extends BasePresenter {


    public RegPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.register((String) args[0], (String) args[1], (String) args[2],
                (String) args[3], (int) args[4], (String) args[5], (String) args[6],
                (String) args[7], (String) args[8], (String) args[9], (String) args[10]);
    }
}
