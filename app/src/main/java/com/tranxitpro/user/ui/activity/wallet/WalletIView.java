package com.tranxitpro.user.ui.activity.wallet;

import com.appoets.paytmpayment.PaytmObject;
import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.AddWallet;
import com.tranxitpro.user.data.network.model.BrainTreeResponse;

public interface WalletIView extends MvpView {
    void onSuccess(AddWallet object);

    void onSuccess(PaytmObject object);

    void onSuccess(BrainTreeResponse response);
    void onError(Throwable e);
}
