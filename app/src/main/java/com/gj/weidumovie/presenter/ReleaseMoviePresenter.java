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

public class ReleaseMoviePresenter extends BasePresenter {
    private int page=1;
    public ReleaseMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        boolean flag= (boolean) args[2];
        if (flag){
            page++;
        }else {
            page=1;
        }
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);

        return iRequest.findReleaseMovieList((int)args[0],(String)args[1],page,(int)args[3]);
    }


}

