package com.tranxitpro.user.ui.fragment.upcoming_trip;

import com.tranxitpro.user.base.BasePresenter;
import com.tranxitpro.user.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.FieldMap;


public class UpcomingTripPresenter<V extends UpcomingTripIView> extends BasePresenter<V> implements UpcomingTripIPresenter<V> {

    @Override
    public void upcomingTrip() {

        getCompositeDisposable().add(APIClient.getAPIClient().upcomingTrip()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(upcomingResponse -> getMvpView().onSuccess(upcomingResponse),
                        throwable -> getMvpView().onError(throwable)));
    }

    @Override
    public void cancelRequest(@FieldMap HashMap<String, Object> params) {

        getCompositeDisposable().add(APIClient.getAPIClient().cancelRequest(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(cancelResponse -> getMvpView().onSuccess(cancelResponse),
                        throwable -> getMvpView().onError(throwable)));
    }
}
