package com.tranxitpro.user.ui.activity.coupon;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.PromoResponse;

public interface CouponIView extends MvpView {
    void onSuccess(PromoResponse object);

    void onError(Throwable e);
}
