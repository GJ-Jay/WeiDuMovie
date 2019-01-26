package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:修改用户请求
 * Author:GJ<br>
 * Date:2019/1/25 14:53
 */
public class UpdateUserPresenter extends BasePresenter{

    public UpdateUserPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.updateUser((int)args[0],(String) args[1],(String) args[2],(int)args[3],(String) args[4]);
    }
}
