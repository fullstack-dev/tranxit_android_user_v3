package com.tranxitpro.user.ui.fragment.lost_item;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.DisputeResponse;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LostIView extends MvpView{
    void onSuccess(Object object);
    void onError(Throwable e);
}
