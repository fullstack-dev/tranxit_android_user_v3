package com.tranxitpro.user.ui.activity.upcoming_trip_detail;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.Datum;

import java.util.List;

public interface UpcomingTripDetailsIView extends MvpView {

    void onSuccess(List<Datum> upcomingTripDetails);

    void onError(Throwable e);
}
