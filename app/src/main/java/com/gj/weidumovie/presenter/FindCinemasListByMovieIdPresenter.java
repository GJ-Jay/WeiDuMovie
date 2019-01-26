package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/24 19:33
 */
public class FindCinemasListByMovieIdPresenter extends BasePresenter{

    public FindCinemasListByMovieIdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.findCinemasListByMovieId((int)args[0]);
    }
}
