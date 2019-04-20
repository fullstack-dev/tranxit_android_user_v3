package com.tranxitpro.user.ui.fragment.searching;

import com.tranxitpro.user.base.MvpView;

public interface SearchingIView extends MvpView {
    void onSuccess(Object object);

    void onError(Throwable e);
}
