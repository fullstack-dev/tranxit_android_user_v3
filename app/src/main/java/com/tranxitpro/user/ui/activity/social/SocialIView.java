package com.tranxitpro.user.ui.activity.social;

import com.tranxitpro.user.base.MvpView;
import com.tranxitpro.user.data.network.model.Token;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface SocialIView extends MvpView {
    void onSuccess(Token token);

    void onError(Throwable e);
}
