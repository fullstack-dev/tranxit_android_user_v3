package com.tranxitpro.user.ui.activity.payment;

import com.appoets.paytmpayment.PaytmObject;
import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.BrainTreeResponse;
import com.tranxitpro.user.data.network.model.Card;
import com.tranxitpro.user.data.network.model.CheckSumData;
import com.tranxitpro.user.data.network.model.PayTmResponse;

import java.util.List;

public interface PaymentIView extends MvpView {

    void onSuccess(Object card);

    void onSuccess(BrainTreeResponse response);

    void onSuccess(List<Card> cards);

    void onAddCardSuccess(Object cards);

    void onError(Throwable e);

    void onPayumoneyCheckSumSucess(CheckSumData checkSumData);

    void onPayTmCheckSumSuccess(PaytmObject payTmResponse);

}
