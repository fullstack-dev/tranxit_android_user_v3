package com.tranxitpro.user.ui.fragment.book_ride;

import com.tranxitpro.user.base.MvpPresenter;

import java.util.HashMap;


public interface BookRideIPresenter<V extends BookRideIView> extends MvpPresenter<V> {
    void rideNow(HashMap<String, Object> obj);

    void getCouponList();
}
