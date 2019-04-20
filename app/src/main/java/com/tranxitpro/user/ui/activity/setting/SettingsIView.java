package com.tranxitpro.user.ui.activity.setting;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.AddressResponse;

public interface SettingsIView extends MvpView {

    void onSuccessAddress(Object object);

    void onLanguageChanged(Object object);

    void onSuccess(AddressResponse address);

    void onError(Throwable e);
}
