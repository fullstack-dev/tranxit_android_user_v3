package com.tranxitpro.user.ui.fragment.book_ride;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.PromoResponse;


public interface BookRideIView extends MvpView {
    void onSuccess(Object object);

    void onError(Throwable e);

    void onSuccessCoupon(PromoResponse promoResponse);
}
