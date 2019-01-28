package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:我关注的影院
 * Author:GJ<br>
 * Date:2019/1/26 20:39
 */
public class MyLikeCinemaPresenter extends BasePresenter{

    private int page=1;
    public MyLikeCinemaPresenter(DataCall dataCall) {
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
        return iRequest.myLikeCinema((int)args[0],(String) args[1],page,(int)args[3]);
    }
}
