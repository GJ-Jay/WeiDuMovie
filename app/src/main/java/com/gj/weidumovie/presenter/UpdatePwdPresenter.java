package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Description:修改密码P层
 * Author:GJ<br>
 * Date:2019/1/26 14:08
 */
public class UpdatePwdPresenter extends BasePresenter{

    public UpdatePwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.updatePwd((int)args[0],(String) args[1],(String)args[2],(String)args[3],(String)args[4]);
    }
}
