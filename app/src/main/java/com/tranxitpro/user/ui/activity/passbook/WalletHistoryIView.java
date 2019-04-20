package com.tranxitpro.user.ui.activity.passbook;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.WalletResponse;

public interface WalletHistoryIView extends MvpView {
    void onSuccess(WalletResponse response);

    void onError(Throwable e);
}
