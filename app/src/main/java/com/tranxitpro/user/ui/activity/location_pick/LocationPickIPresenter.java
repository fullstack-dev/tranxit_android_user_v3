package com.tranxitpro.user.ui.activity.location_pick;

import com.tranxitpro.user.base.MvpPresenter;

public interface LocationPickIPresenter<V extends LocationPickIView> extends MvpPresenter<V> {
    void address();
}
