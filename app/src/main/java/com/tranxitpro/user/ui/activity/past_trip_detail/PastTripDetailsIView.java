package com.tranxitpro.user.ui.activity.past_trip_detail;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.Datum;

import java.util.List;

public interface PastTripDetailsIView extends MvpView {

    void onSuccess(List<Datum> pastTripDetails);

    void onError(Throwable e);
}
