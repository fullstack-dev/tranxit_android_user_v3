package com.tranxitpro.user.ui.fragment.service;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.EstimateFare;
import com.tranxitpro.user.data.network.model.Service;

import java.util.List;

public interface ServiceTypesIView extends MvpView {

    void onSuccess(List<Service> serviceList);

    void onError(Throwable e);

    void onSuccess(Object object);
}
