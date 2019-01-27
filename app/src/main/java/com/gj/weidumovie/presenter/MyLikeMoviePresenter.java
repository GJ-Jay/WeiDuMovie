package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:我关注的影片
 * Author:GJ<br>
 * Date:2019/1/26 20:39
 */
public class MyLikeMoviePresenter extends BasePresenter{

    public MyLikeMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.myLikeMovie((int)args[0],(String) args[1],(int)args[2],(int)args[3]);
    }
}
