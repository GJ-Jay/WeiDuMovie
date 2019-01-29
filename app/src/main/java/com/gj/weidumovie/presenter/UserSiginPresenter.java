package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:用户签到P层
 * Author:GJ<br>
 * Date:2019/1/29 14:45
 */
public class UserSiginPresenter extends BasePresenter{

    public UserSiginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.userSignIn((int)args[0],(String) args[1]);
    }
}
