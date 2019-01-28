package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

public class BuyMovieTicketPresenter extends BasePresenter {
    public BuyMovieTicketPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.buyMovieTicket((int)args[0],(String)args[1],(int)args[2],(int)args[3],(String)args[4]);
    }
}
