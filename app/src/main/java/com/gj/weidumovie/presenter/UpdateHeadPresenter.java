package com.gj.weidumovie.presenter;

import com.gj.weidumovie.core.DataCall;
import com.gj.weidumovie.core.http.IRequest;
import com.gj.weidumovie.core.http.NetworkManager;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:<br>
 * Author:GJ<br>
 * Date:2019/1/24 19:33
 */
public class UpdateHeadPresenter extends BasePresenter{

    public UpdateHeadPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
       // File file = new File((String)args[2]);
        File file= (File) args[2];
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
        return iRequest.updateHead((int)args[0],(String) args[1],builder.build());
    }
}
