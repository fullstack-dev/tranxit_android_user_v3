package com.tranxitpro.user.ui.activity.login;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.ForgotResponse;
import com.tranxitpro.user.data.network.model.Token;

public interface LoginIView extends MvpView {
    void onSuccess(Token token);

    void onSuccess(ForgotResponse object);

    void onError(Throwable e);
}
