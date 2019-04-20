package com.tranxitpro.user.ui.activity.coupon;

import com.tranxitpro.user.base.MvpPresenter;

public interface CouponIPresenter<V extends CouponIView> extends MvpPresenter<V> {
    void coupon();
}
