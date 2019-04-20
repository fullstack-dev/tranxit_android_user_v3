package com.tranxitpro.user.ui.activity.upcoming_trip_detail;

import com.tranxitpro.user.base.MvpPresenter;

public interface UpcomingTripDetailsIPresenter<V extends UpcomingTripDetailsIView> extends MvpPresenter<V> {

    void getUpcomingTripDetails(Integer requestId);
}
