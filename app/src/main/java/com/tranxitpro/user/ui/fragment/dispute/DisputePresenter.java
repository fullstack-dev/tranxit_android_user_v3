package com.tranxitpro.user.ui.fragment.dispute;

import com.tranxitpro.user.base.BasePresenter;
import com.tranxitpro.user.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DisputePresenter<V extends DisputeIView> extends BasePresenter<V> implements DisputeIPresenter<V> {

    @Override
    public void dispute(HashMap<String, Object> obj) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .dispute(obj)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void getDispute() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getDispute("user")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccessDispute, getMvpView()::onError));
    }

    @Override
    public void help() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .help()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
