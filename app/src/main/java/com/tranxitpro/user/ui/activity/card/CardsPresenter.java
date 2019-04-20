package com.tranxitpro.user.ui.activity.card;

import com.tranxitpro.user.base.BasePresenter;
import com.tranxitpro.user.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardsPresenter<V extends CardsIView> extends
        BasePresenter<V> implements CarsIPresenter<V> {

    @Override
    public void card() {

        getCompositeDisposable().add(APIClient.getAPIClient().card()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(cards -> getMvpView().onSuccess(cards),
                        throwable -> getMvpView().onError(throwable)));
    }
}
