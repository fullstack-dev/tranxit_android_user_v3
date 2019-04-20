package com.tranxitpro.user.ui.fragment.dispute;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.DisputeResponse;
import com.tranxitpro.user.data.network.model.Help;

import java.util.List;

public interface DisputeIView extends MvpView {

    void onSuccess(Object object);

    void onSuccessDispute(List<DisputeResponse> responseList);

    void onError(Throwable e);

    void onSuccess(Help help);
}
