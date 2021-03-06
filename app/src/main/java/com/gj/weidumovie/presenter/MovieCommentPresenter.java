package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:关注影院
 * Author:GJ<br>
 * Date:2019/1/27 17:30
 */
public class MovieCommentPresenter extends BasePresenter{

    public MovieCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.movieComment((int)args[0],(String) args[1],(int)args[2],(String)args[3]);
    }
}
