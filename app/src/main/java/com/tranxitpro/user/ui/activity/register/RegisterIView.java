package com.tranxitpro.user.ui.activity.register;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.RegisterResponse;
import com.tranxitpro.user.data.network.model.SettingsResponse;

public interface RegisterIView extends MvpView {

    void onSuccess(SettingsResponse response);

    void onSuccess(RegisterResponse object);

    void onSuccess(Object object);

    void onSuccessPhoneNumber(Object object);

    void onVerifyPhoneNumberError(Throwable e);

    void onError(Throwable e);

    void onVerifyEmailError(Throwable e);
}
