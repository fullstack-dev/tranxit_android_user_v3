package com.tranxitpro.user.ui.activity.passbook;

import com.tranxitpro.user.base.MvpPresenter;

public interface WalletHistoryIPresenter<V extends WalletHistoryIView> extends MvpPresenter<V> {
    void wallet();
}
