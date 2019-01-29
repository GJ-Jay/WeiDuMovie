package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:查询新版本p层
 * Author:GJ<br>
 * Date:2019/1/29 15:36
 */
public class FindNewVersionPresenter extends BasePresenter{

    public FindNewVersionPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.findNewVersion((int)args[0],(String) args[1],(String) args[2]);
    }
}
