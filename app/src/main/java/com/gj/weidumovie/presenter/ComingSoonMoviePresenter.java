package com.gj.weidumovie.presenter;



import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */

public class ComingSoonMoviePresenter extends BasePresenter {
    private int page=1;
    public ComingSoonMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        boolean flag= (boolean) args[2];
        if (flag){
            page++;
        }else {
            page=1;
        }
        return iRequest.findComingSoonMovieList((int)args[0],(String)args[1],page,(int)args[3]);
    }


}

