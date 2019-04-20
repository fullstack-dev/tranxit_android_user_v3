package com.tranxitpro.user.ui.activity.payment;

import com.tranxitpro.user.base.BasePresenter;
import com.tranxitpro.user.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PaymentPresenter<V extends PaymentIView> extends BasePresenter<V> implements PaymentIPresenter<V> {

    @Override
    public void deleteCard(String cardId) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .deleteCard(cardId, "DELETE")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void card() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .card()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }

    @Override
    public void addCard(String cardId) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .card(cardId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onAddCardSuccess, getMvpView()::onError));
    }

    @Override
    public void payuMoneyChecksum() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .payuMoneyChecksum()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onPayumoneyCheckSumSucess, getMvpView()::onError));
    }

    public void paytmCheckSum(String request_id,String payment_mode) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .payTmChecksum(request_id, payment_mode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onPayTmCheckSumSuccess, getMvpView()::onError));
    }

    @Override
    public void getBrainTreeToken() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getBraintreeToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
